package com.xxh.start1.enums;

import com.xxh.start1.model.Question;

public enum CommentTypeENum {
    QUESTION(1),
    COMMENT(2);
    private  Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeENum commentTypeEnum : CommentTypeENum.values()) {
            if(commentTypeEnum.getType()==type) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeENum(Integer type) {
        this.type = type;
    }
}
