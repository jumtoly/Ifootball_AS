package com.ifootball.app.entity;

import com.neweggcn.lib.json.annotations.SerializedName;

public class VenueSearchCriteria {
	/**
	 * 当前页码（为0的时候，会显示推荐的产品）
	 */
	@SerializedName("PageIndex")
	private int pageNumber;
	/**
	 * 一页返回多少条数据，默认值为20
	 */
	@SerializedName("PageSize")
	private int PageSize = 20;
	
	@SerializedName("CitySysNo")
	private int CitySysNo;
	
	
	@SerializedName("DistrictSysno")
	private String DistrictSysno;
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return PageSize;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}

	public int getCitySysNo() {
		return CitySysNo;
	}

	public void setCitySysNo(int citySysNo) {
		CitySysNo = citySysNo;
	}

	public String getDistrictSysno() {
		return DistrictSysno;
	}

	public void setDistrictSysno(String districtSysno) {
		DistrictSysno = districtSysno;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	@SerializedName("Category")
	private String Category;
}
