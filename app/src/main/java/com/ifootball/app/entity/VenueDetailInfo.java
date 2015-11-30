package com.ifootball.app.entity;

import java.util.ArrayList;
import java.util.List;

import android.R.bool;

public class VenueDetailInfo {

	public int SysNo;
	public String Name;
	public int DistrictSysNo;
	public String Location;
	public String DefaultPicUrl;
	public String ShowDefaultPicUrl;
	public double Longitude;
	public double Latitude;
	public String Telphone;
	public String Contract;
	public String Description;
	public String Category;
	public double Score;
	public List<VenuePicVM> PicList;
	public List<TagInfoVM> TagsList;
	public VenueMatchLogVM MatchLog;

	public List<String> getPics() {
		if (PicList != null && PicList.size() > 0) {
			List<String> pic = new ArrayList<String>();
			for (VenuePicVM p : PicList) {
				pic.add(p.getImagePaht());
			}
			return pic;
		} else {
			return null;
		}
	}

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

	public String getShowDefaultPicUrl() {
		return ShowDefaultPicUrl;
	}

	public void setShowDefaultPicUrl(String showDefaultPicUrl) {
		ShowDefaultPicUrl = showDefaultPicUrl;
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

	public String getTelphone() {
		return Telphone;
	}

	public void setTelphone(String telphone) {
		Telphone = telphone;
	}

	public String getContract() {
		return Contract;
	}

	public void setContract(String contract) {
		Contract = contract;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public double getScore() {
		return Score;
	}

	public void setScore(double score) {
		Score = score;
	}

	public List<VenuePicVM> getPicList() {
		return PicList;
	}

	public void setPicList(List<VenuePicVM> picList) {
		PicList = picList;
	}

	public List<TagInfoVM> getTagsList() {
		return TagsList;
	}

	public void setTagsList(List<TagInfoVM> tagsList) {
		TagsList = tagsList;
	}

	public VenueMatchLogVM getMatchLog() {
		return MatchLog;
	}

	public void setMatchLog(VenueMatchLogVM matchLog) {
		MatchLog = matchLog;
	}

	private class VenuePicVM {
		public int VenueSysNo;
		public String Path;
		public String ImagePaht;
		public int Priority;

		public int getVenueSysNo() {
			return VenueSysNo;
		}

		public void setVenueSysNo(int venueSysNo) {
			VenueSysNo = venueSysNo;
		}

		public String getPath() {
			return Path;
		}

		public void setPath(String path) {
			Path = path;
		}

		public String getImagePaht() {
			return ImagePaht;
		}

		public void setImagePaht(String imagePaht) {
			ImagePaht = imagePaht;
		}

		public int getPriority() {
			return Priority;
		}

		public void setPriority(int priority) {
			Priority = priority;
		}

	}

	public class TagInfoVM {
		public int SysNo;
		public String Tag;
		public int MarkCount;
		public String ShowMarkCount;

		public int getSysNo() {
			return SysNo;
		}

		public void setSysNo(int sysNo) {
			SysNo = sysNo;
		}

		public String getTag() {
			return Tag;
		}

		public void setTag(String tag) {
			Tag = tag;
		}

		public int getMarkCount() {
			return MarkCount;
		}

		public void setMarkCount(int markCount) {
			MarkCount = markCount;
		}

		public String getShowMarkCount() {
			return ShowMarkCount;
		}

		public void setShowMarkCount(String showMarkCount) {
			ShowMarkCount = showMarkCount;
		}

	}

	public class VenueMatchLogVM {
		public List<MatchSigninVM> SigninList;
		public int SiginCount;
		public bool IsCurCustomerMatched;

		public List<MatchSigninVM> getSigninList() {
			return SigninList;
		}

		public void setSigninList(List<MatchSigninVM> signinList) {
			SigninList = signinList;
		}

		public int getSiginCount() {
			return SiginCount;
		}

		public void setSiginCount(int siginCount) {
			SiginCount = siginCount;
		}

		public bool getIsCurCustomerMatched() {
			return IsCurCustomerMatched;
		}

		public void setIsCurCustomerMatched(bool isCurCustomerMatched) {
			IsCurCustomerMatched = isCurCustomerMatched;
		}

	}

	public class MatchSigninVM {
		public int CustomerSysNo;
		public int VenueSysNo;
		public String Avatar;
		public String AvatarUrl;

		public int getCustomerSysNo() {
			return CustomerSysNo;
		}

		public void setCustomerSysNo(int customerSysNo) {
			CustomerSysNo = customerSysNo;
		}

		public int getVenueSysNo() {
			return VenueSysNo;
		}

		public void setVenueSysNo(int venueSysNo) {
			VenueSysNo = venueSysNo;
		}

		public String getAvatar() {
			return Avatar;
		}

		public void setAvatar(String avatar) {
			Avatar = avatar;
		}

		public String getAvatarUrl() {
			return AvatarUrl;
		}

		public void setAvatarUrl(String avatarUrl) {
			AvatarUrl = avatarUrl;
		}

	}
}
