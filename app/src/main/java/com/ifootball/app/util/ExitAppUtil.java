package com.ifootball.app.util;

import android.content.Context;
import android.content.DialogInterface;

import com.ifootball.app.entity.CustomerInfo;
import com.ifootball.app.entity.SettingsUtil;

public class ExitAppUtil {
	private static Context context;

	/**
	 * 退出程序
	 */
	public static void exit(final Context context) {

		if (SettingsUtil.getConfirmWhenExit()) {
			DialogUtil.getConfirmAlertDialog(context, "温馨提示", "确认离开吗？",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							isRemember();
							AppManager.getAppManager().AppExit(context);
						}
					}).show();
		} else {
			isRemember();
			AppManager.getAppManager().AppExit(context);
		}
	}

	/**
	 * 判断是否记住登录状态 如false将清除CustomerInfo
	 */
	public static void isRemember() {
		CustomerInfo customerInfo = CustomerUtil.getCustomerInfo();
		if (customerInfo != null) {
			CustomerUtil.clearAuthenTick();
			CustomerUtil.clearCustomerInfo();
		}
	}

	public static void exit() {
		isRemember();
		killProcess();
	}

	private static void killProcess() {
		LoginStackUtil.finishAll();
		System.exit(10);
		android.os.Process.killProcess(android.os.Process.myPid());

	}
}
