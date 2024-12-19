package org.jingtao8a.service.impl;

import org.jingtao8a.entity.po.UserVideoSeries;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.UserVideoSeriesQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.mapper.UserVideoSeriesMapper;
import org.jingtao8a.service.UserVideoSeriesService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
@Description:UserVideoSeriesService
@Date:2024-12-19
*/
@Service("userVideoSeriesService")
public class UserVideoSeriesServiceImpl implements UserVideoSeriesService {

	@Resource
	private UserVideoSeriesMapper<UserVideoSeries, UserVideoSeriesQuery> userVideoSeriesMapper;
	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<UserVideoSeries> findListByParam(UserVideoSeriesQuery query) {
		return this.userVideoSeriesMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(UserVideoSeriesQuery query) {
		return this.userVideoSeriesMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<UserVideoSeries> findListByPage(UserVideoSeriesQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<UserVideoSeries> userInfoList = findListByParam(query);
		PaginationResultVO<UserVideoSeries> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(UserVideoSeries bean) {
		return userVideoSeriesMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(UserVideoSeries bean) {
		return userVideoSeriesMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<UserVideoSeries> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return userVideoSeriesMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<UserVideoSeries> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return userVideoSeriesMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据SeriesId查询
	*/
	@Override
	public UserVideoSeries selectBySeriesId(Integer seriesId) {
		return userVideoSeriesMapper.selectBySeriesId(seriesId);
	}

	/**
	 * 根据SeriesId更新
	*/
	@Override
	public Long updateBySeriesId(UserVideoSeries bean, Integer seriesId) {
		return userVideoSeriesMapper.updateBySeriesId(bean, seriesId);
	}

	/**
	 * 根据SeriesId删除
	*/
	@Override
	public Long deleteBySeriesId(Integer seriesId) {
		return userVideoSeriesMapper.deleteBySeriesId(seriesId);
	}

}