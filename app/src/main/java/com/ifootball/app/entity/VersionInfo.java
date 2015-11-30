package com.ifootball.app.entity;

import java.io.Serializable;

public class VersionInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4794246455826102362L;

	/**
	 * 版本号
	 */
	private String Code ;

    /**
     * 是否强制更新
     */
	private boolean IsForcedUpdate ;
    /**
     * 是否更新
     */
	private boolean IsUpdate ;
    /**
     * 描述
     */
	private String Description ;
	
	/**
	 * 下载地址
	 */
	private String DownloadPath;
    
    /**
     * 版本号
     * @return
     */
	public String getCode() {
		return Code;
	}
	/**
	 * 版本号
	 * @param code
	 */
	public void setCode(String code) {
		Code = code;
	}
	/**
	 * 是否强制更新
	 * @return
	 */
	public boolean getIsForcedUpdate() {
		return IsForcedUpdate;
	}
	/**
	 * 是否强制更新
	 * @param isForcedUpdate
	 */
	public void setIsForcedUpdate(boolean isForcedUpdate) {
		IsForcedUpdate = isForcedUpdate;
	}
	/**
	 * 是否更新
	 * @return
	 */
	public boolean getIsUpdate() {
		return IsUpdate;
	}
	/**
	 * 是否更新
	 * @param isUpdate
	 */
	public void setIsUpdate(boolean isUpdate) {
		IsUpdate = isUpdate;
	}
	/**
	 * 描述
	 * @return
	 */
	public String getDescription() {
		return Description;
	}
	/**
	 * 描述
	 * @param description
	 */
	public void setDescription(String description) {
		Description = description;
	}
	/**
	 * 下载地址
	 * @return
	 */
	public String getDownloadPath() {
		return DownloadPath;
	}
	/**
	 * 下载地址
	 * @param downloadPath
	 */
	public void setDownloadPath(String downloadPath) {
		DownloadPath = downloadPath;
	}

    
    
}
