package org.jingtao8a.enums;

public enum SearchOrderTypeEnum {
    VIDEO_PLAY(0, "playCount", "视频播放数"),
    VIDEO_TIME(1, "createTime", "视频时间"),
    VIDEO_DANMU(2, "danmuCount", "视频弹幕数"),
    VIDEO_COLLECT(3, "collectCount", "视频收藏数"),
    ;
    private Integer type;
    private String field;
    private String desc;

    SearchOrderTypeEnum(Integer type, String field, String desc) {
        this.type = type;
        this.field = field;
        this.desc = desc;
    }

    public Integer getType() { return type; }

    public String getField() { return field; }

    public String getDesc() { return desc; }

    public static SearchOrderTypeEnum getEnum(Integer type) {
        for (SearchOrderTypeEnum item : SearchOrderTypeEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }
}
