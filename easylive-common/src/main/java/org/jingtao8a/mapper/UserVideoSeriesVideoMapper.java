package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Property;

/**
@Description:用户视频序列归档视频Mapper
@Date:2024-12-19
*/

public interface UserVideoSeriesVideoMapper<T,P> extends BaseMapper {
	/**
	 * 根据SeriesIdAndVideoId查询
	*/
	 T selectBySeriesIdAndVideoId(@Param("seriesId") Integer seriesId, @Param("videoId") String videoId);

	/**
	 * 根据SeriesIdAndVideoId更新
	*/
	 Long updateBySeriesIdAndVideoId(@Param("bean") T t, @Param("seriesId") Integer seriesId, @Param("videoId") String videoId);

	/**
	 * 根据SeriesIdAndVideoId删除
	*/
	 Long deleteBySeriesIdAndVideoId(@Param("seriesId") Integer seriesId, @Param("videoId") String videoId);

	 Long selectMaxSort(@Param("seriesId") Integer seriesId);

	 Long deleteByParam(@Param("query") P userVideoSeriesVideoQuery);
}