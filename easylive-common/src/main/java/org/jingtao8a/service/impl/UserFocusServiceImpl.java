package org.jingtao8a.service.impl;

import org.jingtao8a.entity.po.UserFocus;
import org.jingtao8a.entity.po.UserInfo;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.UserFocusQuery;
import org.jingtao8a.entity.query.UserInfoQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.enums.ResponseCodeEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.mapper.UserFocusMapper;
import org.jingtao8a.mapper.UserInfoMapper;
import org.jingtao8a.service.UserFocusService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
@Description:UserFocusService
@Date:2024-12-19
*/
@Service("userFocusService")
public class UserFocusServiceImpl implements UserFocusService {

	@Resource
	private UserFocusMapper<UserFocus, UserFocusQuery> userFocusMapper;

	@Resource
	private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<UserFocus> findListByParam(UserFocusQuery query) {
		return this.userFocusMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(UserFocusQuery query) {
		return this.userFocusMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<UserFocus> findListByPage(UserFocusQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<UserFocus> userInfoList = findListByParam(query);
		PaginationResultVO<UserFocus> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(UserFocus bean) {
		return userFocusMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(UserFocus bean) {
		return userFocusMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<UserFocus> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return userFocusMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<UserFocus> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return userFocusMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据UserIdAndFocusUserId查询
	*/
	@Override
	public UserFocus selectByUserIdAndFocusUserId(String userId, String focusUserId) {
		return userFocusMapper.selectByUserIdAndFocusUserId(userId, focusUserId);
	}

	/**
	 * 根据UserIdAndFocusUserId更新
	*/
	@Override
	public Long updateByUserIdAndFocusUserId(UserFocus bean, String userId, String focusUserId) {
		return userFocusMapper.updateByUserIdAndFocusUserId(bean, userId, focusUserId);
	}

	/**
	 * 根据UserIdAndFocusUserId删除
	*/
	@Override
	public Long deleteByUserIdAndFocusUserId(String userId, String focusUserId) {
		return userFocusMapper.deleteByUserIdAndFocusUserId(userId, focusUserId);
	}

	@Override
	public void focusUser(String userId, String focusUserId) throws BusinessException {
		if (userId.equals(focusUserId)) {
			throw new BusinessException("不能关注自己");
		}
		UserFocus dbUserFocus = userFocusMapper.selectByUserIdAndFocusUserId(userId, focusUserId);
		if (dbUserFocus != null) {//已经关注
			return;
		}
		UserInfo userInfo = userInfoMapper.selectByUserId(focusUserId);
		if (userInfo == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		UserFocus userFocus = new UserFocus();
		userFocus.setUserId(userId);
		userFocus.setFocusUserId(focusUserId);
		userFocus.setFocusTime(new Date());
		userFocusMapper.insert(userFocus);
	}

	@Override
	public void cancelFocus(String userId, String focusUserId) throws BusinessException {
		userFocusMapper.deleteByUserIdAndFocusUserId(userId, focusUserId);
	}


}