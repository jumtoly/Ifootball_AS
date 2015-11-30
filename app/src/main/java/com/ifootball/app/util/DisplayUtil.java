package com.ifootball.app.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DisplayUtil {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int getPxByDp(Context context, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
	}
	
	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
	 * @param context
	 * @param px
	 * @return
	 */
	public static int getDpByPx(Context context, int px){
		
		 final float scale = context.getResources().getDisplayMetrics().density;  
		 return (int) (px / scale + 0.5f);  
	}
	
	/**
	 * 获取屏幕宽度
	 * 
	 * @param activity
	 * @return 屏幕宽度(像素)
	 */
	public static int getScreenWidth(Activity activity) {
		
		DisplayMetrics  dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @param activity
	 * @return 屏幕高度(像素)
	 */
	public static int getScreenHeight(Activity activity) {
		
		DisplayMetrics  dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		return dm.heightPixels;
	}
}
