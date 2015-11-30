package com.ifootball.app.util;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class CookieUtil {
	
	public static void setCookie(Context context){
		if (!StringUtil.isEmpty(CustomerUtil.getAuthenTick())) {
			CookieSyncManager.createInstance(context);  
			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.setAcceptCookie(true);
			cookieManager.setCookie("http://www.kjt.com/", CustomerUtil.getAuthenTick()+";path=/");
			CookieSyncManager.getInstance().sync();
		}
	}
}
