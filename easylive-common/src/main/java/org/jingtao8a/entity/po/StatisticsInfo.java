package org.jingtao8a.entity.po;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
/**
@Description:数据统计信息
@Date:2024-12-25
*/
@Data
@ToString
public class StatisticsInfo implements Serializable {
	/**
	 * 统计日期
	*/
	private String statisticsDate;
	/**
	 * 用户ID
	*/
	private String userId;
	/**
	 * 数据统计类型
	*/
	private Integer dataType;
	/**
	 * 统计数量
	*/
	private Integer statisticsCount;
}