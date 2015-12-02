package com.ifootball.app.activity.login;

import android.os.Bundle;
import android.webkit.WebView;

import com.ifootball.app.R;
import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.framework.widget.TitleBarView;

public class RegisterAgreementActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        TitleBarView view = (TitleBarView) findViewById(R.id.agreement_titlebar);
        view.setViewData(getResources().getDrawable(R.mipmap.ico_backspace),
                "用户协议", null);
        view.setActivity(this);

    }

}
