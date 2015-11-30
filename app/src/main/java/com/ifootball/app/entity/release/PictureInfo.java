package com.ifootball.app.entity.release;

import java.io.Serializable;

import com.neweggcn.lib.json.annotations.SerializedName;

public class PictureInfo implements Serializable
{
	private static final long serialVersionUID = -2344622717347029365L;
	@SerializedName("PicUrl")
	private String picUrl;
	@SerializedName("VideoUrl")
	private String videoUrl;
	@SerializedName("IsVideo")
	private boolean isVideo;

	public String getPicUrl()
	{
		return picUrl;
	}

	public void setPicUrl(String picUrl)
	{
		this.picUrl = picUrl;
	}

	public String getVideoUrl()
	{
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl)
	{
		this.videoUrl = videoUrl;
	}

	public boolean isVideo()
	{
		return isVideo;
	}

	public void setVideo(boolean isVideo)
	{
		this.isVideo = isVideo;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
