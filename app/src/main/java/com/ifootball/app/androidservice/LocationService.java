package com.ifootball.app.androidservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.ifootball.app.common.Common;
import com.ifootball.app.framework.cache.MySharedCache;

public class LocationService extends Service {

    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private static String currentCity;
    private MyBinder myBinder;

    public class MyBinder extends Binder {
        public LocationService getLocationService() {
            return LocationService.this;
        }

    }

    public static String getCurrentCity() {
        return currentCity;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        mLocClient = new LocationClient(this);

        mLocClient.registerLocationListener(myListener);
        LocationClientOption locationOption = new LocationClientOption();
        locationOption.setLocationMode(LocationMode.Battery_Saving);
        locationOption.setOpenGps(true);// 打开gps
        locationOption.setCoorType("bd09ll"); // 设置坐标类型
        locationOption.setScanSpan(1000 * 60 * 60);
        locationOption.setIsNeedAddress(true);
        mLocClient.setLocOption(locationOption);
        mLocClient.start();

    }

    class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location == null)
                return;
            MySharedCache.put(Common.CURRENT_CITY.name(), location.getCity());
            MySharedCache.put(Common.CURRENT_LATITUDE.name(), String.valueOf(location.getLatitude()));
            MySharedCache.put(Common.CURRENT_LONGITUDE.name(), String.valueOf(location.getLongitude()));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocClient.stop();
    }

}
