package org.jingtao8a.service.impl;

import org.elasticsearch.common.recycler.Recycler;
import org.jingtao8a.entity.po.UserVideoSeries;
import org.jingtao8a.entity.po.UserVideoSeriesVideo;
import org.jingtao8a.entity.po.VideoComment;
import org.jingtao8a.entity.po.VideoInfo;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.UserVideoSeriesQuery;
import org.jingtao8a.entity.query.UserVideoSeriesVideoQuery;
import org.jingtao8a.entity.query.VideoInfoQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.enums.ResponseCodeEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.mapper.UserVideoSeriesMapper;
import org.jingtao8a.mapper.UserVideoSeriesVideoMapper;
import org.jingtao8a.mapper.VideoInfoMapper;
import org.jingtao8a.service.UserVideoSeriesService;
import org.jingtao8a.service.UserVideoSeriesVideoService;
import org.jingtao8a.utils.StringTools;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
/**
@Description:UserVideoSeriesService
@Date:2024-12-19
*/
@Service("userVideoSeriesService")
public class UserVideoSeriesServiceImpl implements UserVideoSeriesService {

	@Resource
	private UserVideoSeriesMapper<UserVideoSeries, UserVideoSeriesQuery> userVideoSeriesMapper;

    @Resource
    private VideoInfoMapper<VideoInfo, VideoInfoQuery> videoInfoMapper;

	@Resource
	private UserVideoSeriesVideoMapper<UserVideoSeriesVideo, UserVideoSeriesVideoQuery> userVideoSeriesVideoMapper;

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

    @Override
    public List<UserVideoSeries> getUserAllSeries(String userId) {
        return userVideoSeriesMapper.selectUserAllSeries(userId);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveUserVideoSeries(UserVideoSeries userVideoSeries, String videoIds) throws BusinessException {
		if (userVideoSeries.getSeriesId() == null) {//新增
			if (StringTools.isEmpty(videoIds)) {//新增series时,videoIds不能为空
				throw new BusinessException(ResponseCodeEnum.CODE_600);
			}
			checkVideoIds(userVideoSeries.getUserId(), videoIds);
			userVideoSeries.setUpdateTime(new Date());
			Long maxSort = userVideoSeriesMapper.selectMaxSort(userVideoSeries.getUserId());
			userVideoSeries.setSort(maxSort.intValue() + 1);
			userVideoSeriesMapper.insert(userVideoSeries);

			saveUserVideoSeriesVideo(userVideoSeries.getUserId(), userVideoSeries.getSeriesId(), videoIds); //FIXBUG TODO
		} else {//修改
			UserVideoSeries dbUserVideoSeries = userVideoSeriesMapper.selectBySeriesId(userVideoSeries.getSeriesId());
			if (dbUserVideoSeries == null || !dbUserVideoSeries.getUserId().equals(userVideoSeries.getUserId())) {
				throw new BusinessException(ResponseCodeEnum.CODE_600);
			}
			userVideoSeriesMapper.updateBySeriesId(userVideoSeries, dbUserVideoSeries.getSeriesId());
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveUserVideoSeriesVideo(String userId, Integer seriesId, String videoIds) throws BusinessException {
		UserVideoSeries dbUserVideoSeries = userVideoSeriesMapper.selectBySeriesId(seriesId);
		if (dbUserVideoSeries == null || !dbUserVideoSeries.getUserId().equals(userId)) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		checkVideoIds(userId, videoIds);

		String[] videoIdArray = videoIds.split(",");
		Long maxSort = userVideoSeriesVideoMapper.selectMaxSort(seriesId);

		List<UserVideoSeriesVideo> seriesVideoList = new ArrayList<>();
		for (String videoId : videoIdArray) {
			++maxSort;
			UserVideoSeriesVideo userVideoSeriesVideo = new UserVideoSeriesVideo();
			userVideoSeriesVideo.setUserId(userId);
			userVideoSeriesVideo.setVideoId(videoId);
			userVideoSeriesVideo.setSeriesId(seriesId);
			userVideoSeriesVideo.setSort(maxSort.intValue());
			seriesVideoList.add(userVideoSeriesVideo);
		}
		userVideoSeriesVideoMapper.insertBatch(seriesVideoList);
	}

	private void checkVideoIds(String userId, String videoIds) throws BusinessException {
		String[] videoIdArray = videoIds.split(",");
		VideoInfoQuery videoInfoQuery = new VideoInfoQuery();
		videoInfoQuery.setUserId(userId);
		videoInfoQuery.setVideoIdArray(videoIdArray);
		Long count = videoInfoMapper.selectCount(videoInfoQuery);
		if (count != videoIdArray.length) {//存在videoId不属于该userId
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
	}

}