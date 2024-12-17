package org.jingtao8a.service.impl;

import org.jingtao8a.entity.po.UserAction;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.UserActionQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.mapper.UserActionMapper;
import org.jingtao8a.service.UserActionService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
@Description:UserActionService
@Date:2024-12-17
*/
@Service("userActionService")
public class UserActionServiceImpl implements UserActionService {

	@Resource
	private UserActionMapper<UserAction, UserActionQuery> userActionMapper;
	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<UserAction> findListByParam(UserActionQuery query) {
		return this.userActionMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(UserActionQuery query) {
		return this.userActionMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<UserAction> findListByPage(UserActionQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<UserAction> userInfoList = findListByParam(query);
		PaginationResultVO<UserAction> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(UserAction bean) {
		return userActionMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(UserAction bean) {
		return userActionMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<UserAction> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return userActionMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<UserAction> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return userActionMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据ActionId查询
	*/
	@Override
	public UserAction selectByActionId(Integer actionId) {
		return userActionMapper.selectByActionId(actionId);
	}

	/**
	 * 根据ActionId更新
	*/
	@Override
	public Long updateByActionId(UserAction bean, Integer actionId) {
		return userActionMapper.updateByActionId(bean, actionId);
	}

	/**
	 * 根据ActionId删除
	*/
	@Override
	public Long deleteByActionId(Integer actionId) {
		return userActionMapper.deleteByActionId(actionId);
	}

	/**
	 * 根据VideoIdAndCommentIdAndActionTypeAndUserId查询
	*/
	@Override
	public UserAction selectByVideoIdAndCommentIdAndActionTypeAndUserId(String videoId, Integer commentId, Integer actionType, String userId) {
		return userActionMapper.selectByVideoIdAndCommentIdAndActionTypeAndUserId(videoId, commentId, actionType, userId);
	}

	/**
	 * 根据VideoIdAndCommentIdAndActionTypeAndUserId更新
	*/
	@Override
	public Long updateByVideoIdAndCommentIdAndActionTypeAndUserId(UserAction bean, String videoId, Integer commentId, Integer actionType, String userId) {
		return userActionMapper.updateByVideoIdAndCommentIdAndActionTypeAndUserId(bean, videoId, commentId, actionType, userId);
	}

	/**
	 * 根据VideoIdAndCommentIdAndActionTypeAndUserId删除
	*/
	@Override
	public Long deleteByVideoIdAndCommentIdAndActionTypeAndUserId(String videoId, Integer commentId, Integer actionType, String userId) {
		return userActionMapper.deleteByVideoIdAndCommentIdAndActionTypeAndUserId(videoId, commentId, actionType, userId);
	}

}