package com.ifootball.app.activity.green;

import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.ifootball.app.R;
import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.framework.widget.TitleBarView;

public class MapActivity extends BaseActivity {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true;// 是否首次定位
    private BitmapDescriptor mCurrentMarker;// 用户自定位图标
    private LocationMode mCurrentMode;
    private LocationClient mLocClient;
    private MyLocationListenner myListener = new MyLocationListenner();
    private List<String> mLoactions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        TitleBarView titelView = (TitleBarView) findViewById(R.id.frg_map_titlebar);
        titelView.setViewData(
                getResources().getDrawable(R.mipmap.ico_backspace), "地图页面",
                null);
        titelView.setActivity(this);

        mLoactions = (List<String>) getIntent()
                .getSerializableExtra("LOCATION");

        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        UiSettings uiSettings = mBaiduMap.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        mBaiduMap.setMyLocationEnabled(true);

        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption locationOption = new LocationClientOption();
        locationOption.setOpenGps(true);// 打开gps
        locationOption.setCoorType("bd09ll"); // 设置坐标类型
        locationOption.setIsNeedAddress(true);
        mLocClient.setLocOption(locationOption);
        mLocClient.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
    }

    class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            // if (isFirstLoc)
            // {
            isFirstLoc = false;
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            Log.i("Location",
                    location.getLatitude() + ":" + location.getLongitude());
            float f = mBaiduMap.getMaxZoomLevel();// 19.0
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, f - 3);
            mBaiduMap.animateMapStatus(u);
            //
            // // 定义Maker坐标点
            // LatLng point = new LatLng(39.963175, 116.400244);
            // // 构建Marker图标
            if (mLoactions != null) {
                for (int i = 0; i < mLoactions.size(); i++) {
                    String[] split = mLoactions.get(i).split(":");
                    LatLng latLng = new LatLng(Float.parseFloat(split[0]),
                            Float.parseFloat(split[1]));
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.mipmap.ico_marka);
                    // 构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(latLng).icon(bitmap);
                    // 在地图上添加Marker，并显示
                    mBaiduMap.addOverlay(option);
                }
            }
            String country = location.getCity();
            // }
        }
    }

}
