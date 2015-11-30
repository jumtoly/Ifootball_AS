package com.ifootball.app.base;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.Poi;
import com.ifootball.app.R;
import com.ifootball.app.androidservice.LocationService;
import com.ifootball.app.common.Common;
import com.ifootball.app.framework.cache.MyFileCache;
import com.ifootball.app.framework.cache.MySharedCache;
import com.ifootball.app.framework.http.BetterHttp;
import com.ifootball.app.util.CrashHandler;
import com.ifootball.app.util.ImageUrlUtil;
import com.ifootball.app.util.VersionUtil;
import com.ifootball.app.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.List;

public class BaseApp extends Application {
    private static String mUserAgent;
    private static BaseApp instance;

    private static int mNum = 0;

    public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;

    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();

        if (mNum == 0) {
            instance = this;

            initImageLoader(this);
            MyFileCache.install(getApplicationContext());

            mUserAgent = getResources().getString(R.string.user_agent,
                    VersionUtil.getCurrentVersion());

            setupBetterHttp();

            ImageUrlUtil.setContext(this);

            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(this);

            mNum = mNum + 1;
        }
        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);

//        startService(new Intent(this, LocationService.class));

    }

    /**
     * 实现实时位置回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");// 位置语义化信息
            sb.append(location.getLocationDescribe());
            List<Poi> list = location.getPoiList();// POI信息
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            MySharedCache.put(Common.CURRENT_CITY.name(), location.getCity());
            MySharedCache.put(Common.CURRENT_LATITUDE.name(), String.valueOf(location.getLatitude()));
            MySharedCache.put(Common.CURRENT_LONGITUDE.name(), String.valueOf(location.getLongitude()));
//            logMsg(sb.toString());
            Log.i("BaiduLocationApiDem", sb.toString());
        }


    }


    public static BaseApp instance() {

        return instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private static String getUserAgent() {
        return mUserAgent;
    }

    private void setupBetterHttp() {
        BetterHttp.setContext(this);
        BetterHttp.setupHttpClient();
        BetterHttp.setUserAgent(getUserAgent());
        int densityDpi = getResources().getDisplayMetrics().densityDpi;
        BetterHttp.setDefaultHeader("X-HighResolution",
                String.valueOf(densityDpi));
        BetterHttp.setDefaultHeader("X-OSVersion",
                android.os.Build.VERSION.RELEASE);

        BetterHttp.enableGZIPEncoding();
    }

    private static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging() // Not
                // necessary
                // in
                // common
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}