package com.ifootball.app.util;

import com.ifootball.app.framework.cache.MySharedCache;

public class ForgotPasswordUtil {
	private static final String VALIDATE_CODE_COOKIE_KEY="VALIDATE_CODE_COOKIE";
	
	/**
	 * 缓存COOKIE
	 * 
	 * @param cookie
	 */
	public static void cacheCookie(String cookie) {
		MySharedCache.put(VALIDATE_CODE_COOKIE_KEY, cookie);
	}

	/**
	 * 获取COOKIE
	 * 
	 * @return
	 */
	public static String getCookie() {
		return MySharedCache.get(VALIDATE_CODE_COOKIE_KEY, null);
	}

	/**
	 * 清除COOKIE
	 */
	public static void clearCookie() {
		MySharedCache.put(VALIDATE_CODE_COOKIE_KEY, null);
	}
}
