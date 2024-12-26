package org.jingtao8a.entity.query;
import lombok.Data;
import lombok.ToString;
/**
@Description:数据统计信息
@Date:2024-12-25
*/
@Data
@ToString
public class StatisticsInfoQuery extends BaseQuery {
	/**
	 * 统计日期
	*/
	private String statisticsDate;
	private String statisticsDateFuzzy;

	/**
	 * 用户ID
	*/
	private String userId;
	private String userIdFuzzy;

	/**
	 * 数据统计类型
	*/
	private Integer dataType;
	/**
	 * 统计数量
	*/
	private Integer statisticsCount;

	private String statisticsDateStart;

	private String statisticsDateEnd;
}