package org.jingtao8a.service.impl;

import org.jingtao8a.entity.po.VideoPlayHistory;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.VideoPlayHistoryQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.mapper.VideoPlayHistoryMapper;
import org.jingtao8a.service.VideoPlayHistoryService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
@Description:VideoPlayHistoryService
@Date:2024-12-25
*/
@Service("videoPlayHistoryService")
public class VideoPlayHistoryServiceImpl implements VideoPlayHistoryService {

	@Resource
	private VideoPlayHistoryMapper<VideoPlayHistory, VideoPlayHistoryQuery> videoPlayHistoryMapper;
	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<VideoPlayHistory> findListByParam(VideoPlayHistoryQuery query) {
		return this.videoPlayHistoryMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(VideoPlayHistoryQuery query) {
		return this.videoPlayHistoryMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<VideoPlayHistory> findListByPage(VideoPlayHistoryQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<VideoPlayHistory> userInfoList = findListByParam(query);
		PaginationResultVO<VideoPlayHistory> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(VideoPlayHistory bean) {
		return videoPlayHistoryMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(VideoPlayHistory bean) {
		return videoPlayHistoryMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<VideoPlayHistory> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoPlayHistoryMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<VideoPlayHistory> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoPlayHistoryMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据UserIdAndVideoId查询
	*/
	@Override
	public VideoPlayHistory selectByUserIdAndVideoId(String userId, String videoId) {
		return videoPlayHistoryMapper.selectByUserIdAndVideoId(userId, videoId);
	}

	/**
	 * 根据UserIdAndVideoId更新
	*/
	@Override
	public Long updateByUserIdAndVideoId(VideoPlayHistory bean, String userId, String videoId) {
		return videoPlayHistoryMapper.updateByUserIdAndVideoId(bean, userId, videoId);
	}

	/**
	 * 根据UserIdAndVideoId删除
	*/
	@Override
	public Long deleteByUserIdAndVideoId(String userId, String videoId) {
		return videoPlayHistoryMapper.deleteByUserIdAndVideoId(userId, videoId);
	}

}