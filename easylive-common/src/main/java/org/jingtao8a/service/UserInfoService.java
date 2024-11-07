package org.jingtao8a.service;
import java.util.List;

import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.entity.po.UserInfo;
import org.jingtao8a.entity.query.UserInfoQuery;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.vo.PaginationResultVO;

/**
@Description:用户信息Service
@Date:2024-11-06
*/
public interface UserInfoService {

	/**
	 * 根据条件查询列表
	*/
	List<UserInfo> findListByParam(UserInfoQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(UserInfoQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<UserInfo> findListByPage(UserInfoQuery param);

	/**
	 * 新增
	*/
	Long add(UserInfo bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(UserInfo bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<UserInfo> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<UserInfo> listBean);

	/**
	 * 根据UserId查询
	*/
	UserInfo selectByUserId(String userId);

	/**
	 * 根据UserId更新
	*/
	Long updateByUserId(UserInfo bean, String userId);

	/**
	 * 根据UserId删除
	*/
	Long deleteByUserId(String userId);

	/**
	 * 根据Email查询
	*/
	UserInfo selectByEmail(String email);

	/**
	 * 根据Email更新
	*/
	Long updateByEmail(UserInfo bean, String email);

	/**
	 * 根据Email删除
	*/
	Long deleteByEmail(String email);

	/**
	 * 根据NickName查询
	*/
	UserInfo selectByNickName(String nickName);

	/**
	 * 根据NickName更新
	*/
	Long updateByNickName(UserInfo bean, String nickName);

	/**
	 * 根据NickName删除
	*/
	Long deleteByNickName(String nickName);

	void register(String email, String nickName, String registerPassword) throws BusinessException;

	TokenUserInfoDto login(String email, String password, String ip) throws BusinessException;
}
