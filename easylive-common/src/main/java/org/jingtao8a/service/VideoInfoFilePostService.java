package org.jingtao8a.service;

import org.jingtao8a.entity.po.VideoInfoFilePost;
import org.jingtao8a.entity.query.VideoInfoFilePostQuery;
import org.jingtao8a.vo.PaginationResultVO;

import java.util.List;

/**
@Description:视频文件信息Service
@Date:2024-12-11
*/
public interface VideoInfoFilePostService {

	/**
	 * 根据条件查询列表
	*/
	List<VideoInfoFilePost> findListByParam(VideoInfoFilePostQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(VideoInfoFilePostQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<VideoInfoFilePost> findListByPage(VideoInfoFilePostQuery param);

	/**
	 * 新增
	*/
	Long add(VideoInfoFilePost bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(VideoInfoFilePost bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<VideoInfoFilePost> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<VideoInfoFilePost> listBean);

	/**
	 * 根据FileId查询
	*/
	VideoInfoFilePost selectByFileId(String fileId);

	/**
	 * 根据FileId更新
	*/
	Long updateByFileId(VideoInfoFilePost bean, String fileId);

	/**
	 * 根据FileId删除
	*/
	Long deleteByFileId(String fileId);

	/**
	 * 根据UploadId查询
	*/
	VideoInfoFilePost selectByUploadId(String uploadId);

	/**
	 * 根据UploadId更新
	*/
	Long updateByUploadId(VideoInfoFilePost bean, String uploadId);

	/**
	 * 根据UploadId删除
	*/
	Long deleteByUploadId(String uploadId);

	void transferVideoFile(VideoInfoFilePost videoInfoFilePost);
}