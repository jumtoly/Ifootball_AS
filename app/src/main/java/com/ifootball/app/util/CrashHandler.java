package com.ifootball.app.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;

public class CrashHandler implements UncaughtExceptionHandler {
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	private static final CrashHandler INSTANCE = new CrashHandler();
	private Context mContext;

	private CrashHandler() {
	}

	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	public void init(Context ctx) {
		mContext = ctx;
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, final Throwable ex) {
		String deviceInfo = collectDeviceInfo(mContext);
		StringWriter sw = new StringWriter();
		PrintWriter printWriter = new PrintWriter(sw);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		final String msg = deviceInfo + "\n" + sw.toString();
		new Thread() {
			@Override
			public void run() {
//				MoreService moreService = new MoreService();
//				moreService.sendCrashLog(msg);
				Looper.prepare();
				try {
					new AlertDialog.Builder(mContext)
							.setTitle("提示")
							.setCancelable(false)
							.setMessage("程序崩溃了,请重试...")
							.setNeutralButton("我知道了",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											ExitAppUtil.exit();
										}
									}).create().show();
				} catch (Exception iException) {
					iException.printStackTrace();
				}
				Looper.loop();
			}
		}.start();
	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	public String collectDeviceInfo(Context ctx) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				appendInfo(stringBuffer, "versionName", versionName);
				appendInfo(stringBuffer, "versionCode", versionCode);
				int sdkVersion = Build.VERSION.SDK_INT;
				if (sdkVersion > 8) {
					Date firstInstallTime = new Date(pi.firstInstallTime);
					appendInfo(stringBuffer, "firstInstallTime",
							formatter.format(firstInstallTime));
					Date lastUpdateTime = new Date(pi.lastUpdateTime);
					appendInfo(stringBuffer, "lastUpdateTime",
							formatter.format(lastUpdateTime));
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				appendInfo(stringBuffer, field.getName(), field.get(null)
						.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return stringBuffer.toString();
	}

	private void appendInfo(StringBuffer stringBuffer, String key, String value) {
		stringBuffer.append(key + "=" + value + "\r\n");
	}
}