package com.ifootball.app.entity.stand;

import java.io.Serializable;

import com.neweggcn.lib.json.annotations.SerializedName;

public class CommentInfo implements Serializable {
    private static final long serialVersionUID = 2301852905215877569L;
    @SerializedName("UserSysNo")
    private int userSysNo;
    @SerializedName("TopicSysNo")
    private int topicSysNo;
    @SerializedName("CommentType")
    private int commentType;
    @SerializedName("Content")
    private String content;
    @SerializedName("UserSysNoed")
    private int userSysNoed;
    @SerializedName("ReferenceSysNo")
    private int referenceSysNo;

    public CommentInfo(int userSysNo, int topicSysNo, int commentType,
                       String content, int userSysNoed, int referenceSysNo) {
        super();
        this.userSysNo = userSysNo;
        this.topicSysNo = topicSysNo;
        this.commentType = commentType;
        this.content = content;
        this.userSysNoed = userSysNoed;
        this.referenceSysNo = referenceSysNo;
    }

    public CommentInfo(int userSysNo, int topicSysNo, int commentType,
                       String content, int userSysNoed) {
        super();
        this.userSysNo = userSysNo;
        this.topicSysNo = topicSysNo;
        this.commentType = commentType;
        this.content = content;
        this.userSysNoed = userSysNoed;
    }

    public int getUserSysNoed() {
        return userSysNoed;
    }

    public void setUserSysNoed(int userSysNoed) {
        this.userSysNoed = userSysNoed;
    }

    public int getReferenceSysNo() {
        return referenceSysNo;
    }

    public void setReferenceSysNo(int referenceSysNo) {
        this.referenceSysNo = referenceSysNo;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
