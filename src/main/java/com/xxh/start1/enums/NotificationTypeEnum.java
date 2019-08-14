package com.xxh.start1.enums;


public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论");
    ;
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int status) {
        this.type = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    NotificationTypeEnum(int status, String name) {
        this.type = status;
        this.name = name;
    }
    public  static String nameOfType(int type){
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if(notificationTypeEnum.getType()==type){
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
