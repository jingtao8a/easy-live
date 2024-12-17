package org.jingtao8a.enums;

public enum UserActionTypeEnum {
    COMMENT_LIKE(0, "like_count", "喜欢点赞评论"),
    COMMENT_HATE(1, "hate_count", "讨厌评论"),
    VIDEO_LIK(2, "like_count", "视频点赞"),
    VIDEO_COLLECT(3, "collect_count", "视频收藏"),
    VIDEO_COIN(4, "coin_count", "视频投币"),
    VIDEO_COMMENT(5, "comment_count", "视频评论数"),
    VIDEO_DANMU(6, "danmu_count", "弹幕评论数"),
    VIDEO_PLAY(7, "play_count", "视频播放数"),
    ;
    private Integer type;
    private String field;
    private String desc;

    UserActionTypeEnum(Integer type, String field, String desc) {
        this.type = type; this.field = field; this.desc = desc;
    }

    public static UserActionTypeEnum getEnum(Integer type) {
        for (UserActionTypeEnum e : UserActionTypeEnum.values()) {
            if (e.type == type) {
                return e;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public String getField() {
        return field;
    }

    public String getDesc() {
        return desc;
    }
}
