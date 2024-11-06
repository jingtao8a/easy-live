package org.jingtao8a.service.impl;
import java.util.List;
import org.jingtao8a.entity.po.UserInfo;
import org.jingtao8a.entity.query.UserInfoQuery;
import org.jingtao8a.vo.PaginationResultVO;
import org.jingtao8a.service.UserInfoService;
import org.jingtao8a.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.entity.query.SimplePage;
/**
@Description:UserInfoService
@Date:2024-11-06
*/
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	@Resource
	private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<UserInfo> findListByParam(UserInfoQuery query) {
		return this.userInfoMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(UserInfoQuery query) {
		return this.userInfoMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<UserInfo> findListByPage(UserInfoQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<UserInfo> userInfoList = findListByParam(query);
		PaginationResultVO<UserInfo> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(UserInfo bean) {
		return userInfoMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(UserInfo bean) {
		return userInfoMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<UserInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return userInfoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<UserInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return userInfoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据UserId查询
	*/
	@Override
	public UserInfo selectByUserId(String userId) {
		return userInfoMapper.selectByUserId(userId);
	}

	/**
	 * 根据UserId更新
	*/
	@Override
	public Long updateByUserId(UserInfo bean, String userId) {
		return userInfoMapper.updateByUserId(bean, userId);
	}

	/**
	 * 根据UserId删除
	*/
	@Override
	public Long deleteByUserId(String userId) {
		return userInfoMapper.deleteByUserId(userId);
	}

	/**
	 * 根据Email查询
	*/
	@Override
	public UserInfo selectByEmail(String email) {
		return userInfoMapper.selectByEmail(email);
	}

	/**
	 * 根据Email更新
	*/
	@Override
	public Long updateByEmail(UserInfo bean, String email) {
		return userInfoMapper.updateByEmail(bean, email);
	}

	/**
	 * 根据Email删除
	*/
	@Override
	public Long deleteByEmail(String email) {
		return userInfoMapper.deleteByEmail(email);
	}

	/**
	 * 根据NickName查询
	*/
	@Override
	public UserInfo selectByNickName(String nickName) {
		return userInfoMapper.selectByNickName(nickName);
	}

	/**
	 * 根据NickName更新
	*/
	@Override
	public Long updateByNickName(UserInfo bean, String nickName) {
		return userInfoMapper.updateByNickName(bean, nickName);
	}

	/**
	 * 根据NickName删除
	*/
	@Override
	public Long deleteByNickName(String nickName) {
		return userInfoMapper.deleteByNickName(nickName);
	}

}