package com.ifootball.app.util;

import android.content.Context;

import com.ifootball.app.framework.widget.MyToast;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;


public class WXLoginUtil {
	
	private static final String APP_SECRET_KEY="56de1ff074d64685336089cd7705b807";//开发平台设置的密钥
	
	private IWXAPI mIWXAPI;
	private Context mContext;
	
	private String mNonceStr;
	
	public WXLoginUtil(Context context){
		if(mIWXAPI==null){
			mNonceStr=null;
			mContext=context;
			mIWXAPI=WXUtil.initWXAPI(context);
		}
	}
	
	public void login() {
		if (mIWXAPI != null) {
			if (mIWXAPI.getWXAppSupportAPI()==0) {
				MyToast.show(mContext, "请安装微信APP");
				return;
			}
		}
		mNonceStr=WXUtil.getNonceStr();
		SendAuth.Req req = new SendAuth.Req();
		req.scope = "snsapi_userinfo";//应用授权作用域，如获取用户个人信息则填写snsapi_userinfo
		req.state = mNonceStr;//用于保持请求和回调的状态，授权请求后原样带回给第三方
		mIWXAPI.sendReq(req);
	}
	
}
