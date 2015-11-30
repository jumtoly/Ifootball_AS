package com.ifootball.app.entity;

public class RegisterInfo {
	public String CellPhone;
	public String Password;
	public String RePassword;
	public String ConfirmKey;
	public int FromLinkSource;
	public int Gender;

	public String getCellPhone() {
		return CellPhone;
	}

	public void setCellPhone(String cellPhone) {
		CellPhone = cellPhone;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getRePassword() {
		return RePassword;
	}

	public void setRePassword(String rePassword) {
		RePassword = rePassword;
	}

	public String getConfirmKey() {
		return ConfirmKey;
	}

	public void setConfirmKey(String confirmKey) {
		ConfirmKey = confirmKey;
	}

	public int getFromLinkSource() {
		return FromLinkSource;
	}

	public void setFromLinkSource(int fromLinkSource) {
		FromLinkSource = fromLinkSource;
	}

	public int getGender() {
		return Gender;
	}

	public void setGender(int gender) {
		Gender = gender;
	}

}
