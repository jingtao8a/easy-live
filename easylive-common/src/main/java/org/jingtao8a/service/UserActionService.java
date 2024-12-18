package org.jingtao8a.service;

import org.jingtao8a.entity.po.UserAction;
import org.jingtao8a.entity.query.UserActionQuery;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.vo.PaginationResultVO;

import java.util.List;

/**
@Description:用户行为 点赞、评论Service
@Date:2024-12-17
*/
public interface UserActionService {

	/**
	 * 根据条件查询列表
	*/
	List<UserAction> findListByParam(UserActionQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(UserActionQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<UserAction> findListByPage(UserActionQuery param);

	/**
	 * 新增
	*/
	Long add(UserAction bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(UserAction bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<UserAction> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<UserAction> listBean);

	/**
	 * 根据ActionId查询
	*/
	UserAction selectByActionId(Integer actionId);

	/**
	 * 根据ActionId更新
	*/
	Long updateByActionId(UserAction bean, Integer actionId);

	/**
	 * 根据ActionId删除
	*/
	Long deleteByActionId(Integer actionId);

	/**
	 * 根据VideoIdAndCommentIdAndActionTypeAndUserId查询
	*/
	UserAction selectByVideoIdAndCommentIdAndActionTypeAndUserId(String videoId, Integer commentId, Integer actionType, String userId);

	/**
	 * 根据VideoIdAndCommentIdAndActionTypeAndUserId更新
	*/
	Long updateByVideoIdAndCommentIdAndActionTypeAndUserId(UserAction bean, String videoId, Integer commentId, Integer actionType, String userId);

	/**
	 * 根据VideoIdAndCommentIdAndActionTypeAndUserId删除
	*/
	Long deleteByVideoIdAndCommentIdAndActionTypeAndUserId(String videoId, Integer commentId, Integer actionType, String userId);

    void saveAction(UserAction userAction) throws BusinessException;
}