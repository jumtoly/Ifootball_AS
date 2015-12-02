package com.ifootball.app.activity.stand;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.ifootball.app.R;
import com.ifootball.app.activity.release.ReleaseImageActivity;
import com.ifootball.app.entity.release.PictureInfo;
import com.ifootball.app.framework.widget.HackyViewPager;
import com.ifootball.app.framework.widget.release.ImageLoader;
import com.ifootball.app.util.ImageLoaderUtil;
import com.ifootball.app.util.IntentUtil;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


public class SeeImageActivity extends Activity {
    public static final String SIGN_CAMERA_POSITION = "SIGN_CAMERA_POSITION";
    private static final String ISLOCKED_ARG = "isLocked";
    public static final int REQUEST_SIGN = 201;
    private static final String SIGN_CAMERA_REQUEST_DATA = "SIGN_CAMERA_REQUEST_DATA";
    public static final String SIGN_STAND_POSITION = "SIGN_STAND_POSITION";
    public static final String SIGN_STAND_IMAGES = "SIGN_STAND_IMAGES";


    private ViewPager mViewPager;

    private float startX;
    private float startY;
    private float endX;
    private float endY;
    public boolean isOnclick;

    private Bundle rostrumBundle;
    private Bundle detailsBundle;

    private int mPosition;

    private ArrayList<String> mPics;
    private ArrayList<PictureInfo> mPictures; //是详情页面发过来的预览图

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeimage);
        mViewPager = (HackyViewPager) findViewById(R.id.see_image_pager);
        setContentView(mViewPager);
        getIntentData();
        mViewPager.setAdapter(new SamplePagerAdapter(this));
        mViewPager.setCurrentItem(mPosition);
        if (savedInstanceState != null) {
            boolean isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG,
                    false);
            ((HackyViewPager) mViewPager).setLocked(isLocked);
        }
    }

    public void getIntentData() {
        mPosition = getIntent().getIntExtra(SIGN_STAND_POSITION, 0);
//        mPics = getIntent().getStringArrayExtra(SIGN_CAMERA_POSITION);
        mPics = (ArrayList<String>) getIntent().getSerializableExtra(SIGN_STAND_IMAGES);
    }

    class SamplePagerAdapter extends PagerAdapter {

        private Context mContext;

        public SamplePagerAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {

            return mPics != null ? mPics.size()
                    : (mPictures != null ? mPictures.size() : 0);
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            PhotoView photoView = new PhotoView(container.getContext());
            LayoutParams params = new LayoutParams(displayMetrics.widthPixels,
                    displayMetrics.heightPixels);
            photoView.setLayoutParams(params);
            //TODO 拍照后的图片显示
//            ImageLoader.getInstance().loadImage(mPics.get(0), photoView);
            if (mPics != null && mPics.size() > 0) {
                ImageLoaderUtil.displayImage(
                        mPics.get(position).replace("p200", "Original"),
                        photoView, 0);
            } else if (mPictures != null) {
                ImageLoaderUtil.displayImage(mPictures.get(position)
                        .getPicUrl(), photoView, 0);
            }

            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {

                @Override
                public void onViewTap(View arg0, float arg1, float arg2) {
                    ((Activity) mContext).finish();
                    ((Activity) mContext).overridePendingTransition(
                            R.anim.see_enter_scale, R.anim.see_out_scale);
                }
            });
            container.addView(photoView, LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    private boolean isViewPagerActive() {
        return (mViewPager != null && mViewPager instanceof HackyViewPager);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean(ISLOCKED_ARG,
                    ((HackyViewPager) mViewPager).isLocked());
        }
        super.onSaveInstanceState(outState);
    }
}
