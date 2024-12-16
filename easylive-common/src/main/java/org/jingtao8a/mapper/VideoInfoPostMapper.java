package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;
/**
@Description:视频信息Mapper
@Date:2024-12-11
*/

public interface VideoInfoPostMapper<T,P> extends BaseMapper {
	Long updateByParam(@Param("bean") T t, @Param("query") P param);
	/**
	 * 根据VideoId查询
	*/
	 T selectByVideoId(@Param("videoId") String videoId);

	/**
	 * 根据VideoId更新
	*/
	 Long updateByVideoId(@Param("bean") T t, @Param("videoId") String videoId);

	/**
	 * 根据VideoId删除
	*/
	 Long deleteByVideoId(@Param("videoId") String videoId);

}