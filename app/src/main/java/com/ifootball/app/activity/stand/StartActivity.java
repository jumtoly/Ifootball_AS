package com.ifootball.app.activity.stand;

import android.app.Activity;
import android.os.Bundle;

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
        mCountDownTimerUtil.start();
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
