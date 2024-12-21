package org.jingtao8a.service.impl;

import org.jingtao8a.constants.Constants;
import org.jingtao8a.entity.po.VideoComment;
import org.jingtao8a.entity.po.VideoDanmu;
import org.jingtao8a.entity.po.VideoInfo;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.VideoCommentQuery;
import org.jingtao8a.entity.query.VideoDanmuQuery;
import org.jingtao8a.entity.query.VideoInfoQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.enums.ResponseCodeEnum;
import org.jingtao8a.enums.UserActionTypeEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.mapper.VideoDanmuMapper;
import org.jingtao8a.mapper.VideoInfoMapper;
import org.jingtao8a.service.VideoDanmuService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
/**
@Description:VideoDanmuService
@Date:2024-12-17
*/
@Service("videoDanmuService")
public class VideoDanmuServiceImpl implements VideoDanmuService {

	@Resource
	private VideoDanmuMapper<VideoDanmu, VideoDanmuQuery> videoDanmuMapper;

	@Resource
	private VideoInfoMapper<VideoInfo, VideoInfoQuery> videoInfoMapper;
	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<VideoDanmu> findListByParam(VideoDanmuQuery query) {
		return this.videoDanmuMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(VideoDanmuQuery query) {
		return this.videoDanmuMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<VideoDanmu> findListByPage(VideoDanmuQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<VideoDanmu> userInfoList = findListByParam(query);
		PaginationResultVO<VideoDanmu> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(VideoDanmu bean) {
		return videoDanmuMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(VideoDanmu bean) {
		return videoDanmuMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<VideoDanmu> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoDanmuMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<VideoDanmu> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoDanmuMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据DanmuId查询
	*/
	@Override
	public VideoDanmu selectByDanmuId(Integer danmuId) {
		return videoDanmuMapper.selectByDanmuId(danmuId);
	}

	/**
	 * 根据DanmuId更新
	*/
	@Override
	public Long updateByDanmuId(VideoDanmu bean, Integer danmuId) {
		return videoDanmuMapper.updateByDanmuId(bean, danmuId);
	}

	/**
	 * 根据DanmuId删除
	*/
	@Override
	public Long deleteByDanmuId(Integer danmuId) {
		return videoDanmuMapper.deleteByDanmuId(danmuId);
	}

    @Override
	@Transactional(rollbackFor = Exception.class)
    public void saveVideoDanmu(VideoDanmu videoDanmu) throws BusinessException {
		VideoInfo videoInfo = videoInfoMapper.selectByVideoId(videoDanmu.getVideoId());
		if (videoInfo == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		if (videoInfo.getInteraction() != null && videoInfo.getInteraction().contains(Constants.ONE.toString())) {
			throw new BusinessException("up主已经关闭弹幕");
		}
		videoDanmuMapper.insert(videoDanmu);
		videoInfoMapper.updateCountInfo(videoInfo.getVideoId(), UserActionTypeEnum.VIDEO_DANMU.getField(), 1);
		//TODO 更新 es, 弹幕数量
    }

	@Override
    public void deleteDanmu(Integer danmuId, String userId) throws BusinessException {
		VideoDanmu dbVideoDanmu = videoDanmuMapper.selectByDanmuId(danmuId);
		if (dbVideoDanmu == null) {//弹幕不存在
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		VideoInfo videoInfo = videoInfoMapper.selectByVideoId(dbVideoDanmu.getVideoId());
		if (videoInfo == null) {//视频不存在
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		if (!videoInfo.getUserId().equals(userId) && !dbVideoDanmu.getUserId().equals(userId)) {//只有video的user和danmu的user可以删除该弹幕
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		videoDanmuMapper.deleteByDanmuId(danmuId);
	}
}