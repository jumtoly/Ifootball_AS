package com.ifootball.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import com.ifootball.app.framework.http.ConnectionChangedBroadcastReceiver;

public class ImageUrlUtil {

	public static final String IMAGE_SETTING_CHANGED_ACTION = "image_setting_changed_action";
	public static final String IMAGE_SETTING_TYPE = "image_setting_config_type";

	private static Context appContext;
	private static double scaleFactor;
	private static Pattern pattern;
	private static List<Pair> pairs;
	private static boolean isWIFI=true;
	public static final String SYSTEM_SETTINGS_IMAGE_SETTING_AUTO = "auto";
	public static final String SYSTEM_SETTINGS_IMAGE_SETTING_HIGH = "high";
	public static final String SYSTEM_SETTINGS_IMAGE_SETTING_NONE = "none";

	public static void setContext(Context context) {
		if (appContext != null) {
			return;
		}
		appContext = context.getApplicationContext();
		appContext.registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

			}
		}, new IntentFilter(IMAGE_SETTING_CHANGED_ACTION));

		appContext.registerReceiver(new ConnectionChangedBroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();

				if (!action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
					return;
				}

				boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
				NetworkInfo aNetworkInfo = (NetworkInfo) intent
						.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);

				if (!noConnectivity) {
					// reset scale factor
					initScaleFactor();
//					isWIFI = aNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;
					isWIFI=true;
				}
			}

		}, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

		init();

	}

	public static String getImageUrlSmall(String url){
		return getImageUrl(url,45);
	}
	
	public static String getImageUrl(String url){
		return getImageUrl(url,96);
	}
	
	public static String getImageUrl(String url, int widthInDp) {
		SharedPreferences settings =appContext.getSharedPreferences("setting_preference",0);
		Boolean showPics = settings.getBoolean("image_setting_config", true);
		if(!showPics){
			return "";
		}
		if (url == null) {
			return "";
		}
		
		int widthInPx = (int) (widthInDp * scaleFactor);
		return getImageUrlByPx(url, widthInPx).replaceAll(" ", "%20");
	}

	public static String getImageUrlByPx(String url, int widthInPx) {
		if (url == null) {
			return "";
		}
		Pair pair = findSuitablePair(widthInPx);
		if (pair == null) {
			return "";
		}
		return pattern.matcher(url).replaceAll("/neg/" + pair.string + "/");
	}

	private static void init() {
		initScaleFactor();
		initRegexPattern();
		initPairList();
	}

	private static void initPairList() {
		pairs = new ArrayList<Pair>();
		pairs.add(new Pair(60, "P60"));
		pairs.add(new Pair(68, "P68"));
		pairs.add(new Pair(120, "P120"));
		pairs.add(new Pair(160, "P160"));
		pairs.add(new Pair(200, "P200"));
		pairs.add(new Pair(240, "P240"));
		pairs.add(new Pair(450, "P450"));
		pairs.add(new Pair(800, "P800"));
	}

	private static void initRegexPattern() {
		pattern = Pattern.compile("/neg/P[0-9]{2,3}/", Pattern.CASE_INSENSITIVE);
	}

	private static final int DENSITY_XHIGH = 320;

	private static void initScaleFactor() {
		int densityDpi = appContext.getResources().getDisplayMetrics().densityDpi;

		if (densityDpi == DisplayMetrics.DENSITY_HIGH) {
			scaleFactor = 1.5;
		} else if (densityDpi == DisplayMetrics.DENSITY_LOW) {
			scaleFactor = 0.75;
		} else if (densityDpi == DENSITY_XHIGH) {
			scaleFactor = 2;
		} else {
			scaleFactor = 1;
		}
	}

	private static Pair findSuitablePair(int width) {

		for (int i = 0; i < pairs.size(); i++) {
			if (pairs.get(i).dimension >= width) {
				if (isWIFI || i == 0) {
					return pairs.get(i);
				}
				return pairs.get(i - 1);
			}
		}

		return pairs.get(pairs.size() - 1);
	}

	private static class Pair {
		public Pair(int d, String s) {
			this.dimension = d;
			this.string = s;
		}

		public int dimension;

		public String string;
	}
}