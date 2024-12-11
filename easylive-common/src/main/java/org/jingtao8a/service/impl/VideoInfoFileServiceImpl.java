package org.jingtao8a.service.impl;

import org.jingtao8a.entity.po.VideoInfoFile;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.VideoInfoFileQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.mapper.VideoInfoFileMapper;
import org.jingtao8a.service.VideoInfoFileService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
@Description:VideoInfoFileService
@Date:2024-12-11
*/
@Service("videoInfoFileService")
public class VideoInfoFileServiceImpl implements VideoInfoFileService {

	@Resource
	private VideoInfoFileMapper<VideoInfoFile, VideoInfoFileQuery> videoInfoFileMapper;
	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<VideoInfoFile> findListByParam(VideoInfoFileQuery query) {
		return this.videoInfoFileMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(VideoInfoFileQuery query) {
		return this.videoInfoFileMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<VideoInfoFile> findListByPage(VideoInfoFileQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<VideoInfoFile> userInfoList = findListByParam(query);
		PaginationResultVO<VideoInfoFile> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(VideoInfoFile bean) {
		return videoInfoFileMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(VideoInfoFile bean) {
		return videoInfoFileMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<VideoInfoFile> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoInfoFileMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<VideoInfoFile> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoInfoFileMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据FileId查询
	*/
	@Override
	public VideoInfoFile selectByFileId(String fileId) {
		return videoInfoFileMapper.selectByFileId(fileId);
	}

	/**
	 * 根据FileId更新
	*/
	@Override
	public Long updateByFileId(VideoInfoFile bean, String fileId) {
		return videoInfoFileMapper.updateByFileId(bean, fileId);
	}

	/**
	 * 根据FileId删除
	*/
	@Override
	public Long deleteByFileId(String fileId) {
		return videoInfoFileMapper.deleteByFileId(fileId);
	}

}