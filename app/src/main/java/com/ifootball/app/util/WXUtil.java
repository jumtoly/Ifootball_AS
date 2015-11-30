package com.ifootball.app.util;

import java.util.Random;

import android.content.Context;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXUtil {
	public static final String APP_WEI_XIN_ID = "wxdd00da1c59bab3a3";// 微信KEY

	/**
	 * 初始化微信
	 * @param context
	 * @return
	 */
	public static IWXAPI initWXAPI(Context context) {
		IWXAPI wxApi = createWXAPI(context);
		wxApi.registerApp(APP_WEI_XIN_ID);
		
		return wxApi;
	}
	
	/**
	 * 创建微信API
	 * @param context
	 * @return
	 */
	public static IWXAPI createWXAPI(Context context){
		return WXAPIFactory.createWXAPI(context,APP_WEI_XIN_ID, true);
	}
	
	/**
	 * 获取随机字符串
	 * @return
	 */
	public static String getNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	
	/**
	 * 获取时间戳
	 * @return
	 */
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis()/1000);
	}
}
