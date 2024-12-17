package org.jingtao8a.enums;

public enum VideoRecommendTypeEnum {
    NO_RECOMMEND(0, "未推荐"),
    RECOMMEND(1, "已推荐"),
    ;

    private Integer type;
    private String desc;

    VideoRecommendTypeEnum(Integer type, String desc) {this.type = type;this.desc = desc;}

    public Integer getType() { return type;}

    public String getDesc() { return desc;}

    public static VideoRecommendTypeEnum getEnum(Integer type) {
        for (VideoRecommendTypeEnum item : VideoRecommendTypeEnum.values()) {
            if (item.getType().equals(type)) {return item;}
        }
        return null;
    }
}
