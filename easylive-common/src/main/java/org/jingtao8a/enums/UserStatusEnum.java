package org.jingtao8a.enums;

public enum UserStatusEnum {
    DISABLE(0, "禁用"),
    ENABLE(1, "启用"),
    ;
    Integer status;
    String desc;

    UserStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public static UserStatusEnum getUserStatusEnum(Integer status) {
        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            if (userStatusEnum.getStatus() == status) {
                return userStatusEnum;
            }
        }
        return null;
    }
}
