package com.ifootball.app.util;

import android.content.Context;

public class UnitConverter {
	public static int dip2px(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				* paramContext.getResources().getDisplayMetrics().density);
	}

	public static int px2dip(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				/ paramContext.getResources().getDisplayMetrics().density);
	}

	public static int sp2px(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				* paramContext.getResources().getDisplayMetrics().scaledDensity);
	}
}

/*
 * Location: F:\apk反编译工具\反编译工具包\dex2jar-0.0.9.15\classes_dex2jar.jar Qualified
 * Name: com.android.common.utils.UnitConverter JD-Core Version: 0.6.2
 */