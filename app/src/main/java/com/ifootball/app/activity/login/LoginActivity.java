package com.ifootball.app.activity.login;

import java.io.IOException;

import com.ifootball.app.R;
import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.entity.BizException;
import com.ifootball.app.entity.CustomerInfo;
import com.ifootball.app.entity.ResultData;
import com.ifootball.app.framework.widget.MyToast;
import com.ifootball.app.framework.widget.TitleBarView;
import com.ifootball.app.util.CustomerUtil;
import com.ifootball.app.util.IntentUtil;
import com.ifootball.app.util.MyAsyncTask;
import com.ifootball.app.webservice.CustomerService;
import com.ifootball.app.webservice.ServiceException;
import com.neweggcn.lib.json.JsonParseException;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class LoginActivity extends BaseActivity implements OnClickListener {

    private EditText mTelphoneEditText;
    private EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TitleBarView view = (TitleBarView) findViewById(R.id.login_titlebar);
        view.setViewData(getResources().getDrawable(R.mipmap.ico_backspace),
                "登陆", null);
        view.setActivity(this);

        findView();
    }

    private void findView() {
        mTelphoneEditText = (EditText) findViewById(R.id.login_tellphone);
        mPasswordEditText = (EditText) findViewById(R.id.login_password);

        findViewById(R.id.login_tellogin_btn).setOnClickListener(this);
        findViewById(R.id.login_toreginster_btn).setOnClickListener(this);
        findViewById(R.id.login_tofindpassword_btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_tellogin_btn:
                login();
                break;
            case R.id.login_toreginster_btn:
                IntentUtil.redirectToNextActivity(LoginActivity.this,
                        RegisterActivity.class);
                break;
            case R.id.login_tofindpassword_btn:
                IntentUtil.redirectToNextActivity(LoginActivity.this,
                        FindPasswordActivity.class);
                break;
            default:
                break;
        }
    }

    private void login() {
        if (mTelphoneEditText.getText().toString() == "") {
            MyToast.show(LoginActivity.this, "手机号输入不正确", 1000);
            return;
        }

        if (mPasswordEditText.getText().toString() == "") {
            MyToast.show(LoginActivity.this, "请输入位密码", 1000);
            return;
        }

        new MyAsyncTask<ResultData<CustomerInfo>>(this) {

            @Override
            public ResultData<CustomerInfo> callService() throws IOException,
                    JsonParseException, BizException, ServiceException {
                return new CustomerService().login(mTelphoneEditText.getText()
                        .toString(), mPasswordEditText.getText().toString(), 1);

            }

            @Override
            public void onLoaded(ResultData<CustomerInfo> result)
                    throws Exception {
                if (!result.isSuccess()) {
                    MyToast.show(LoginActivity.this, result.getMessage(), 1000);
                } else {
                    CustomerUtil.cacheCustomerInfo(result.getData());
                    CustomerUtil.cacheLoginAccount(result.getData()
                            .getCellPhone());
                    LoginActivity.this.finish();
                    MyToast.show(LoginActivity.this, "登陆成功", 1000);
                }

            }

        }.executeTask();
    }
}
