package org.jingtao8a.service.impl;

import org.jingtao8a.component.EsSearchComponent;
import org.jingtao8a.constants.Constants;
import org.jingtao8a.entity.po.VideoInfo;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.VideoInfoQuery;
import org.jingtao8a.enums.*;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.mapper.VideoInfoMapper;
import org.jingtao8a.service.VideoInfoService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
@Description:VideoInfoService
@Date:2024-12-11
*/
@Service("videoInfoService")
public class VideoInfoServiceImpl implements VideoInfoService {

	@Resource
	private VideoInfoMapper<VideoInfo, VideoInfoQuery> videoInfoMapper;

	@Resource
	private EsSearchComponent esSearchComponent;

	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<VideoInfo> findListByParam(VideoInfoQuery query) {
		return this.videoInfoMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(VideoInfoQuery query) {
		return this.videoInfoMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<VideoInfo> findListByPage(VideoInfoQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<VideoInfo> userInfoList = findListByParam(query);
		PaginationResultVO<VideoInfo> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(VideoInfo bean) {
		return videoInfoMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(VideoInfo bean) {
		return videoInfoMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<VideoInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoInfoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<VideoInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoInfoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据VideoId查询
	*/
	@Override
	public VideoInfo selectByVideoId(String videoId) {
		return videoInfoMapper.selectByVideoId(videoId);
	}

	/**
	 * 根据VideoId更新
	*/
	@Override
	public Long updateByVideoId(VideoInfo bean, String videoId) {
		return videoInfoMapper.updateByVideoId(bean, videoId);
	}

	/**
	 * 根据VideoId删除
	*/
	@Override
	public Long deleteByVideoId(String videoId) {
		return videoInfoMapper.deleteByVideoId(videoId);
	}

    @Override
	@Transactional(rollbackFor = Exception.class)
    public void addReadCount(String videoId) throws BusinessException {
        videoInfoMapper.updateCountInfo(videoId, UserActionTypeEnum.VIDEO_PLAY.getField(), Constants.ONE);
    	VideoInfo videoInfo = new VideoInfo();
		videoInfo.setLastPlayTime(new Date());
		videoInfoMapper.updateByVideoId(videoInfo, videoId);
		//更新es
		esSearchComponent.updateDocCount(videoId, SearchOrderTypeEnum.VIDEO_PLAY.getField(), 1);
	}

	@Override
	public void recommendVideo(String videoId) throws BusinessException {
		VideoInfo videoInfo = videoInfoMapper.selectByVideoId(videoId);
		if (videoInfo == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		VideoRecommendTypeEnum videoRecommendTypeEnum = null;
		if (VideoRecommendTypeEnum.RECOMMEND.getType().equals(videoInfo.getRecommendType())) {
			videoRecommendTypeEnum = VideoRecommendTypeEnum.NO_RECOMMEND;
		} else {
			videoRecommendTypeEnum = VideoRecommendTypeEnum.RECOMMEND;
		}
		VideoInfo updateVideoInfo = new VideoInfo();
		updateVideoInfo.setRecommendType(videoRecommendTypeEnum.getType());
		videoInfoMapper.updateByVideoId(updateVideoInfo, videoId);
	}
}