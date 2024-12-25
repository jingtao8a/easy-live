package org.jingtao8a.entity.query;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
/**
@Description:用户消息表
@Date:2024-12-25
*/
@Data
@ToString
public class UserMessageQuery extends BaseQuery {
	/**
	 * 消息ID 自增
	*/
	private Integer messageId;
	/**
	 * 用户ID
	*/
	private String userId;
	private String userIdFuzzy;

	/**
	 * 视频ID
	*/
	private String videoId;
	private String videoIdFuzzy;

	/**
	 * 
	*/
	private Integer messageType;
	/**
	 * 发送人ID
	*/
	private String sendUserId;
	private String sendUserIdFuzzy;

	/**
	 * 0:未读 1:已读
	*/
	private Integer readType;
	/**
	 * 创建时间
	*/
	private Date createTime;
	private String createTimeStart;
	private String createTimeEnd;

	/**
	 * 扩展信息
	*/
	private String extendJson;
	private String extendJsonFuzzy;

}