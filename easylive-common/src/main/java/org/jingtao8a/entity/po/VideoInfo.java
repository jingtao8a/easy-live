package org.jingtao8a.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
@Description:视频信息
@Date:2024-12-11
*/
@Data
@ToString
public class VideoInfo implements Serializable {
	/**
	 * 视频ID
	*/
	private String videoId;
	/**
	 * 视频封面
	*/
	private String videoCover;
	/**
	 * 视频名称
	*/
	private String videoName;
	/**
	 * 用户ID
	*/
	private String userId;
	/**
	 * 创建时间
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 最后更新时间
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;
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
	/**
	 * 简介
	*/
	private String introduction;
	/**
	 * 互动设置
	*/
	private String interaction;
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
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastPlayTime;
}