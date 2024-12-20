package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;
import org.jingtao8a.entity.query.VideoDanmuQuery;

/**
@Description:视频弹幕Mapper
@Date:2024-12-17
*/

public interface VideoDanmuMapper<T,P> extends BaseMapper {
	/**
	 * 根据DanmuId查询
	*/
	 T selectByDanmuId(@Param("danmuId") Integer danmuId);

	/**
	 * 根据DanmuId更新
	*/
	 Long updateByDanmuId(@Param("bean") T t, @Param("danmuId") Integer danmuId);

	/**
	 * 根据DanmuId删除
	*/
	 Long deleteByDanmuId(@Param("danmuId") Integer danmuId);

    void deleteByParam(@Param("query") VideoDanmuQuery videoDanmuQuery);
}