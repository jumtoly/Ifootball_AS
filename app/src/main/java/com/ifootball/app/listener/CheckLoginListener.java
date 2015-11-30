package com.ifootball.app.listener;

import android.os.Bundle;
import android.os.Parcelable;

import com.ifootball.app.entity.CustomerInfo;


public interface CheckLoginListener extends Parcelable {
	public void OnLogined(CustomerInfo customer, Bundle bundle);
}
