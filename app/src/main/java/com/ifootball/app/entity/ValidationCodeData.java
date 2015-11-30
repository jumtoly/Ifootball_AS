package com.ifootball.app.entity;

import android.graphics.Bitmap;

public class ValidationCodeData {
	private String mCode;
	private Bitmap mBitmap;
	public String getCode() {
		return mCode;
	}
	public void setCode(String code) {
		this.mCode = code;
	}
	public Bitmap getBitmap() {
		return mBitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.mBitmap = bitmap;
	}
	
	
}
