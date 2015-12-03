package com.ifootball.app.entity;

import java.io.Serializable;

public class AreaInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1473986204578652170L;

	private int SysNo;

	private int CitySysNo;

	private String CityName;

	private String DistrictName;

	private int Priority;

	public int getSysNo() {
		return SysNo;
	}

	public void setSysNo(int sysNo) {
		SysNo = sysNo;
	}

	public int getCitySysNo() {
		return CitySysNo;
	}

	public void setCitySysNo(int citySysNo) {
		CitySysNo = citySysNo;
	}

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}

	public String getDistrictName() {
		return DistrictName;
	}

	public void setDistrictName(String districtName) {
		DistrictName = districtName;
	}

	public int getPriority() {
		return Priority;
	}

	public void setPriority(int priority) {
		Priority = priority;
	}

}
