package com.xxh.start1.exception;
public enum CustomizeErrorCode  implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"问题不存在"),
    TARGET_PARAM_NOT_FOUND(2002,"不存在就完事了"),
    COMMENT_NOT_FOUND(2003,"评论不存在"),
    NO_LOGIN (2004,"你没有登录"),
    SYS_ERROR(2005,"系统出错了！"),
    CONTENT_IS_EMPTY(2006,"输入不能为空！"),
    READ_NOTIFICATION_FAIL(2007,"找不到回复人");
    @Override
    public  String getMessage(){
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


    private String message;
    private  Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}

