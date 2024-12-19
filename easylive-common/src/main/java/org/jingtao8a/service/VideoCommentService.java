package org.jingtao8a.service;

import org.jingtao8a.entity.po.VideoComment;
import org.jingtao8a.entity.query.VideoCommentQuery;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.vo.PaginationResultVO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
@Description:评论Service
@Date:2024-12-17
*/
public interface VideoCommentService {

	/**
	 * 根据条件查询列表
	*/
	List<VideoComment> findListByParam(VideoCommentQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(VideoCommentQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<VideoComment> findListByPage(VideoCommentQuery param);

	/**
	 * 新增
	*/
	Long add(VideoComment bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(VideoComment bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<VideoComment> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<VideoComment> listBean);

	/**
	 * 根据CommentId查询
	*/
	VideoComment selectByCommentId(Integer commentId);

	/**
	 * 根据CommentId更新
	*/
	Long updateByCommentId(VideoComment bean, Integer commentId);

	/**
	 * 根据CommentId删除
	*/
	Long deleteByCommentId(Integer commentId);

    void postComment(VideoComment videoComment, Integer replyCommentId) throws BusinessException;

	void topComment(Integer commentId, String userId) throws BusinessException;

	void cancelTopComment(Integer commentId, String userId) throws BusinessException;

	void deleteComment(Integer commentId, String userId) throws BusinessException;
}