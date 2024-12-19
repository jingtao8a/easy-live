package org.jingtao8a.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.*;
/**
@Description:评论
@Date:2024-12-17
*/
@Data
@ToString
public class VideoComment implements Serializable {
	/**
	 * 评论ID
	*/
	private Integer commentId;
	/**
	 * 父级评论ID
	*/
	private Integer pCommentId;
	/**
	 * 视频ID
	*/
	private String videoId;
	/**
	 * 视频用户ID
	*/
	private String videoUserId;
	/**
	 * 回复内容
	*/
	private String content;
	/**
	 * 图片
	*/
	private String imgPath;
	/**
	 * 用户ID
	*/
	private String userId;
	/**
	 * 回复人ID
	*/
	private String replyUserId;
	/**
	 * 0:未置顶 1:置顶
	*/
	private Integer topType;
	/**
	 * 发布时间
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date postTime;
	/**
	 * 喜欢数量
	*/
	private Integer likeCount;
	/**
	 * 讨厌数量
	*/
	private Integer hateCount;

	private String avatar;

	private String nickName;

	private String replyAvatar;

	private String replyNickName;

	private List<VideoComment> children;
}