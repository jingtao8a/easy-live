package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
@Description:用户视频序列归档Mapper
@Date:2024-12-19
*/

public interface UserVideoSeriesMapper<T,P> extends BaseMapper {
	/**
	 * 根据SeriesId查询
	*/
	 T selectBySeriesId(@Param("seriesId") Integer seriesId);

	/**
	 * 根据SeriesId更新
	*/
	 Long updateBySeriesId(@Param("bean") T t, @Param("seriesId") Integer seriesId);

	/**
	 * 根据SeriesId删除
	*/
	 Long deleteBySeriesId(@Param("seriesId") Integer seriesId);

	List<T> selectUserAllSeries(@Param("userId") String userId);

	Long selectMaxSort(@Param("userId") String userId);
}