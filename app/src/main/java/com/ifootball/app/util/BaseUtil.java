package com.ifootball.app.util;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

public class BaseUtil {

	private static ProgressDialog mLoadingDialog;

	public static void showLoading(Context context, String tips) {
		try {
			if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
				mLoadingDialog.dismiss();
				mLoadingDialog = null;
			}
			mLoadingDialog = DialogUtil.getProgressDialog(context, tips);
			mLoadingDialog.show();
		} catch (Exception e) {
		}
	}

	public static void showLoading(Context context, int tipId) {
		showLoading(context, context.getResources().getString(tipId));
	}

	public static void closeLoading() {
		try {
			if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
				mLoadingDialog.dismiss();
				mLoadingDialog = null;
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 跳转到浏览器
	 * 
	 * @param context
	 * @param url
	 */
	public static void goWebView(Context context, String url) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri uri = Uri.parse(url);
		intent.setData(uri);
		try {
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			String msg = StringUtil.format("您的设备上没有浏览器，无法查看网址:{0}！", url);
			DialogUtil.getConfirmAlertDialog(context, "温馨提示", msg, null).show();
		}
	}

	public static Boolean isPackageAvailable(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
		// 从pinfo中将包名字逐一取出，压入pName list中
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				pName.add(pn);
			}
		}
		return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
	}

	public static void startPackage(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> allApp = packageManager.queryIntentActivities(
				mainIntent, 0);
		ResolveInfo foundResolveInfo = null;
		for (int i = 0; i < allApp.size(); i++) {
			ResolveInfo res = allApp.get(i);
			if (res.activityInfo.packageName.equals(packageName)) {
				foundResolveInfo = res;
			}
		}
		if (foundResolveInfo != null) {

			String pkg = foundResolveInfo.activityInfo.packageName;
			String cls = foundResolveInfo.activityInfo.name;

			ComponentName componet = new ComponentName(pkg, cls);

			Intent i = new Intent();
			i.setComponent(componet);
			context.startActivity(i);
		}
	}
}
