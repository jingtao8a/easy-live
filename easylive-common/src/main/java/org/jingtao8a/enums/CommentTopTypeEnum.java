package org.jingtao8a.enums;

public enum CommentTopTypeEnum {
    NO_TOP(0, "未置顶"),
    TOP(1, "已置顶"),
    ;
    private Integer type;
    private String desc;

    CommentTopTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static CommentTopTypeEnum getEnum(Integer type) {
        for (CommentTopTypeEnum e : CommentTopTypeEnum.values()) {
            if (e.type == type) {
                return e;
            }
        }
        return null;
    }
}
