package org.jingtao8a.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.jingtao8a.enums.VideoStatusEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
@Description:视频信息
@Date:2024-12-11
*/
@Data
@ToString
public class VideoInfoPost extends VideoInfo implements Serializable {
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
	 * 0:转码中 1:转码失败 2:待审核 3:审核成功 4:审核失败
	*/
	private Integer status;
	/**
	 * 0:自制作 1:转载
	*/
	private Integer postType;
	/**
	 * 原资源说明
	*/
	private String originInfo;
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

	private String statusName;

	public String getStatusName() {
		VideoStatusEnum videoStatusEnum = VideoStatusEnum.getByStatus(status);
		return videoStatusEnum == null ? "" : videoStatusEnum.getDesc();
	}
}