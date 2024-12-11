package org.jingtao8a.service.impl;

import org.jingtao8a.entity.po.VideoInfoPost;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.VideoInfoPostQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.mapper.VideoInfoPostMapper;
import org.jingtao8a.service.VideoInfoPostService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
@Description:VideoInfoPostService
@Date:2024-12-11
*/
@Service("videoInfoPostService")
public class VideoInfoPostServiceImpl implements VideoInfoPostService {

	@Resource
	private VideoInfoPostMapper<VideoInfoPost, VideoInfoPostQuery> videoInfoPostMapper;
	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<VideoInfoPost> findListByParam(VideoInfoPostQuery query) {
		return this.videoInfoPostMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(VideoInfoPostQuery query) {
		return this.videoInfoPostMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<VideoInfoPost> findListByPage(VideoInfoPostQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<VideoInfoPost> userInfoList = findListByParam(query);
		PaginationResultVO<VideoInfoPost> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(VideoInfoPost bean) {
		return videoInfoPostMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(VideoInfoPost bean) {
		return videoInfoPostMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<VideoInfoPost> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoInfoPostMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<VideoInfoPost> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoInfoPostMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据VideoId查询
	*/
	@Override
	public VideoInfoPost selectByVideoId(String videoId) {
		return videoInfoPostMapper.selectByVideoId(videoId);
	}

	/**
	 * 根据VideoId更新
	*/
	@Override
	public Long updateByVideoId(VideoInfoPost bean, String videoId) {
		return videoInfoPostMapper.updateByVideoId(bean, videoId);
	}

	/**
	 * 根据VideoId删除
	*/
	@Override
	public Long deleteByVideoId(String videoId) {
		return videoInfoPostMapper.deleteByVideoId(videoId);
	}

}