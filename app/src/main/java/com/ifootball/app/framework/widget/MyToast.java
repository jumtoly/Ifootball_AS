package com.ifootball.app.framework.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ifootball.app.R;

/**
 * MyToast for display message after action executed.
 * 
 */
public class MyToast {

	/**
	 * show toast message after action executed. the toast display time is short
	 * default.
	 * 
	 * @param mContext
	 * @param message
	 */
	public static void show(Context mContext, String message) {

		show(mContext, message, Toast.LENGTH_SHORT, Gravity.CENTER, 0, 0);
	}

	/**
	 * show toast message after action executed.
	 * 
	 * @param mContext
	 * @param message
	 * @param duration
	 */
	public static void show(Context mContext, String message, int duration) {

		show(mContext, message, duration, Gravity.CENTER, 0, 0);
	}

	/**
	 *  show toast message after action executed
	 * 
	 * @param mContext
	 * @param message
	 * 			the message to be shown in toast
	 * @param duration
	 * 			the toast show time length
	 * @param gravity
	 * 			the gravity of toast
	 * @param xOffset
	 * 			the x coordinate 
	 * @param yOffset
	 * 			the y coordinate
	 */
	public static void show(Context mContext, String message, int duration,
			int gravity, int xOffset, int yOffset) {

		if (mContext == null) {
			return;
		}

		View viewContainer = LayoutInflater.from(mContext).inflate(
				R.layout.frm_common_toast, null);
		TextView textView = (TextView) viewContainer
				.findViewById(R.id.common_toast_textview_message);
		textView.setText(message);

		Toast toast = new Toast(mContext);
		toast.setGravity(gravity, xOffset, yOffset);
		toast.setView(viewContainer);
		toast.setDuration(duration);
		toast.show();
	}
}