package org.jingtao8a.service;

import org.jingtao8a.entity.po.StatisticsInfo;
import org.jingtao8a.entity.query.StatisticsInfoQuery;
import org.jingtao8a.vo.PaginationResultVO;

import java.util.List;
import java.util.Map;

/**
@Description:数据统计信息Service
@Date:2024-12-25
*/
public interface StatisticsInfoService {

	/**
	 * 根据条件查询列表
	*/
	List<StatisticsInfo> findListByParam(StatisticsInfoQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(StatisticsInfoQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<StatisticsInfo> findListByPage(StatisticsInfoQuery param);

	/**
	 * 新增
	*/
	Long add(StatisticsInfo bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(StatisticsInfo bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<StatisticsInfo> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<StatisticsInfo> listBean);

	/**
	 * 根据StatisticsDateAndUserIdAndDataType查询
	*/
	StatisticsInfo selectByStatisticsDateAndUserIdAndDataType(String statisticsDate, String userId, Integer dataType);

	/**
	 * 根据StatisticsDateAndUserIdAndDataType更新
	*/
	Long updateByStatisticsDateAndUserIdAndDataType(StatisticsInfo bean, String statisticsDate, String userId, Integer dataType);

	/**
	 * 根据StatisticsDateAndUserIdAndDataType删除
	*/
	Long deleteByStatisticsDateAndUserIdAndDataType(String statisticsDate, String userId, Integer dataType);

	List<StatisticsInfo> statisticData();

	Map<String, Integer> getStatisticsInfoActualTime(String userId);

	List<StatisticsInfo> findListTotalInfoByParam(StatisticsInfoQuery statisticsInfoQuery);

	List<StatisticsInfo> findListUserCountTotalInfoByParam(StatisticsInfoQuery statisticsInfoQuery);
}