package com.ifootball.app.framework.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

public class BlinkTextView extends TextView {

	private Handler mHandler;
	private boolean flag;
	private int mRepeat;
	private boolean isBlinking;

	public BlinkTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void startBlink() {
		startBlink(10);
	}

	public void startBlink(int repeat) {
		if (isBlinking) {
			return;
		}
		
		if (mHandler == null) {
			mHandler = new Handler();
		}

		mRepeat = repeat;
		
		final int currentColor = getCurrentTextColor();

		final Runnable runnable = new Runnable() {

			@Override
			public void run() {
				if (flag) {
					setTextColor(Color.BLACK);
				} else {
					setTextColor(Color.RED);
				}

				flag = !flag;
				mRepeat--;

			}
		};
		isBlinking = true;

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				while (mRepeat >= 0) {
					try {
						synchronized (this) {
							wait(250);
							mHandler.post(runnable);
						}
					} catch (InterruptedException e) {
					}

				}
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						setTextColor(currentColor);
						isBlinking = false;
					}
				});
				
				return null;
			}
		}.execute();

	}
}