package com.ifootball.app.entity.stand;

import java.io.Serializable;

import com.neweggcn.lib.json.annotations.SerializedName;

public class CommUPInfo implements Serializable {
    private static final long serialVersionUID = 2301852905215877569L;

    @SerializedName("SysNo")
    private int sysNo;
    @SerializedName("UserSysNo")
    private int userSysNo;
    @SerializedName("UserSysNoed")
    private int userSysNoed;
    @SerializedName("TopicSysNo")
    private int topicSysNo;
    @SerializedName("CommentType")
    private int commentType;
    @SerializedName("Content")
    private String content;
    @SerializedName("AvataredUrl")
    private String avataredUrl;
    @SerializedName("AvatarUrl")
    private String avatarUrl;
    @SerializedName("NickNamed")
    private String nickNamed;
    @SerializedName("NickName")
    private String nickName;
    @SerializedName("StrInDate")
    private String strInDate;

    public int getUserSysNoed() {
        return userSysNoed;
    }

    public void setUserSysNoed(int userSysNoed) {
        this.userSysNoed = userSysNoed;
    }

    public String getAvataredUrl() {
        return avataredUrl;
    }

    public void setAvataredUrl(String avataredUrl) {
        this.avataredUrl = avataredUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickNamed() {
        return nickNamed;
    }

    public void setNickNamed(String nickNamed) {
        this.nickNamed = nickNamed;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public int getTopicSysNo() {
        return topicSysNo;
    }

    public void setTopicSysNo(int topicSysNo) {
        this.topicSysNo = topicSysNo;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStrInDate() {
        return strInDate;
    }

    public void setStrInDate(String strInDate) {
        this.strInDate = strInDate;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
