package com.ifootball.app.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class LoginStackUtil {
	private static List<Activity> sDictionary =new ArrayList<>();
	public static void addActivity(Activity activity) {
		sDictionary.add(activity);
	}
	
	public static void removeActivity(Activity activity) {
		if(sDictionary.contains(activity)){
			sDictionary.remove(activity);
		}
	}
	
	public static void  removeTop() {
		if(sDictionary.size()>0){
			sDictionary.remove(sDictionary.size()-1);
		}
	}
	
	public static void finishAll(){
		for (Activity activity:sDictionary) {
			if(activity != null){
				activity.finish();
			}
		}
		sDictionary.clear();
	}
	
	public static void clearAll(){
		sDictionary.clear();
	}
}
