package com.ifootball.app.common;

public enum CommentTypeEnum {
    COMMENT(0), UP(1), DOWN(2), REPLY(3);
    private int value;

    CommentTypeEnum(int value) {
        this.value = value;
    }

    public int getCommentType() {
        return value;
    }
}
