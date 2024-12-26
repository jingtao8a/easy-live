package org.jingtao8a.service;

import org.jingtao8a.entity.po.VideoPlayHistory;
import org.jingtao8a.entity.query.VideoPlayHistoryQuery;
import org.jingtao8a.vo.PaginationResultVO;

import java.util.List;

/**
@Description:视频播放历史Service
@Date:2024-12-25
*/
public interface VideoPlayHistoryService {

	/**
	 * 根据条件查询列表
	*/
	List<VideoPlayHistory> findListByParam(VideoPlayHistoryQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(VideoPlayHistoryQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<VideoPlayHistory> findListByPage(VideoPlayHistoryQuery param);

	/**
	 * 新增
	*/
	Long add(VideoPlayHistory bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(VideoPlayHistory bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<VideoPlayHistory> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<VideoPlayHistory> listBean);

	/**
	 * 根据UserIdAndVideoId查询
	*/
	VideoPlayHistory selectByUserIdAndVideoId(String userId, String videoId);

	/**
	 * 根据UserIdAndVideoId更新
	*/
	Long updateByUserIdAndVideoId(VideoPlayHistory bean, String userId, String videoId);

	/**
	 * 根据UserIdAndVideoId删除
	*/
	Long deleteByUserIdAndVideoId(String userId, String videoId);

    void saveHistory(String userId, String videoId, Integer fileIndx);

	void deleteByParam(VideoPlayHistoryQuery videoPlayHistoryQuery);
}