package org.jingtao8a.enums;

public enum MessageReadTypeEnum {
    NO_READ(0, "未读"),
    READ(1, "已读"),
    ;
    private Integer type;
    private String desc;
    MessageReadTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public Integer getType() {
        return type;
    }
    public String getDesc() {
        return desc;
    }
    public MessageReadTypeEnum getEnum(Integer type) {
        for (MessageReadTypeEnum e : MessageReadTypeEnum.values()) {
            if (e.getType() == type) {
                return e;
            }
        }
        return null;
    }
}
