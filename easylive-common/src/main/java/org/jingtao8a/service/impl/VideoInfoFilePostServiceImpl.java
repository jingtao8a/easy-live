package org.jingtao8a.service.impl;

import org.jingtao8a.entity.po.VideoInfoFilePost;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.VideoInfoFilePostQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.mapper.VideoInfoFilePostMapper;
import org.jingtao8a.service.VideoInfoFilePostService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
@Description:VideoInfoFilePostService
@Date:2024-12-11
*/
@Service("videoInfoFilePostService")
public class VideoInfoFilePostServiceImpl implements VideoInfoFilePostService {

	@Resource
	private VideoInfoFilePostMapper<VideoInfoFilePost, VideoInfoFilePostQuery> videoInfoFilePostMapper;
	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<VideoInfoFilePost> findListByParam(VideoInfoFilePostQuery query) {
		return this.videoInfoFilePostMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(VideoInfoFilePostQuery query) {
		return this.videoInfoFilePostMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<VideoInfoFilePost> findListByPage(VideoInfoFilePostQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<VideoInfoFilePost> userInfoList = findListByParam(query);
		PaginationResultVO<VideoInfoFilePost> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(VideoInfoFilePost bean) {
		return videoInfoFilePostMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(VideoInfoFilePost bean) {
		return videoInfoFilePostMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<VideoInfoFilePost> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoInfoFilePostMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<VideoInfoFilePost> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoInfoFilePostMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据FileId查询
	*/
	@Override
	public VideoInfoFilePost selectByFileId(String fileId) {
		return videoInfoFilePostMapper.selectByFileId(fileId);
	}

	/**
	 * 根据FileId更新
	*/
	@Override
	public Long updateByFileId(VideoInfoFilePost bean, String fileId) {
		return videoInfoFilePostMapper.updateByFileId(bean, fileId);
	}

	/**
	 * 根据FileId删除
	*/
	@Override
	public Long deleteByFileId(String fileId) {
		return videoInfoFilePostMapper.deleteByFileId(fileId);
	}

	/**
	 * 根据UploadId查询
	*/
	@Override
	public VideoInfoFilePost selectByUploadId(String uploadId) {
		return videoInfoFilePostMapper.selectByUploadId(uploadId);
	}

	/**
	 * 根据UploadId更新
	*/
	@Override
	public Long updateByUploadId(VideoInfoFilePost bean, String uploadId) {
		return videoInfoFilePostMapper.updateByUploadId(bean, uploadId);
	}

	/**
	 * 根据UploadId删除
	*/
	@Override
	public Long deleteByUploadId(String uploadId) {
		return videoInfoFilePostMapper.deleteByUploadId(uploadId);
	}

}