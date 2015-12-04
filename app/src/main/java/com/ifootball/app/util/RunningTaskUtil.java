package com.ifootball.app.util;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.SharedPreferences;

import com.ifootball.app.baseapp.BaseApp;

public class RunningTaskUtil {
	
	public static boolean isRunning() {
		boolean isRun=false;
		ActivityManager activityManager =(ActivityManager)(BaseApp.instance().getSystemService(android.content.Context.ACTIVITY_SERVICE )) ;
		List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1) ;
		if(runningTaskInfos != null){
			ComponentName componentName=runningTaskInfos.get(0).topActivity;
	        if (componentName!=null&&componentName.getClassName().startsWith(BaseApp.instance().getPackageName())) {
	        	isRun=true;
			}
		}
		
		return isRun;
	}
	
	public static void setSharedCache(boolean isFirst,String serviceVersionCode) {
		SharedPreferences mysherPreferences = BaseApp.instance().getSharedPreferences("RUNNING_TASK_DATA_FILE",Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mysherPreferences.edit();
		editor.putBoolean("RUNNING_TASK_FIRST_DATA",isFirst);
		editor.putString("RUNNING_TASK_VERSION_DATA", StringUtil.isEmpty(serviceVersionCode)?null:serviceVersionCode.toLowerCase());
		editor.commit();
	}
	
	public static boolean getSharedCacheCart(String serviceVersionCode) {
		SharedPreferences mysherPreferences = BaseApp.instance().getSharedPreferences("RUNNING_TASK_DATA_FILE",Activity.MODE_PRIVATE);
		String cacheVersionCode=mysherPreferences.getString("RUNNING_TASK_VERSION_DATA", null);
		boolean isFirst=mysherPreferences.getBoolean("RUNNING_TASK_FIRST_DATA", false);
		if (StringUtil.isEmpty(cacheVersionCode)) {
			return isFirst;
		}else {
			if (StringUtil.isEmpty(serviceVersionCode)) {
				return false;
			}
			if (cacheVersionCode.toLowerCase().equals(serviceVersionCode.toLowerCase())) {
				return isFirst;
			}
		}
		
		return  false;
	}
}
