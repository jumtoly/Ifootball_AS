package com.ifootball.app.activity.release;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.framework.adapter.release.ReleaseTrends2DAdapter;
import com.ifootball.app.framework.widget.TitleBarView;
import com.ifootball.app.R;

public class ReleaseTrendsActivity extends BaseActivity implements
        OnClickListener, OnItemClickListener, OnScrollListener {


    public static final String SELECT_IMAGES = "SELECT_IMAGES";
    public static final String VIDEO = "VIDEO";

    private static final String LOG_TAG = "ReleaseTrendsActivity";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;


    public static final int PHOTO = 3;
    public static final int CARMERA = 4;
    public static final int ADDRESS = 5;

    private EditText mShareContent;
    private GridView mShareImages;
    private VideoView mShareVideo;
    private View mSelectAddress;
    private View mShareToFriend;
    private int mGridLastVisibleItem;
    private LinkedList<String> mSharePicUrls = new LinkedList<>();
    private Uri fileUri;
    private ReleaseTrends2DAdapter mAdatpter;
    private ImageView mSubmit;
    private TitleBarView mTitleBar;
    private String sendContent;
    private TextView mSelectLocation;
    private ProgressBar mProgress;
    private String mVideoPath;
    private boolean isVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_release);
        mTitleBar = (TitleBarView) findViewById(R.id.release_trends_titlebar);
        mTitleBar.setViewData(
                getResources().getDrawable(R.mipmap.ico_backspace), "发布动态",
                getResources().getDrawable(R.mipmap.ico_submit));
        mTitleBar.setActivity(this);
        findView();
        mVideoPath = getIntent().getStringExtra(VIDEO);
        if (mVideoPath == null) {
            isVideo = false;
            imageSetting();
        } else {
            isVideo = true;
            videoSetting(mVideoPath);

        }

    }

    private void videoSetting(String videoPath) {

        final File file = new File(videoPath);
        if (!file.exists()) {
            return;
        }

        try {
            final Uri uri = Uri.parse(videoPath);
            mShareVideo.setVideoURI(uri);
            mShareVideo.start();
            mShareVideo.setOnCompletionListener(new OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    mShareVideo.start();

                }
            });
        } catch (Exception e) {
            Log.e("ReleaseTrendsActivity", e.toString());
        }

    }

    private void imageSetting() {
        mShareVideo.setVisibility(View.GONE);
        mShareImages.setVisibility(View.VISIBLE);
        mSharePicUrls.add("");
        mAdatpter = new ReleaseTrends2DAdapter(this, mSharePicUrls);
        mShareImages.setAdapter(mAdatpter);
    }

    private void findView() {
        mShareContent = (EditText) findViewById(R.id.main_release_trends_shareContent);
        mShareImages = (GridView) findViewById(R.id.main_release_trends_shareImages);
        mSelectAddress = findViewById(R.id.main_release_trends_selectAddress);
        mShareToFriend = findViewById(R.id.main_release_trends_shareToFriend);
        mSubmit = (ImageView) mTitleBar.findViewById(R.id.title_right_icon);
        mShareVideo = (VideoView) findViewById(R.id.main_release_trends_shareVid);
        mSelectLocation = (TextView) findViewById(R.id.main_release_trends_address);
        mProgress = (ProgressBar) findViewById(R.id.release_trends_progress);
        mProgress.setVisibility(View.GONE);

        mShareImages.setOnItemClickListener(this);
        mShareImages.setOnScrollListener(this);
        mSelectAddress.setOnClickListener(this);
        mShareToFriend.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mVideoPath != null) {
            videoSetting(mVideoPath);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_release_trends_selectAddress:// 选择地点
            /*Intent intent = new Intent(this, SelectAddressActivity.class);
            startActivityForResult(intent, ADDRESS);*/
                break;
            case R.id.main_release_trends_shareToFriend:// 分享好友

                break;
            case R.id.title_right_icon:// 提交动态
                submit(isVideo);
                break;

        }
    }

    private void submitVideoRelease(final String picUrl) {/*

															mProgress.setVisibility(View.VISIBLE);
															try {
															RequestParams params = new RequestParams();
															File file = new File(mVideoPath);
															FileInputStream fStream = new FileInputStream(file);
															params.put("video", file);
															Connect2Service.post(CommonInface.BASE_URL
															+ CommonInface.URL_UPLOAD_VIDEO, params,
															new BaseJsonHttpResponseHandler() {

															@Override
															public void onFailure(int arg0, Header[] arg1,
															Throwable throwable, String arg3, Object arg4) {
															Toast.makeText(ReleaseTrendsActivity.this,
															"上传失败" + throwable.toString(),
															Toast.LENGTH_SHORT).show();

															}

															@Override
															public void onSuccess(int arg0, Header[] arg1,
															String responseString, Object arg3) {
															Gson gson = new Gson();
															Type type = new TypeToken<ResultData<String>>() {
															}.getType();
															ResultData<String> fromJson = gson.fromJson(
															responseString, type);
															String url = fromJson.getData();
															releaseContentSend(picUrl + "," + url);
															}

															@Override
															protected Object parseResponse(String arg0, boolean arg1)
															throws Throwable {
															return null;
															}
															});
															} catch (FileNotFoundException e) {
															e.printStackTrace();
															}

															*/
    }

    public File saveBitmap(File url, Bitmap bm) {
        File f = new File(url.getParentFile(), url.getName().substring(0,
                url.getName().length() - 4)
                + ".jpg");
        if (f.exists()) {
            try {
                FileOutputStream out = new FileOutputStream(f);
                bm.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    private void submit(final boolean isVideo) {/*
                                                try {
												mProgress.setVisibility(View.VISIBLE);
												RequestParams params = new RequestParams();
												if (isVideo) {
												MediaMetadataRetriever media = new MediaMetadataRetriever();
												media.setDataSource(mVideoPath);
												Bitmap bitmap = media.getFrameAtTime();
												params.put("file", saveBitmap(new File(mVideoPath), bitmap));
												} else {
												File[] files = new File[mSharePicUrls.size() - 1];
												for (int i = 0; i < mSharePicUrls.size() - 1; i++) {
												String compressBitmapPath = ImageCompression
												.compressBitmap(ReleaseTrendsActivity.this,
												mSharePicUrls.get(i),
												CommonInface.LOC_CACHE_PATH
												+ CommonInface.IMG_PATH, 480 / 2,
												800 / 2, false);
												files[i] = new File(compressBitmapPath);
												params.put("files", files);
												}

												}
												Connect2Service.post(CommonInface.BASE_URL
												+ CommonInface.URL_UPLOAD_PIC, params,
												new BaseJsonHttpResponseHandler() {

												@Override
												public void onFailure(int statusCode, Header[] headers,
												Throwable throwable, String rawJsonData,
												Object errorResponse) {
												Toast.makeText(ReleaseTrendsActivity.this,
												"上传失败" + throwable.toString(),
												Toast.LENGTH_SHORT).show();
												}

												@Override
												public void onSuccess(int statusCode, Header[] headers,
												String responseString, Object response) {
												Gson gson = new Gson();
												Type type = new TypeToken<ResultData<String>>() {
												}.getType();
												ResultData<String> fromJson = gson.fromJson(
												responseString, type);
												String picUrl = fromJson.getData();
												if (isVideo) {
												submitVideoRelease(picUrl);

												} else {
												releaseContentSend(picUrl);
												}

												}

												@Override
												protected Object parseResponse(String rawJsonData,
												boolean isFailure) throws Throwable {
												return null;
												}

												});
												} catch (FileNotFoundException e) {
												e.printStackTrace();
												}

												*/
    }

    private void releaseContentSend(String url) {/*
                                                    String content = mShareContent.getText().toString().trim();
													float latitude = Float.parseFloat(MySharedCache.get(
													CommonInface.CURRENT_LATITUDE, "0"));
													float longitude = Float.parseFloat(MySharedCache.get(
													CommonInface.CURRENT_LONGITUDE, "0"));
													String location = mSelectLocation.getText().toString().trim();
													String sendLocation = location;
													if (location.equals("选择地点") || location.equals("不选择位置")) {
													sendLocation = "";
													}
													final ReleaseSendInfo sendInfo = new ReleaseSendInfo(content, url,
													latitude, longitude, sendLocation);
													RequestParams params = new RequestParams();

													Gson gson = new Gson();
													new MyAsyncTask<ResultData<String>>(ReleaseTrendsActivity.this) {

													@Override
													public ResultData<String> callService() throws IOException,
													JsonParseException, BizException, ServiceException {
													return new ReleaseContentService().sendReleaseContent(sendInfo);
													}

													@Override
													public void onLoaded(ResultData<String> result) throws Exception {

													mProgress.setVisibility(View.GONE);
													if (result.isSuccess()) {
													Toast.makeText(ReleaseTrendsActivity.this, "发表成功",
													Toast.LENGTH_SHORT).show();
													Log.i("TAG", result.isSuccess() + "");
													ReleaseTrendsActivity.this.finish();
													} else {
													Toast.makeText(ReleaseTrendsActivity.this, "发表失败",
													Toast.LENGTH_SHORT).show();
													}
													}
													}.executeTask();

													*/
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {/*
                        if (position == CommonInface.COUNT_RESLEASE_PIC + 1) {
						Toast.makeText(this, "已经添加到最大张数，不能再添加了", Toast.LENGTH_SHORT).show();
						}
						if (position == mGridLastVisibleItem - 1
						&& position == (mSharePicUrls.size() == 1 ? 0 : mSharePicUrls
						.size() - 1)
						&& position != CommonInface.COUNT_RESLEASE_PIC
						&& mSharePicUrls.size() != CommonInface.COUNT_RESLEASE_PIC + 1) {
						// Intent intent = new Intent(ReleaseTrendsActivity.this,
						// ReleaseImageActivity.class);
						// startActivity(intent);

						setAlert();

						} else {

						}

						*/
    }

    private void setAlert() {/*
                                View popView = LayoutInflater.from(this).inflate(
								R.layout.popwin_add_image, null);

								AlertDialog.Builder builder = new AlertDialog.Builder(this);
								builder.setView(LayoutInflater.from(this).inflate(
								R.layout.popwin_add_image, null));
								final AlertDialog alert = builder.create();
								alert.getWindow().setGravity(Gravity.BOTTOM);
								alert.getWindow().setLayout(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT);

								alert.show();
								WindowManager.LayoutParams params = alert.getWindow().getAttributes();
								params.width = WindowManager.LayoutParams.MATCH_PARENT;
								params.height = WindowManager.LayoutParams.WRAP_CONTENT;
								alert.getWindow().setAttributes(params);

								alert.getWindow().findViewById(R.id.popwin_add_image_photo)
								.setOnClickListener(new OnClickListener() {
								// 从相册选择
								@Override
								public void onClick(View v) {
								Intent intent1 = new Intent(ReleaseTrendsActivity.this,
								ReleaseImageActivity.class);
								intent1.putExtra("PICTOTAL", mSharePicUrls.size() - 1);
								startActivityForResult(intent1, PHOTO);
								alert.dismiss();
								}
								});
								alert.getWindow().findViewById(R.id.popwin_add_image_camera)
								.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
								// 利用系统自带的相机应用:拍照
								if (mSharePicUrls.size() > CommonInface.COUNT_RESLEASE_PIC) {
								Toast.makeText(ReleaseTrendsActivity.this,
									"已经到达最大限制，不能再拍照", Toast.LENGTH_SHORT)
									.show();
								} else {
								SysCamera();
								}

								alert.dismiss();

								}

								});
								alert.getWindow().findViewById(R.id.popwin_add_image_cancel)
								.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
								alert.dismiss();

								}
								});
								*/
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        this.mGridLastVisibleItem = totalItemCount;
    }

    private static File getOutputMediaFile(int type) {

        File mediaStorageDir = null;
        try {
            mediaStorageDir = new File(
                    Environment.getExternalStorageDirectory() + File.separator
                            + "ifootball", "ifootball_images");

            Log.d(LOG_TAG, "Successfully created mediaStorageDir: "
                    + mediaStorageDir);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error in Creating mediaStorageDir: "
                    + mediaStorageDir);
        }

        if (mediaStorageDir != null && !mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(LOG_TAG,
                        "failed to create directory, check if you have the WRITE_EXTERNAL_STORAGE permission");
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
            case PHOTO:
                if (data.getStringArrayListExtra("SELECT_IMAGES").isEmpty()) {
                    return;
                }
                mSharePicUrls.addAll(0,
                        data.getStringArrayListExtra("SELECT_IMAGES"));
                mAdatpter.update();

                break;
            case RESULT_OK: // 拍照成功回调
                if (CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE == requestCode) {
                    try {
                        mSharePicUrls.add(
                                0,
                                fileUri.toString().substring(7,
                                        fileUri.toString().length()));
                        mAdatpter.update();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }

                }
            case RESULT_CANCELED:// 用户取消的拍照

                break;
            case RESULT_FIRST_USER:// 拍照失败
                break;
            case ADDRESS:// 选择地点
            /*if (data.getSerializableExtra("ADDRESS").equals("")) {
                return;
			}
			mSelectLocation.setText(((ReleaseSelectAddress) data
					.getSerializableExtra("ADDRESS")).getLocationName());*/
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSharePicUrls.clear();
        mShareVideo.stopPlayback();
    }

}
