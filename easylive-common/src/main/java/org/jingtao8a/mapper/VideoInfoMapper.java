package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;
/**
@Description:视频信息Mapper
@Date:2024-12-11
*/

public interface VideoInfoMapper<T,P> extends BaseMapper {
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

    void updateCountInfo(@Param("videoId") String videoId, @Param("field") String field, @Param("changeCount") Integer changeCount);
}