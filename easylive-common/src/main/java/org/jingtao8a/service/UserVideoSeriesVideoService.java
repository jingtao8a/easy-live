package org.jingtao8a.service;

import org.jingtao8a.entity.po.UserVideoSeriesVideo;
import org.jingtao8a.entity.query.UserVideoSeriesVideoQuery;
import org.jingtao8a.vo.PaginationResultVO;

import java.util.List;

/**
@Description:用户视频序列归档视频Service
@Date:2024-12-19
*/
public interface UserVideoSeriesVideoService {

	/**
	 * 根据条件查询列表
	*/
	List<UserVideoSeriesVideo> findListByParam(UserVideoSeriesVideoQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(UserVideoSeriesVideoQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<UserVideoSeriesVideo> findListByPage(UserVideoSeriesVideoQuery param);

	/**
	 * 新增
	*/
	Long add(UserVideoSeriesVideo bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(UserVideoSeriesVideo bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<UserVideoSeriesVideo> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<UserVideoSeriesVideo> listBean);

	/**
	 * 根据SeriesIdAndVideoId查询
	*/
	UserVideoSeriesVideo selectBySeriesIdAndVideoId(Integer seriesId, String videoId);

	/**
	 * 根据SeriesIdAndVideoId更新
	*/
	Long updateBySeriesIdAndVideoId(UserVideoSeriesVideo bean, Integer seriesId, String videoId);

	/**
	 * 根据SeriesIdAndVideoId删除
	*/
	Long deleteBySeriesIdAndVideoId(Integer seriesId, String videoId);

}