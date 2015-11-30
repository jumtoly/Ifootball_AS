package com.ifootball.app.listener;

import android.os.Bundle;

import com.ifootball.app.entity.CustomerInfo;

public interface LoginCallback {
	void OnLogined(CustomerInfo customer, Bundle bundle);
}
