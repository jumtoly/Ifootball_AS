package com.ifootball.app.listener;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.ifootball.app.entity.CustomerInfo;

public class OnLoginListener implements CheckLoginListener {
	
	private static LoginCallback mCallback;
	
	public OnLoginListener(){

	}
	
	public OnLoginListener(LoginCallback onCallback){
		mCallback=onCallback;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
	}
	
	public static final Parcelable.Creator<OnLoginListener> CREATOR = new Creator<OnLoginListener>() {

		@Override
		public OnLoginListener createFromParcel(Parcel source) {
			
			OnLoginListener listener = new OnLoginListener();
			
			return listener;
		}

		@Override
		public OnLoginListener[] newArray(int size) {
			return new OnLoginListener[size];
		}
	};

	@Override
	public void OnLogined(CustomerInfo customer, Bundle bundle) {
		if (mCallback!=null) {
			mCallback.OnLogined(customer,bundle);
		}
		mCallback=null;
	}

}
