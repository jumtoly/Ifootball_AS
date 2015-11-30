package com.ifootball.app.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ifootball.app.entity.CustomerInfo;
import com.ifootball.app.framework.cache.MySharedCache;
import com.ifootball.app.listener.CheckLoginListener;
import com.neweggcn.lib.json.Gson;

public class CustomerUtil {
	public static final String INTENT_ONLOGIN_SUCCESSFULLY_CALLBACK="INTENT_ONLOGIN_SUCCESSFULLY_CALLBACK";
	public static final String INTENT_ONLOGIN_CALLBACK_PARAMS="INTENT_ONLOGIN_CALLBACK_PARAMS";
	
	private static final String CUSTOMER_LOGIN_ACCOUNT_KEY="CUSTOMER_LOGIN_ACCOUNT";
	private static final String CUSTOMER_UTIL_DATA_KEY="CUSTOMER_UTIL_DATA";
	private static final String AUTHENTICATION_KEY = "X-ifootball-Authentication";
	private static final String CUSTOMER_IS_VISITOR_KEY="CUSTOMER_IS_VISITOR";
	
	public static void cacheCustomerInfo(CustomerInfo info){
		if (info!=null) {
			MySharedCache.put(CUSTOMER_UTIL_DATA_KEY, new Gson().toJson(info));
		}
	}
	
	public static CustomerInfo getCustomerInfo() {
		String json=MySharedCache.get(CUSTOMER_UTIL_DATA_KEY, "");
		if (!StringUtil.isEmpty(json)) {
			return new Gson().fromJson(json, CustomerInfo.class);
		}
		
		return null;
	}
	
	public static void clearCustomerInfo() {
		MySharedCache.put(CUSTOMER_UTIL_DATA_KEY, new Gson().toJson(new CustomerInfo()));
		clearAuthenTick();
	}
	
	public static void cacheAuthenTick(String authenTick){
		MySharedCache.put(AUTHENTICATION_KEY, authenTick);
	}
	
	public static void clearAuthenTick() {
		MySharedCache.put(AUTHENTICATION_KEY, null);
	}
	
	public static String getAuthenTick() {
		return MySharedCache.get(AUTHENTICATION_KEY, null);
	}
	
	/**
	 * 判断是否登录 登录true 未登录 false
	 * @return
	 */
	public static boolean isLogin() {
		return !StringUtil.isEmpty(CustomerUtil.getAuthenTick());
	}
	
	/**
	 * 判断是否登录并回调
	 * @param activity
	 * @param loginClass
	 * @param listener
	 * @param bundle
	 */
	public static void checkLogin(Activity activity, Class<?> loginClass,CheckLoginListener listener, Bundle bundle) {
		if (!isLogin()) {
			Intent intent = new Intent(activity, loginClass);
			intent.putExtra(INTENT_ONLOGIN_SUCCESSFULLY_CALLBACK, listener);
			intent.putExtra(INTENT_ONLOGIN_CALLBACK_PARAMS, bundle);
			activity.startActivity(intent);
		} else {
			listener.OnLogined(getCustomerInfo(), bundle);
		}
	}
	
	/**
	 * 判断是否登录并回调
	 * @param activity
	 * @param loginClass
	 * @param listener
	 */
	public static void checkLogin(Activity activity, Class<?> loginClass,CheckLoginListener listener) {
		checkLogin(activity,loginClass,listener,null);
	}
	
	/**
	 * 缓存登录账号
	 * @param account
	 */
	public static void cacheLoginAccount(String account) {
		MySharedCache.put(CUSTOMER_LOGIN_ACCOUNT_KEY, account);
	}
	/**
	 * 获取登录账号
	 * @return
	 */
	public static String getLoginAccount() {
		return MySharedCache.get(CUSTOMER_LOGIN_ACCOUNT_KEY, "");
	}
	
	/**
	 * 退出登录需要清除的缓存
	 */
	public static void exitLogin(){
		clearAuthenTick();
		if (getIsVisitor()) {
			LoginStackUtil.finishAll();
		}
		CustomerUtil.cacheIsVisitorLogin(false);
	}
	/**
	 * 游客登录
	 * @param isVisitor
	 */
	public static void cacheIsVisitorLogin(boolean isVisitor){
		MySharedCache.put(CUSTOMER_IS_VISITOR_KEY, isVisitor);
	}
	/**
	 * 游客登录
	 * @return
	 */
	public static boolean getIsVisitor() {
		return MySharedCache.get(CUSTOMER_IS_VISITOR_KEY, false);
	}
	public static void addVisitorView(Activity activity) {
		if (getIsVisitor()) {
			LoginStackUtil.addActivity(activity);
		}
	}
	public static void removeVisitorView(Activity activity) {
		if (getIsVisitor()) {
			LoginStackUtil.removeActivity(activity);
		}
	}
}
