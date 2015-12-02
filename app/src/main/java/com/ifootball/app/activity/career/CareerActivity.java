package com.ifootball.app.activity.career;

import android.os.Bundle;
import android.view.KeyEvent;

import com.ifootball.app.R;
import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.framework.widget.NavigationHelper;
import com.ifootball.app.util.ExitAppUtil;

public class CareerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        putContentView(R.layout.activity_career, "", NavigationHelper.CAREER,
                true, true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ExitAppUtil.exit(this);

        }
        return super.onKeyDown(keyCode, event);
    }

}
