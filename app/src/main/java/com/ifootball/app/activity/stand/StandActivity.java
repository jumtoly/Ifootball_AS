package com.ifootball.app.activity.stand;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.activity.release.ReleaseImageActivity;
import com.ifootball.app.framework.widget.NavigationHelper;
import com.ifootball.app.util.ExitAppUtil;
import com.ifootball.app.util.IntentUtil;
import com.ifootball.app.R;

public class StandActivity extends BaseActivity implements OnClickListener,
        OnPageChangeListener {
    private static final int ROSTRUM = 0;
    private static final int NEARBY = 1;
    private static final int NEWS = 2;
    private static final int BEST_HEAT = 3;
    private ViewPager contentViewPager;
    private Button rostrumBtn;
    private Button nearbyBtn;
    private Button newsBtn;
    private Button bestHeatBtn;
    private ImageView standPlusBtn;
    private ImageView rostrumImg;
    private ImageView nearbyImg;
    private ImageView newsImg;
    private ImageView bestHeatImg;
    private List<Fragment> listViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        putContentView(R.layout.activity_stand, "", NavigationHelper.STAND, true);
        findview();
        initManager(savedInstanceState);
        inteViewPager();
    }

    private void initManager(Bundle savedInstanceState) {

    }

    private void inteViewPager() {
        listViews = new ArrayList<Fragment>();
        listViews.add(new StandRostrumFragment());
        listViews.add(new StandNearByFragment());
        listViews.add(new StandNewsFragment());
        listViews.add(new StandBestHeatFragment());
        contentViewPager.setAdapter(new HomeViewPagerAdapter(
                getSupportFragmentManager(), listViews));
        contentViewPager.setCurrentItem(ROSTRUM);
        setSelectBar(ROSTRUM);

    }

    private void findview() {
        contentViewPager = (ViewPager) findViewById(R.id.home_content_viewpager);
        rostrumBtn = (Button) findViewById(R.id.home_btn_rostrum);
        nearbyBtn = (Button) findViewById(R.id.home_btn_nearby);
        newsBtn = (Button) findViewById(R.id.home_btn_news);
        bestHeatBtn = (Button) findViewById(R.id.home_btn_best_heat);
        standPlusBtn = (ImageView) findViewById(R.id.home_btn_stand_plus);

        rostrumImg = (ImageView) findViewById(R.id.home_btn_rostrum_line);
        nearbyImg = (ImageView) findViewById(R.id.home_btn_nearby_line);
        newsImg = (ImageView) findViewById(R.id.home_btn_news_line);
        bestHeatImg = (ImageView) findViewById(R.id.home_btn_best_heat_line);

        rostrumBtn.setOnClickListener(this);
        nearbyBtn.setOnClickListener(this);
        newsBtn.setOnClickListener(this);
        bestHeatBtn.setOnClickListener(this);
        standPlusBtn.setOnClickListener(this);
        contentViewPager.setOffscreenPageLimit(1);
        contentViewPager.setOnPageChangeListener(this);
    }

    private void setSelectBar(int index) {
        restoreViewState();
        switch (index) {
            case ROSTRUM:
                rostrumBtn.getPaint().setFakeBoldText(true);
                rostrumBtn.postInvalidate();
                rostrumImg.setVisibility(View.VISIBLE);
                contentViewPager.setCurrentItem(ROSTRUM);
                break;
            case NEARBY:
                nearbyBtn.getPaint().setFakeBoldText(true);
                nearbyBtn.postInvalidate();
                nearbyImg.setVisibility(View.VISIBLE);
                contentViewPager.setCurrentItem(NEARBY);
                break;
            case NEWS:
                newsBtn.getPaint().setFakeBoldText(true);
                newsBtn.postInvalidate();
                newsImg.setVisibility(View.VISIBLE);
                contentViewPager.setCurrentItem(NEWS);
                break;
            case BEST_HEAT:
                bestHeatBtn.getPaint().setFakeBoldText(true);
                bestHeatBtn.postInvalidate();
                bestHeatImg.setVisibility(View.VISIBLE);
                contentViewPager.setCurrentItem(BEST_HEAT);
                break;

            default:
                break;
        }
    }

    private void restoreViewState() {
        rostrumBtn.getPaint().setFakeBoldText(false);
        rostrumBtn.postInvalidate();
        rostrumImg.setVisibility(View.GONE);

        nearbyBtn.getPaint().setFakeBoldText(false);
        nearbyBtn.postInvalidate();
        nearbyImg.setVisibility(View.GONE);

        newsBtn.getPaint().setFakeBoldText(false);
        newsBtn.postInvalidate();
        newsImg.setVisibility(View.GONE);

        bestHeatBtn.getPaint().setFakeBoldText(false);
        bestHeatBtn.postInvalidate();
        bestHeatImg.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_btn_rostrum:
                setSelectBar(ROSTRUM);
                break;
            case R.id.home_btn_nearby:
                setSelectBar(NEARBY);
                break;
            case R.id.home_btn_news:
                setSelectBar(NEWS);
                break;
            case R.id.home_btn_best_heat:
                setSelectBar(BEST_HEAT);
                break;
            case R.id.home_btn_stand_plus:
                setPlusBtn();
                break;

            default:
                break;
        }
    }

    private void setPlusBtn() {
        standPlusBtn.clearAnimation();
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation animation = new RotateAnimation(90, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5F,
                RotateAnimation.RELATIVE_TO_SELF, 0.5F);
        animation.setDuration(100);
        animationSet.addAnimation(animation);
        standPlusBtn.setAnimation(animation);
        final CharSequence[] items = {"图片", "视频"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        //TODO 图片activity
                        IntentUtil.redirectToNextActivity(StandActivity.this,
                                ReleaseImageActivity.class);
                        break;

                    case 1:
                        //TODO 视频activity
                        break;
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        setSelectBar(arg0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//		contentViewPager.clearOnPageChangeListeners();
    }

    class HomeViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> listViews = new ArrayList<Fragment>();

        public HomeViewPagerAdapter(FragmentManager fm, List<Fragment> listViews) {
            super(fm);
            this.listViews = listViews;
        }

        @Override
        public int getCount() {
            return listViews.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return listViews.get(arg0);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ExitAppUtil.exit(this);

        }
        return super.onKeyDown(keyCode, event);
    }
}
