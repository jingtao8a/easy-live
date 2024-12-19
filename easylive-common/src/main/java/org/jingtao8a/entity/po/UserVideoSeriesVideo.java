package org.jingtao8a.entity.po;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
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
}