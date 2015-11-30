package com.ifootball.app.listener;

public interface CountDownTimerListener {
	void onFinish();
	void onTick(long millisUntilFinished);
}
