package com.ifootball.app.entity;

import java.io.Serializable;

public class VenueSearchResultItem  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2853835232519809669L;

	private int SysNo;
	public int getSysNo() {
		return SysNo;
	}
	public void setSysNo(int sysNo) {
		SysNo = sysNo;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getDistrictSysNo() {
		return DistrictSysNo;
	}
	public void setDistrictSysNo(int districtSysNo) {
		DistrictSysNo = districtSysNo;
	}
	public String getDistrictName() {
		return DistrictName;
	}
	public void setDistrictName(String districtName) {
		DistrictName = districtName;
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
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getDefaultPicUrl() {
		return DefaultPicUrl;
	}
	public void setDefaultPicUrl(String defaultPicUrl) {
		DefaultPicUrl = defaultPicUrl;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	public String getContract() {
		return Contract;
	}
	public void setContract(String contract) {
		Contract = contract;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public double getAVGScore() {
		return AVGScore;
	}
	public void setAVGScore(double aVGScore) {
		AVGScore = aVGScore;
	}
	public double getDistance() {
		return Distance;
	}
	public void setDistance(double distance) {
		Distance = distance;
	}
	public int getPlayerNumber() {
		return PlayerNumber;
	}
	public void setPlayerNumber(int playerNumber) {
		PlayerNumber = playerNumber;
	}
	private String Name;
	private int DistrictSysNo;
	private String DistrictName;
	private int CitySysNo;
	private String CityName;
	private String Location;
	private String DefaultPicUrl;
	private double Longitude;
	private double Latitude;
	private String Telphone;
	private String Contract;
	private String Category;
	private double AVGScore;
	private double Distance;
	private int PlayerNumber;
	
}
