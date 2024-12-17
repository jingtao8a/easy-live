package org.jingtao8a.entity.query;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
/**
@Description:用户行为 点赞、评论
@Date:2024-12-17
*/
@Data
@ToString
public class UserActionQuery extends BaseQuery {
	/**
	 * 自增id
	*/
	private Integer actionId;
	/**
	 * 视频ID
	*/
	private String videoId;
	private String videoIdFuzzy;

	/**
	 * 视频用户ID
	*/
	private String videoUserId;
	private String videoUserIdFuzzy;

	/**
	 * 评论ID
	*/
	private Integer commentId;
	/**
	 * 0:喜欢点赞评论 1.讨厌评论 2.视频点赞 3.视频收藏 4.视频投币
	*/
	private Integer actionType;
	/**
	 * 数量
	*/
	private Integer actionCount;
	/**
	 * 用户ID
	*/
	private String userId;
	private String userIdFuzzy;

	/**
	 * 操作时间
	*/
	private Date actionTime;
	private String actionTimeStart;
	private String actionTimeEnd;

}