package com.ifootball.app.baseapp;

import android.app.Application;

import com.ifootball.app.http.BetterHttp;
import com.ifootball.app.utils.VersionUtil;

public class BaseApp extends Application {
    private static String mUserAgent;
    private static BaseApp instance;

    private static int mNum = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        if (mNum == 0) {
            instance = this;

            mUserAgent = String.format("Android Phone/%s", VersionUtil.getCurrentVersion());
            setupBetterHttp();
            mNum = mNum + 1;
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
        BetterHttp.setDefaultHeader(BetterHttp.X_HIGHRESOLUTION,
                String.valueOf(densityDpi));
        BetterHttp.setDefaultHeader(BetterHttp.X_OSVERSION,
                android.os.Build.VERSION.RELEASE);
        BetterHttp.enableGZIPEncoding();
    }
}