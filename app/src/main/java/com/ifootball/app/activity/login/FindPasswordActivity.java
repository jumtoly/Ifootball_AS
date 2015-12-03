package com.ifootball.app.activity.login;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ifootball.app.R;
import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.entity.BizException;
import com.ifootball.app.entity.ResultData;
import com.ifootball.app.framework.widget.MyToast;
import com.ifootball.app.framework.widget.TitleBarView;
import com.ifootball.app.util.MyAsyncTask;
import com.ifootball.app.util.StringUtil;
import com.ifootball.app.webservice.CustomerService;
import com.ifootball.app.webservice.ServiceException;
import com.ifootball.app.webservice.green.GreenService;
import com.neweggcn.lib.json.JsonParseException;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class FindPasswordActivity extends BaseActivity implements
        OnClickListener {

    private EditText mTelphoneEditText;
    private EditText mCodeEditText;
    private EditText mPasswordEditText;
    private EditText mRePasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassword);
        TitleBarView view = (TitleBarView) findViewById(R.id.findpassword_titlebar);
        view.setViewData(getResources().getDrawable(R.mipmap.ico_backspace),
                "找回密码", null);
        view.setActivity(this);

        findView();
    }

    private void findView() {
        mTelphoneEditText = (EditText) findViewById(R.id.findpassword_telphone);
        mCodeEditText = (EditText) findViewById(R.id.findpassword_code);
        mPasswordEditText = (EditText) findViewById(R.id.findpassword_password);
        mRePasswordEditText = (EditText) findViewById(R.id.findpassword_repassword);
        findViewById(R.id.findpassword_sendcodebtn).setOnClickListener(this);
        findViewById(R.id.findpassword_btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.findpassword_sendcodebtn:
                sendCode((Button) v);
                break;
            case R.id.findpassword_btn:
                register();
                break;
            default:
                break;
        }
    }

    private void register() {
        if (mTelphoneEditText.getText().toString() == "") {
            MyToast.show(FindPasswordActivity.this, "手机号输入不正确", 1000);
            return;
        }
        if (mCodeEditText.getText().toString() == "") {
            MyToast.show(FindPasswordActivity.this, "请输入手机验证码", 1000);
            return;
        }
        if (mPasswordEditText.getText().toString().length() > 20
                || mPasswordEditText.getText().toString().length() < 6) {
            MyToast.show(FindPasswordActivity.this, "请输入6-20位密码", 1000);
            return;
        }
        if (!mPasswordEditText.getText().toString()
                .equals(mRePasswordEditText.getText().toString())) {
            MyToast.show(FindPasswordActivity.this, "两次输入密码不一致", 1000);
            return;
        }

        new MyAsyncTask<ResultData<String>>(this) {

            @Override
            public ResultData<String> callService() throws IOException,
                    JsonParseException, BizException, ServiceException {
                return new CustomerService().findPassword(mTelphoneEditText
                        .getText().toString(), mPasswordEditText.getText()
                        .toString(), "", mCodeEditText.getText().toString());
            }

            @Override
            public void onLoaded(ResultData<String> result) throws Exception {
                if (!result.isSuccess()) {
                    MyToast.show(FindPasswordActivity.this,
                            result.getMessage(), 1000);
                } else {
                    mTelphoneEditText.setText("");
                    mCodeEditText.setText("");
                    mPasswordEditText.setText("");
                    mRePasswordEditText.setText("");
                    MyToast.show(FindPasswordActivity.this, "注册成功", 1000);
                }

            }

        }.executeTask();
    }

    private void sendCode(final Button v) {
        if (!StringUtil.isPhone(mTelphoneEditText.getText().toString())) {
            MyToast.show(FindPasswordActivity.this, "手机号输入不正确", 1000);
        } else {
            v.setClickable(false);
            new MyAsyncTask<ResultData<String>>(this) {

                @Override
                public ResultData<String> callService() throws IOException,
                        JsonParseException, BizException, ServiceException {
                    return new GreenService().sendValidateCode(mTelphoneEditText.getText().toString());
                }

                @Override
                public void onLoaded(ResultData<String> result)
                        throws Exception {
                    if (!result.isSuccess()) {
                        MyToast.show(FindPasswordActivity.this,
                                result.getMessage(), 1000);
                    } else {
                        final Timer timer = new Timer();
                        timer.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int s = Integer.parseInt(v.getTag()
                                                .toString()) - 1;
                                        v.setTag(s);
                                        v.setText(String.format("%s秒后重新发送", s));
                                        if (s == 0) {
                                            v.setText("获取验证码");
                                            v.setClickable(true);
                                            v.setTag("60");
                                            timer.cancel();
                                        }
                                    }
                                });
                            }
                        }, 1000, 1000);
                    }
                }
            }.executeTask();

        }
    }
}
