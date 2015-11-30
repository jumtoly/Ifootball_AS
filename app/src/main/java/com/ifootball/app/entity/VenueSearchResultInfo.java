package com.ifootball.app.entity;

import java.util.Collection;
import java.util.List;

import com.neweggcn.lib.json.annotations.SerializedName;

 


public class VenueSearchResultInfo implements HasPageInfo, HasCollection<VenueSearchResultItem> {
	@SerializedName("ResultList")
	private List<VenueSearchResultItem> mVenueListItems;
	
 
	@SerializedName("PageInfo")
	private PageInfo mPageInfo;


	@Override
	public Collection<VenueSearchResultItem> getList() {
		// TODO Auto-generated method stub
		return mVenueListItems;
	}


	@Override
	public PageInfo getPageInfo() {
		// TODO Auto-generated method stub
		return mPageInfo;
	}
	
}