package com.ifootball.app.activity.stand;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ifootball.app.base.BaseApp;
import com.ifootball.app.entity.SettingsUtil;
import com.ifootball.app.framework.cache.MySharedCache;
import com.ifootball.app.listener.CountDownTimerListener;
import com.ifootball.app.util.CountDownTimerUtil;
import com.ifootball.app.util.ExitAppUtil;
import com.ifootball.app.util.IntentUtil;
import com.ifootball.app.util.VersionUtil;
import com.ifootball.app.R;

public class StartActivity extends Activity {
    private CountDownTimerUtil mCountDownTimerUtil;
    private static final String FRIST_START_KEY = "FRIST_START";

    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.mipmap.bg);
        init();
    }

    private void init() {
        if (SettingsUtil.getVersionCheckNotify()) {
            VersionUtil.getInstance().checkVersionUpdate();
        }
        ExitAppUtil.isRemember();
        setCountDownTimer();
        location();
        mCountDownTimerUtil.start();
    }

    private void location() {
        mLocationClient = ((BaseApp) getApplication()).mLocationClient;
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，
        int span = 1000;

        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
    }

    private void setCountDownTimer() {
        mCountDownTimerUtil = new CountDownTimerUtil(2000, 1000,
                new CountDownTimerListener() {

                    @Override
                    public void onFinish() {
                        if (MySharedCache.get(FRIST_START_KEY, true)) {
                            MySharedCache.put(FRIST_START_KEY, false);
                            IntentUtil.redirectToNextActivity(
                                    StartActivity.this,
                                    FirstStartAppActivity.class);
                        } else {
                            IntentUtil.redirectToNextActivity(
                                    StartActivity.this, StandActivity.class);
                        }
                        finish();
                    }

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimerUtil != null) {
            mCountDownTimerUtil.cancel();
            mCountDownTimerUtil = null;
        }
    }
}
