package com.ifootball.app.entity;

import java.io.Serializable;

import com.neweggcn.lib.json.annotations.SerializedName;

public class PageInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4803759973924323430L;
	@SerializedName("TotalCount")
	private long mTotalCount;
	@SerializedName("PageSize")
	private int mPageSize;
	@SerializedName("PageNumber")
	private int mPageNumber;
	@SerializedName("SortBy")
	private String SortBy ;
	
	public String getSortBy() {
		return SortBy;
	}

	public void setSortBy(String sortBy) {
		SortBy = sortBy;
	}

	public void setTotalCount(long totalCount) {
		this.mTotalCount = totalCount;
	}

	public long getTotalCount() {
		return mTotalCount;
	}

	public void setPageSize(int pageSize) {
		this.mPageSize = pageSize;
	}

	public int getPageSize() {
		return mPageSize;
	}

	public void setPageNumber(int pageNumber) {
		this.mPageNumber = pageNumber;
	}

	public int getPageNumber() {
		return mPageNumber;
	}
}