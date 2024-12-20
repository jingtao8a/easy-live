package org.jingtao8a.service;

import org.jingtao8a.entity.po.UserVideoSeries;
import org.jingtao8a.entity.query.UserVideoSeriesQuery;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.vo.PaginationResultVO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
@Description:用户视频序列归档Service
@Date:2024-12-19
*/
public interface UserVideoSeriesService {

	/**
	 * 根据条件查询列表
	*/
	List<UserVideoSeries> findListByParam(UserVideoSeriesQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(UserVideoSeriesQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<UserVideoSeries> findListByPage(UserVideoSeriesQuery param);

	/**
	 * 新增
	*/
	Long add(UserVideoSeries bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(UserVideoSeries bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<UserVideoSeries> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<UserVideoSeries> listBean);

	/**
	 * 根据SeriesId查询
	*/
	UserVideoSeries selectBySeriesId(Integer seriesId);

	/**
	 * 根据SeriesId更新
	*/
	Long updateBySeriesId(UserVideoSeries bean, Integer seriesId);

	/**
	 * 根据SeriesId删除
	*/
	Long deleteBySeriesId(Integer seriesId);

	List<UserVideoSeries> getUserAllSeries(String userId);

	void saveUserVideoSeries(UserVideoSeries userVideoSeries, String videoIds) throws BusinessException;

	void saveUserVideoSeriesVideo(String userId, Integer seriesId, String videoIds) throws BusinessException;

	void delUserVideoSeriesVideo(String userId, Integer seriesId, String videoId) throws BusinessException;

	void delVideoSeries(String userId, Integer seriesId) throws BusinessException;

	void changeVideoSeriesSort(String userId, String seriesIds);

	List<UserVideoSeries> findListWithVideoList(UserVideoSeriesQuery userVideoSeriesQuery);
}