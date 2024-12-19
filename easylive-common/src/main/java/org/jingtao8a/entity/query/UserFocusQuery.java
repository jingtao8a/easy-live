package org.jingtao8a.entity.query;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
/**
@Description:用户关注表
@Date:2024-12-19
*/
@Data
@ToString
public class UserFocusQuery extends BaseQuery {
	/**
	 * 用户ID
	*/
	private String userId;
	private String userIdFuzzy;

	/**
	 * 被关注用户ID
	*/
	private String focusUserId;
	private String focusUserIdFuzzy;

	/**
	 * 关注时间
	*/
	private Date focusTime;
	private String focusTimeStart;
	private String focusTimeEnd;

	private Integer queryType;//0:查关注 1：查粉丝
}