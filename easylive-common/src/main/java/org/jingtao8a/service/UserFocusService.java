package org.jingtao8a.service;

import org.jingtao8a.entity.po.UserFocus;
import org.jingtao8a.entity.query.UserFocusQuery;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.vo.PaginationResultVO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
@Description:用户关注表Service
@Date:2024-12-19
*/
public interface UserFocusService {

	/**
	 * 根据条件查询列表
	*/
	List<UserFocus> findListByParam(UserFocusQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(UserFocusQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<UserFocus> findListByPage(UserFocusQuery param);

	/**
	 * 新增
	*/
	Long add(UserFocus bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(UserFocus bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<UserFocus> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<UserFocus> listBean);

	/**
	 * 根据UserIdAndFocusUserId查询
	*/
	UserFocus selectByUserIdAndFocusUserId(String userId, String focusUserId);

	/**
	 * 根据UserIdAndFocusUserId更新
	*/
	Long updateByUserIdAndFocusUserId(UserFocus bean, String userId, String focusUserId);

	/**
	 * 根据UserIdAndFocusUserId删除
	*/
	Long deleteByUserIdAndFocusUserId(String userId, String focusUserId);

	void focusUser(String userId, String focusUserId) throws BusinessException;

	void cancelFocus(String userId, String focusUserId) throws BusinessException;
}