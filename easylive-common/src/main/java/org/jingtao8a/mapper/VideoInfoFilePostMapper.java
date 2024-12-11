package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;
/**
@Description:视频文件信息Mapper
@Date:2024-12-11
*/

public interface VideoInfoFilePostMapper<T,P> extends BaseMapper {
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

	/**
	 * 根据UploadId查询
	*/
	 T selectByUploadId(@Param("uploadId") String uploadId);

	/**
	 * 根据UploadId更新
	*/
	 Long updateByUploadId(@Param("bean") T t, @Param("uploadId") String uploadId);

	/**
	 * 根据UploadId删除
	*/
	 Long deleteByUploadId(@Param("uploadId") String uploadId);

}