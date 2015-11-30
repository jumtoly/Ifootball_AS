package com.ifootball.app.activity.stand;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.ifootball.app.util.ExitAppUtil;
import com.ifootball.app.util.IntentUtil;
import com.ifootball.app.R;

public class FirstStartAppActivity extends Activity {
    private int[] images = {R.mipmap.startappimg1, R.mipmap.startappimg2,
            R.mipmap.startappimg3};
    private ViewPager mViewPager;
    private boolean isRedirect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start_app_layout);
        mViewPager = (ViewPager) findViewById(R.id.first_start_app_viewpager);
        HomeViewPager productViewPager = new HomeViewPager(images, this);
        mViewPager.setAdapter(productViewPager);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 0 && isRedirect
                        && mViewPager.getCurrentItem() == (images.length - 1)) {
                    IntentUtil.redirectToNextActivity(
                            FirstStartAppActivity.this, StandActivity.class);
                    FirstStartAppActivity.this.finish();
                }
                if (state == 0
                        && mViewPager.getCurrentItem() == (images.length - 1)) {
                    isRedirect = true;
                }
                if (state == 2
                        || mViewPager.getCurrentItem() != (images.length - 1)) {
                    isRedirect = false;
                }
            }
        });
      /*  mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                if (arg0 == 0 && isRedirect
                        && mViewPager.getCurrentItem() == (images.length - 1)) {
                    IntentUtil.redirectToNextActivity(
                            FirstStartAppActivity.this, StandActivity.class);
                    FirstStartAppActivity.this.finish();
                }
                if (arg0 == 0
                        && mViewPager.getCurrentItem() == (images.length - 1)) {
                    isRedirect = true;
                }
                if (arg0 == 2
                        || mViewPager.getCurrentItem() != (images.length - 1)) {
                    isRedirect = false;
                }
            }
        });*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isExitingAppliation(keyCode, event)) {
            ExitAppUtil.exit(FirstStartAppActivity.this);
        }

        return super.onKeyDown(keyCode, event);
    }

    private boolean isExitingAppliation(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0;
    }

    private class HomeViewPager extends PagerAdapter {
        private int[] images;
        private LayoutInflater mLayoutInflater;

        public HomeViewPager(int[] images, Context context) {
            super();
            this.images = images;
            mLayoutInflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public void destroyItem(View collection, int position, Object view) {
            ((ViewPager) collection).removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public Object instantiateItem(View collection, final int position) {
            View view = mLayoutInflater.inflate(
                    R.layout.first_startapp_viewpager_cell, null);
            ImageView imageView = (ImageView) view
                    .findViewById(R.id.product_viewpager_img);
            imageView.setImageResource(images[position]);
            if (position == images.length - 1) {
                view.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        IntentUtil
                                .redirectToNextActivity(
                                        FirstStartAppActivity.this,
                                        StandActivity.class);
                        FirstStartAppActivity.this.finish();
                    }
                });
            }
            ((ViewPager) collection).addView(view, 0);
            return view;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mViewPager.clearOnPageChangeListeners();
    }
}
