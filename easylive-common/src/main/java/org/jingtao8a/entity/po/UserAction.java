package org.jingtao8a.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
@Description:用户行为 点赞、评论
@Date:2024-12-17
*/
@Data
@ToString
public class UserAction implements Serializable {
	/**
	 * 自增id
	*/
	private Integer actionId;
	/**
	 * 视频ID
	*/
	private String videoId;
	/**
	 * 视频用户ID
	*/
	private String videoUserId;
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
	/**
	 * 操作时间
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date actionTime;

	private String videoCover;

	private String videoName;
}