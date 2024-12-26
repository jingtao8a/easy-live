package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;
/**
@Description:视频播放历史Mapper
@Date:2024-12-25
*/

public interface VideoPlayHistoryMapper<T,P> extends BaseMapper {
	/**
	 * 根据UserIdAndVideoId查询
	*/
	 T selectByUserIdAndVideoId(@Param("userId") String userId, @Param("videoId") String videoId);

	/**
	 * 根据UserIdAndVideoId更新
	*/
	 Long updateByUserIdAndVideoId(@Param("bean") T t, @Param("userId") String userId, @Param("videoId") String videoId);

	/**
	 * 根据UserIdAndVideoId删除
	*/
	 Long deleteByUserIdAndVideoId(@Param("userId") String userId, @Param("videoId") String videoId);

    void deleteByParam(@Param("query") P videoPlayHistoryQuery);
}