package org.jingtao8a.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
@Description:用户视频序列归档视频
@Date:2024-12-19
*/
@Data
@ToString
public class UserVideoSeriesVideo implements Serializable {
	/**
	 * 列表ID
	*/
	private Integer seriesId;
	/**
	 * 视频ID
	*/
	private String videoId;
	/**
	 * 用户ID
	*/
	private String userId;
	/**
	 * 排序
	*/
	private Integer sort;

	private String videoCover;

	private String videoName;

	private Integer playCount;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
}