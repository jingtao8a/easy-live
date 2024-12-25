package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;
/**
@Description:数据统计信息Mapper
@Date:2024-12-25
*/

public interface StatisticsInfoMapper<T,P> extends BaseMapper {
	/**
	 * 根据StatisticsDateAndUserIdAndDataType查询
	*/
	 T selectByStatisticsDateAndUserIdAndDataType(@Param("statisticsDate") String statisticsDate, @Param("userId") String userId, @Param("dataType") Integer dataType);

	/**
	 * 根据StatisticsDateAndUserIdAndDataType更新
	*/
	 Long updateByStatisticsDateAndUserIdAndDataType(@Param("bean") T t, @Param("statisticsDate") String statisticsDate, @Param("userId") String userId, @Param("dataType") Integer dataType);

	/**
	 * 根据StatisticsDateAndUserIdAndDataType删除
	*/
	 Long deleteByStatisticsDateAndUserIdAndDataType(@Param("statisticsDate") String statisticsDate, @Param("userId") String userId, @Param("dataType") Integer dataType);

}