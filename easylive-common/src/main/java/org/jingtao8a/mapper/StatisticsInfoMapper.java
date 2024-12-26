package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.*;

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

	List<T> selectStatisticsFans(@Param("statisticsDate") String statisticsDate);

	List<T> selectStatisticsComment(@Param("statisticsDate") String statisticsDate);

	List<T> selectStatisticsOthers(@Param("statisticsDate") String statisticsDate, @Param("actionTypeArray") Integer[] actionTypeArray);

	List<T> selectStatisticsDanmu(@Param("statisticsDate") String statisticsDate);

    Map<String, Integer> selectTotalCountInfo(@Param("userId") String userId);
}