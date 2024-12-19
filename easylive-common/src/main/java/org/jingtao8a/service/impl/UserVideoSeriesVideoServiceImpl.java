package org.jingtao8a.service.impl;

import org.jingtao8a.entity.po.UserVideoSeriesVideo;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.UserVideoSeriesVideoQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.mapper.UserVideoSeriesVideoMapper;
import org.jingtao8a.service.UserVideoSeriesVideoService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
@Description:UserVideoSeriesVideoService
@Date:2024-12-19
*/
@Service("userVideoSeriesVideoService")
public class UserVideoSeriesVideoServiceImpl implements UserVideoSeriesVideoService {

	@Resource
	private UserVideoSeriesVideoMapper<UserVideoSeriesVideo, UserVideoSeriesVideoQuery> userVideoSeriesVideoMapper;
	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<UserVideoSeriesVideo> findListByParam(UserVideoSeriesVideoQuery query) {
		return this.userVideoSeriesVideoMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(UserVideoSeriesVideoQuery query) {
		return this.userVideoSeriesVideoMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<UserVideoSeriesVideo> findListByPage(UserVideoSeriesVideoQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<UserVideoSeriesVideo> userInfoList = findListByParam(query);
		PaginationResultVO<UserVideoSeriesVideo> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(UserVideoSeriesVideo bean) {
		return userVideoSeriesVideoMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(UserVideoSeriesVideo bean) {
		return userVideoSeriesVideoMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<UserVideoSeriesVideo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return userVideoSeriesVideoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<UserVideoSeriesVideo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return userVideoSeriesVideoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据SeriesIdAndVideoId查询
	*/
	@Override
	public UserVideoSeriesVideo selectBySeriesIdAndVideoId(Integer seriesId, String videoId) {
		return userVideoSeriesVideoMapper.selectBySeriesIdAndVideoId(seriesId, videoId);
	}

	/**
	 * 根据SeriesIdAndVideoId更新
	*/
	@Override
	public Long updateBySeriesIdAndVideoId(UserVideoSeriesVideo bean, Integer seriesId, String videoId) {
		return userVideoSeriesVideoMapper.updateBySeriesIdAndVideoId(bean, seriesId, videoId);
	}

	/**
	 * 根据SeriesIdAndVideoId删除
	*/
	@Override
	public Long deleteBySeriesIdAndVideoId(Integer seriesId, String videoId) {
		return userVideoSeriesVideoMapper.deleteBySeriesIdAndVideoId(seriesId, videoId);
	}

}