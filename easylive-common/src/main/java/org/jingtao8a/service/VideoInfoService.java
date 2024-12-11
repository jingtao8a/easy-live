package org.jingtao8a.service;

import org.jingtao8a.entity.po.VideoInfo;
import org.jingtao8a.entity.query.VideoInfoQuery;
import org.jingtao8a.vo.PaginationResultVO;

import java.util.List;

/**
@Description:视频信息Service
@Date:2024-12-11
*/
public interface VideoInfoService {

	/**
	 * 根据条件查询列表
	*/
	List<VideoInfo> findListByParam(VideoInfoQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(VideoInfoQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<VideoInfo> findListByPage(VideoInfoQuery param);

	/**
	 * 新增
	*/
	Long add(VideoInfo bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(VideoInfo bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<VideoInfo> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<VideoInfo> listBean);

	/**
	 * 根据VideoId查询
	*/
	VideoInfo selectByVideoId(String videoId);

	/**
	 * 根据VideoId更新
	*/
	Long updateByVideoId(VideoInfo bean, String videoId);

	/**
	 * 根据VideoId删除
	*/
	Long deleteByVideoId(String videoId);

}