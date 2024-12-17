package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;
/**
@Description:视频文件信息Mapper
@Date:2024-12-11
*/

public interface VideoInfoFileMapper<T,P> extends BaseMapper {
	/**
	 * 根据FileId查询
	*/
	 T selectByFileId(@Param("fileId") String fileId);

	/**
	 * 根据FileId更新
	*/
	 Long updateByFileId(@Param("bean") T t, @Param("fileId") String fileId);

	/**
	 * 根据FileId删除
	*/
	 Long deleteByFileId(@Param("fileId") String fileId);

	 Long deleteByParam(@Param("query") P videoInfoFileQuery);
}