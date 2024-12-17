package org.jingtao8a.service.impl;

import org.jingtao8a.entity.po.VideoComment;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.VideoCommentQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.mapper.VideoCommentMapper;
import org.jingtao8a.service.VideoCommentService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
@Description:VideoCommentService
@Date:2024-12-17
*/
@Service("videoCommentService")
public class VideoCommentServiceImpl implements VideoCommentService {

	@Resource
	private VideoCommentMapper<VideoComment, VideoCommentQuery> videoCommentMapper;
	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<VideoComment> findListByParam(VideoCommentQuery query) {
		return this.videoCommentMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(VideoCommentQuery query) {
		return this.videoCommentMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<VideoComment> findListByPage(VideoCommentQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<VideoComment> userInfoList = findListByParam(query);
		PaginationResultVO<VideoComment> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(VideoComment bean) {
		return videoCommentMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(VideoComment bean) {
		return videoCommentMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<VideoComment> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoCommentMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<VideoComment> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoCommentMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据CommentId查询
	*/
	@Override
	public VideoComment selectByCommentId(Integer commentId) {
		return videoCommentMapper.selectByCommentId(commentId);
	}

	/**
	 * 根据CommentId更新
	*/
	@Override
	public Long updateByCommentId(VideoComment bean, Integer commentId) {
		return videoCommentMapper.updateByCommentId(bean, commentId);
	}

	/**
	 * 根据CommentId删除
	*/
	@Override
	public Long deleteByCommentId(Integer commentId) {
		return videoCommentMapper.deleteByCommentId(commentId);
	}

}