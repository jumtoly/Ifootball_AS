package com.ifootball.app.activity.base;

import android.app.Activity;
import android.os.Bundle;

import com.ifootball.app.R;
import com.ifootball.app.util.VersionUtil;
import com.ifootball.app.R;

public class VersionActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.version_layout);
        VersionUtil.getInstance().update(this);
    }
}
