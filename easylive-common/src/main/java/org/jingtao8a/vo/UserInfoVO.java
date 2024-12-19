package org.jingtao8a.vo;

import lombok.Data;

@Data
public class UserInfoVO {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 0：女 1：男 2：未知
     */
    private Integer sex;
    /**
     * 个人简介
     */
    private String personIntroduction;
    /**
     * 空间公告
     */
    private String noticeInfo;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 学校
     */
    private String school;

    private Integer fansCount;//粉丝数
    private Integer currentCoinCount;
    private Integer focusCount;//关注数

    private Integer likeCount;
    private Integer playCount;
    private Boolean havaFocus;

    private Integer theme;
}
