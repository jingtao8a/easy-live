package org.jingtao8a.enums;

public enum StatisticTypeEnum {
    PLAY(0, "播放量"),
    FANS(1, "粉丝"),
    LIKE(2, "点赞"),
    COLLECTION(3, "收藏"),
    COIN(4, "投币"),
    COMMENT(5, "评论"),
    DANMU(6, "弹幕"),
    ;
    private Integer type;
    private String desc;
    StatisticTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static StatisticTypeEnum getEnum(Integer type) {
        for (StatisticTypeEnum item : StatisticTypeEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }
}
