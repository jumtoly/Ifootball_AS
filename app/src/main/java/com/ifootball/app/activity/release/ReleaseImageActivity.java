package com.ifootball.app.activity.release;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.activity.stand.SeeImageActivity;
import com.ifootball.app.entity.release.ImageFloder;
import com.ifootball.app.framework.adapter.release.Photo2DAdapter;
import com.ifootball.app.framework.widget.TextViewMultilineEllipse;
import com.ifootball.app.framework.widget.TitleBarView;
import com.ifootball.app.framework.widget.release.ListImageDirPopupWindow;
import com.ifootball.app.framework.widget.release.ListImageDirPopupWindow.OnImageDirSelected;
import com.ifootball.app.util.IntentUtil;
import com.ifootball.app.R;

public class ReleaseImageActivity extends BaseActivity implements
        OnImageDirSelected, OnClickListener, OnItemClickListener {
    public static final String PICTOTAL = "PICTOTAL";

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;

    private ProgressDialog mProgressDialog;
    private Uri fileUri;

    private int mPicsSize; //存储文件夹中的图片数量

    private File mImgDir;//图片数量最多的文件夹
    private List<String> mImgs;//所有的图片

    private GridView mGirdView;
    private Photo2DAdapter mAdapter;

    private ImageView mSubmit;

    private TextView mCurretnDirName;
    private HashSet<String> mDirPaths = new HashSet<>();//临时的辅助类，用于防止同一个文件夹的多次扫描

    private List<ImageFloder> mImageFloders = new ArrayList<>();//扫描拿到所有的图片文件夹

    private RelativeLayout mBottomLy;

    private LinearLayout mChooseDir;
    private int totalCount = 0;

    private int mScreenHeight;
    public static int picCount;

    private ListImageDirPopupWindow mListImageDirPopupWindow;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            // 为View绑定数据
            data2View();
            // 初始化展示文件夹的popupWindw
            initListDirPopupWindw();
        }
    };

    /**
     * 为View绑定数据
     */
    private void data2View() {
        if (mImgDir == null) {
            Toast.makeText(getApplicationContext(), "一张图片没扫描到",
                    Toast.LENGTH_SHORT).show();
            mAdapter = new Photo2DAdapter(getApplicationContext(), mImgs,
                    R.layout.item_release_image_grid, "");
            mGirdView.setAdapter(mAdapter);
            return;
        }

        mImgs = Arrays.asList(mImgDir.list());
        Collections.reverse(mImgs);
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter = new Photo2DAdapter(getApplicationContext(), mImgs,
                R.layout.item_release_image_grid, mImgDir.getAbsolutePath());
        mGirdView.setAdapter(mAdapter);

    }

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(
                LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.7),
                mImageFloders, LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.list_dir, null));

        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        putContentView(R.layout.activity_release_image, "", true);
        TitleBarView view = (TitleBarView) findViewById(R.id.release_image_titlebar);

        view.setViewData(getResources().getDrawable(R.mipmap.ico_backspace),
                "图片", getResources().getDrawable(R.mipmap.ico_submit));
        view.setActivity(this);
        picCount = getIntent().getIntExtra(PICTOTAL, 0);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;

        initView();
        getImages();
        initEvent();
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = ReleaseImageActivity.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);
                if (mCursor == null) {
                    return;
                }
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }

                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            return filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg");
                        }
                    }).length;
                    totalCount += picSize;

                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);

                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();

                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();

    }

    /**
     * 初始化View
     */
    private void initView() {
        mGirdView = (GridView) findViewById(R.id.id_gridView);
        mChooseDir = (LinearLayout) findViewById(R.id.id_choose_dir);
        mCurretnDirName = (TextView) findViewById(R.id.id_current_dir_name);

        mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
        mSubmit = (ImageView) findViewById(R.id.title_right_icon);
        mSubmit.setOnClickListener(this);
        mGirdView.setOnItemClickListener(this);
    }

    private void initEvent() {
        /**
         * 为底部的布局设置点击事件，弹出popupWindow
         */
        mBottomLy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListImageDirPopupWindow
                        .setAnimationStyle(R.style.anim_popup_dir);
                mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);

                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = .3f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void selected(ImageFloder floder) {

        mImgDir = new File(floder.getDir());
        mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".jpg") || filename.endsWith(".png")
                        || filename.endsWith(".jpeg");
            }
        }));
        Collections.reverse(mImgs);

        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter = new Photo2DAdapter(getApplicationContext(), mImgs,
                R.layout.item_release_image_grid, mImgDir.getAbsolutePath());
        mGirdView.setAdapter(mAdapter);
        // mAdapter.notifyDataSetChanged();
        mCurretnDirName.setText(floder.getName());
        mListImageDirPopupWindow.dismiss();

    }

    @Override
    public void onClick(View v) {

        ArrayList<String> resultList = new ArrayList<>(
                Photo2DAdapter.mSelectedImage);

        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ReleaseTrendsActivity.SELECT_IMAGES, resultList);
        IntentUtil.redirectToNextNewActivity(this, ReleaseTrendsActivity.class, bundle);
        Photo2DAdapter.clear();
        ReleaseImageActivity.this.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (position == 0) {
            SysCamera();
        }

    }

    private void SysCamera() {
        fileUri = Uri.fromFile(getOutputMediaFile(MEDIA_TYPE_IMAGE));

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

        ContentValues localContentValues = new ContentValues();

        localContentValues.put("_data",
                fileUri.toString().substring(7, fileUri.toString().length()));

        localContentValues.put("description", "save image ---");

        localContentValues.put("mime_type", "image/jpeg");

        ContentResolver localContentResolver = getContentResolver();

        Uri localUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        localContentResolver.insert(localUri, localContentValues);
    }

    private static File getOutputMediaFile(int type) {

        File mediaStorageDir = null;
        try {
            mediaStorageDir = new File(
                    Environment.getExternalStorageDirectory() + File.separator
                            + "ifootball", "ifootball_images");

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mediaStorageDir != null && !mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE && mediaStorageDir != null) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {

            case RESULT_OK: // 拍照成功回调
                if (CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE == requestCode) {
                    Bundle bundle = new Bundle();
                    bundle.putString(SeeImageActivity.SIGN_CAMERA_POSITION, fileUri
                            .toString().substring(7, fileUri.toString().length()));
                    IntentUtil.redirectToNextActivity(this, SeeImageActivity.class,
                            bundle);
                }
            case RESULT_CANCELED:// 用户取消的拍照

                break;
            case RESULT_FIRST_USER:// 拍照失败
                break;
            case SeeImageActivity.REQUEST_SIGN:

                break;
        }
    }
}
