package com.ifootball.app.entity;

import com.ifootball.app.framework.cache.MySharedCache;

public class SettingsUtil {
	private static String confirm_when_exit = "confirm_when_exit";
	private static String version_check_notification = "version_check_notification";
	private static String image_setting_config = "image_setting_config";

	public static Boolean getConfirmWhenExit() {
		return MySharedCache.get(confirm_when_exit, true);
	}

	public static void setConfirmWhenExit(Boolean value) {
		MySharedCache.put(confirm_when_exit, value);
	}
	
	public static Boolean getVersionCheckNotify() {
		return MySharedCache.get(version_check_notification, true);
	}

	public static void setVersionCheckNotify(Boolean value) {
		MySharedCache.put(version_check_notification, value);
	}
	
	public static Boolean getImageSettings() {
		return MySharedCache.get(image_setting_config, true);
	}

	public static void setImageSettings(Boolean value) {
		MySharedCache.put(image_setting_config, value);
	}
}
