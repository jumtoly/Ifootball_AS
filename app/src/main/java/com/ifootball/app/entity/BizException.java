package com.ifootball.app.entity;

public class BizException extends Exception {

	private static final long serialVersionUID = -2111092304574674326L;

	private final String mCode;
	private final String mDescription;

	public BizException(String code, String description) {
		mCode = code;
		mDescription = description;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return mCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return mDescription;
	}

}