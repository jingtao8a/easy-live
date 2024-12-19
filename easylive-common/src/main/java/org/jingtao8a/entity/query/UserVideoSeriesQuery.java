package org.jingtao8a.entity.query;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
/**
@Description:用户视频序列归档
@Date:2024-12-19
*/
@Data
@ToString
public class UserVideoSeriesQuery extends BaseQuery {
	/**
	 * 列表ID
	*/
	private Integer seriesId;
	/**
	 * 列表名称
	*/
	private String seriesName;
	private String seriesNameFuzzy;

	/**
	 * 描述
	*/
	private String seriesDescription;
	private String seriesDescriptionFuzzy;

	/**
	 * 用户ID
	*/
	private String userId;
	private String userIdFuzzy;

	/**
	 * 排序
	*/
	private Integer sort;
	/**
	 * 更新时间
	*/
	private Date updateTime;
	private String updateTimeStart;
	private String updateTimeEnd;

}