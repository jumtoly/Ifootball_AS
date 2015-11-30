package com.ifootball.app.entity.stand;

import java.io.Serializable;
import java.util.List;

import com.neweggcn.lib.json.annotations.SerializedName;

public class StandInfo implements Serializable
{

	private static final long serialVersionUID = -225227035757722040L;

	@SerializedName("SysNo")
	private int sysNo;
	@SerializedName("UserSysNo")
	private int userSysNo;
	@SerializedName("Content")
	private String content;
	@SerializedName("Pic")
	private List<String> picUrls;
	@SerializedName("IsVideo")
	private boolean isVideo;
	@SerializedName("VideoPath")
	private String videoPath;
	@SerializedName("Longitude")
	private float longitude;
	@SerializedName("Latitude")
	private float latitude;
	@SerializedName("Position")
	private String position;
	@SerializedName("ViewCount")
	private int viewCount;
	@SerializedName("EnjoyCount")
	private int enjoyCount;
	@SerializedName("InDate")
	private String inDate;
	@SerializedName("InDateStr")
	private String inDateStr;
	@SerializedName("Type")
	private int type;
	@SerializedName("NickName")
	private String nickName;
	@SerializedName("Avatar")
	private String avatar;

	public StandInfo()
	{
		super();
	}

	public StandInfo(int sysNo, int userSysNo, String content,
			List<String> picUrls, boolean isVideo, String videoPath,
			float longitude, float latitude, String position, int viewCount,
			int enjoyCount, String inDate, String inDateStr, int type,
			String nickName, String avatar)
	{
		super();
		this.sysNo = sysNo;
		this.userSysNo = userSysNo;
		this.content = content;
		this.picUrls = picUrls;
		this.isVideo = isVideo;
		this.videoPath = videoPath;
		this.longitude = longitude;
		this.latitude = latitude;
		this.position = position;
		this.viewCount = viewCount;
		this.enjoyCount = enjoyCount;
		this.inDate = inDate;
		this.inDateStr = inDateStr;
		this.type = type;
		this.nickName = nickName;
		this.avatar = avatar;
	}

	public int getSysNo()
	{
		return sysNo;
	}

	public void setSysNo(int sysNo)
	{
		this.sysNo = sysNo;
	}

	public int getUserSysNo()
	{
		return userSysNo;
	}

	public void setUserSysNo(int userSysNo)
	{
		this.userSysNo = userSysNo;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public List<String> getPicUrls()
	{
		return picUrls;
	}

	public void setPicUrls(List<String> picUrls)
	{
		this.picUrls = picUrls;
	}

	public boolean isVideo()
	{
		return isVideo;
	}

	public void setVideo(boolean isVideo)
	{
		this.isVideo = isVideo;
	}

	public String getVideoPath()
	{
		return videoPath;
	}

	public void setVideoPath(String videoPath)
	{
		this.videoPath = videoPath;
	}

	public float getLongitude()
	{
		return longitude;
	}

	public void setLongitude(float longitude)
	{
		this.longitude = longitude;
	}

	public float getLatitude()
	{
		return latitude;
	}

	public void setLatitude(float latitude)
	{
		this.latitude = latitude;
	}

	public String getPosition()
	{
		return position;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public int getViewCount()
	{
		return viewCount;
	}

	public void setViewCount(int viewCount)
	{
		this.viewCount = viewCount;
	}

	public int getEnjoyCount()
	{
		return enjoyCount;
	}

	public void setEnjoyCount(int enjoyCount)
	{
		this.enjoyCount = enjoyCount;
	}

	public String getInDate()
	{
		return inDate;
	}

	public void setInDate(String inDate)
	{
		this.inDate = inDate;
	}

	public String getInDateStr()
	{
		return inDateStr;
	}

	public void setInDateStr(String inDateStr)
	{
		this.inDateStr = inDateStr;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public String getAvatar()
	{
		return avatar;
	}

	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
}
