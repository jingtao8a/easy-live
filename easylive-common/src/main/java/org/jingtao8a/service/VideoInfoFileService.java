package org.jingtao8a.service;

import org.jingtao8a.entity.po.VideoInfoFile;
import org.jingtao8a.entity.query.VideoInfoFileQuery;
import org.jingtao8a.vo.PaginationResultVO;

import java.util.List;

/**
@Description:视频文件信息Service
@Date:2024-12-11
*/
public interface VideoInfoFileService {

	/**
	 * 根据条件查询列表
	*/
	List<VideoInfoFile> findListByParam(VideoInfoFileQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(VideoInfoFileQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<VideoInfoFile> findListByPage(VideoInfoFileQuery param);

	/**
	 * 新增
	*/
	Long add(VideoInfoFile bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(VideoInfoFile bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<VideoInfoFile> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<VideoInfoFile> listBean);

	/**
	 * 根据FileId查询
	*/
	VideoInfoFile selectByFileId(String fileId);

	/**
	 * 根据FileId更新
	*/
	Long updateByFileId(VideoInfoFile bean, String fileId);

	/**
	 * 根据FileId删除
	*/
	Long deleteByFileId(String fileId);

}