package org.jingtao8a.entity.query;

import lombok.Data;
import lombok.ToString;

import java.util.*;
/**
@Description:视频信息
@Date:2024-12-11
*/
@Data
@ToString
public class VideoInfoQuery extends BaseQuery {
	/**
	 * 视频ID
	*/
	private String videoId;
	private String videoIdFuzzy;

	/**
	 * 视频封面
	*/
	private String videoCover;
	private String videoCoverFuzzy;

	/**
	 * 视频名称
	*/
	private String videoName;
	private String videoNameFuzzy;

	/**
	 * 用户ID
	*/
	private String userId;
	private String userIdFuzzy;

	/**
	 * 创建时间
	*/
	private Date createTime;
	private String createTimeStart;
	private String createTimeEnd;

	/**
	 * 最后更新时间
	*/
	private Date lastUpdateTime;
	private String lastUpdateTimeStart;
	private String lastUpdateTimeEnd;

	/**
	 * 父级分类ID
	*/
	private Integer pCategoryId;
	/**
	 * 分类ID
	*/
	private Integer categoryId;
	/**
	 * 0:自制作 1:转载
	*/
	private Integer postType;
	/**
	 * 标签
	*/
	private String tags;
	private String tagsFuzzy;

	/**
	 * 简介
	*/
	private String introduction;
	private String introductionFuzzy;

	/**
	 * 互动设置
	*/
	private String interaction;
	private String interactionFuzzy;

	/**
	 * 持续时间(seconds)
	*/
	private Integer duration;
	/**
	 * 播放数量
	*/
	private Integer playCount;
	/**
	 * 点赞数量
	*/
	private Integer likeCount;
	/**
	 * 弹幕数量
	*/
	private Integer danmuCount;
	/**
	 * 评论数量
	*/
	private Integer commentCount;
	/**
	 * 投币数量
	*/
	private Integer coinCount;
	/**
	 * 收藏数量
	*/
	private Integer collectCount;
	/**
	 * 是否推荐 0:未推荐 1:已推荐
	*/
	private Integer recommendType;
	/**
	 * 最后播放时间
	*/
	private Date lastPlayTime;
	private String lastPlayTimeStart;
	private String lastPlayTimeEnd;

    private boolean queryUserInfo;

	private String[] videoIdArray;

	private String[] excludeVideoIdArray;
}