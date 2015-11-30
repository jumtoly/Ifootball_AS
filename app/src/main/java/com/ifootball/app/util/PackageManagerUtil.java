package com.ifootball.app.util;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

public class PackageManagerUtil {
	
	public static void uninstall(Context context,String packageName) {
		if (isPackage(context,packageName)) {
			uninstallExecute(context,packageName);
		}
	}
	
	public static boolean isPackage(Context context,String packageName) {
		if (packageName!=null&&!"".equals(packageName.trim())) {
			PackageManager packageManager = context.getPackageManager();
			  List<PackageInfo> infos = packageManager.getInstalledPackages(0);
			  if (infos!=null&&infos.size()>0) {
				  for (PackageInfo info : infos) {
					if (info.packageName!=null&&!"".equals(info.packageName.trim())) {
						if (packageName.trim().toLowerCase().equals(info.packageName.trim().toLowerCase())) {
							return true;
						}
					}
				  }
			  }
		}
		
		return false;
	}
	
	public static void uninstallExecute(Context context,String packageName) {
		if (packageName!=null&&!"".equals(packageName.trim())) {
			Uri packageURI = Uri.parse("package:"+packageName.trim());
			Intent intent = new Intent(Intent.ACTION_DELETE,packageURI);
			Activity activity=(Activity)context;
			activity.startActivityForResult(intent,1);
		}
	}
}
