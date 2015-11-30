package com.ifootball.app.util;

import android.os.CountDownTimer;

import com.ifootball.app.listener.CountDownTimerListener;

public class CountDownTimerUtil extends CountDownTimer{

	private CountDownTimerListener mListener;
	public CountDownTimerUtil(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}
	
	public CountDownTimerUtil(long millisInFuture, long countDownInterval,CountDownTimerListener listener){
		this(millisInFuture, countDownInterval);
		mListener=listener;
	}

	@Override
	public void onFinish() {
		if (mListener!=null) {
			mListener.onFinish();
		}
	}

	@Override
	public void onTick(long millisUntilFinished) {
		if (mListener!=null) {
			mListener.onTick(millisUntilFinished);
		}
	}
}
