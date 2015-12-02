package com.ifootball.app.entity.stand;

import java.io.Serializable;
import java.util.List;

import com.ifootball.app.entity.release.PictureInfo;
import com.neweggcn.lib.json.annotations.SerializedName;

public class UserDetailsInfo implements Serializable {
    private static final long serialVersionUID = 9050808207023912486L;
    @SerializedName("SysNo")
    private int sysNo;
    @SerializedName("UserSysNo")
    private int userSysNo;
    @SerializedName("Content")
    private String content;
    @SerializedName("PicList")
    private List<PictureInfo> picUrls;
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
    @SerializedName("StrInDate")
    private String strInDate;
    @SerializedName("Type")
    private int type;
    @SerializedName("AvatarUrl")
    private String avatarUrl;
    @SerializedName("NickName")
    private String nickName;
    @SerializedName("CommentList")
    private List<CommUPInfo> commentList;
    @SerializedName("UPList")
    private List<CommUPInfo> upList;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<CommUPInfo> getUpList() {
        return upList;
    }

    public void setUpList(List<CommUPInfo> upList) {
        this.upList = upList;
    }

    public int getSysNo() {
        return sysNo;
    }

    public void setSysNo(int sysNo) {
        this.sysNo = sysNo;
    }

    public int getUserSysNo() {
        return userSysNo;
    }

    public void setUserSysNo(int userSysNo) {
        this.userSysNo = userSysNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<PictureInfo> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<PictureInfo> picUrls) {
        this.picUrls = picUrls;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getEnjoyCount() {
        return enjoyCount;
    }

    public void setEnjoyCount(int enjoyCount) {
        this.enjoyCount = enjoyCount;
    }

    public String getStrInDate() {
        return strInDate;
    }

    public void setStrInDate(String strInDate) {
        this.strInDate = strInDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<CommUPInfo> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommUPInfo> commentList) {
        this.commentList = commentList;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
