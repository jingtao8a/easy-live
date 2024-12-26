package org.jingtao8a.service.impl;
import java.util.Date;
import java.util.List;

import org.jingtao8a.component.RedisComponent;
import org.jingtao8a.constants.Constants;
import org.jingtao8a.dto.SysSettingDto;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.dto.UserCountInfoDto;
import org.jingtao8a.entity.po.UserFocus;
import org.jingtao8a.entity.po.UserInfo;
import org.jingtao8a.entity.query.UserFocusQuery;
import org.jingtao8a.entity.query.UserInfoQuery;
import org.jingtao8a.enums.ResponseCodeEnum;
import org.jingtao8a.enums.UserSexEnum;
import org.jingtao8a.enums.UserStatusEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.mapper.UserFocusMapper;
import org.jingtao8a.utils.CopyTools;
import org.jingtao8a.utils.StringTools;
import org.jingtao8a.vo.PaginationResultVO;
import org.jingtao8a.service.UserInfoService;
import org.jingtao8a.mapper.UserInfoMapper;
import org.jingtao8a.vo.UserInfoVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.entity.query.SimplePage;
import org.springframework.transaction.annotation.Transactional;

/**
@Description:UserInfoService
@Date:2024-11-06
*/
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	@Resource
	private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

	@Resource
	private RedisComponent redisComponent;

	@Resource
	private UserFocusMapper<UserFocus, UserFocusQuery> userFocusMapper;
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

	@Override
	public void register(String email, String nickName, String registerPassword) throws BusinessException {
		if (null != userInfoMapper.selectByEmail(email)) {
			throw new BusinessException("该邮箱已经注册");
		}
		if (null != userInfoMapper.selectByNickName(nickName)) {
			throw new BusinessException("该昵称已经使用");
		}
		UserInfo userInfo = new UserInfo();
		String userId = StringTools.getRandomNumber(Constants.LENGTH_10);
		userInfo.setUserId(userId);
		userInfo.setEmail(email);
		userInfo.setNickName(nickName);
		userInfo.setPassword(StringTools.encodeByMd5(registerPassword));//密码使用md5加密后再存储
		userInfo.setJoinTime(new Date());
		userInfo.setStatus(UserStatusEnum.ENABLE.getStatus());
		userInfo.setSex(UserSexEnum.SECRECY.getType());
		userInfo.setTheme(Constants.ONE);
		//注册初始化 用户的硬币
		SysSettingDto sysSettingDto = redisComponent.getSysSettingDto();
		userInfo.setTotalCoinCount(sysSettingDto.getRegisterCoinCount());
		userInfo.setCurrentCoinCount(sysSettingDto.getRegisterCoinCount());

		userInfoMapper.insert(userInfo);
	}

	@Override
	public TokenUserInfoDto login(String email, String password, String ip) throws BusinessException {
		UserInfo userInfo = userInfoMapper.selectByEmail(email);
		if (null == userInfo || !userInfo.getPassword().equals(password)) {
			throw new BusinessException("账号或者密码不存在");
		}
		if (UserStatusEnum.DISABLE.getStatus().equals(userInfo.getStatus())) {
			throw new BusinessException("账号已禁用");
		}

		userInfo.setLastLoginIp(ip);
		userInfo.setLastLoginTime(new Date());
		this.userInfoMapper.updateByUserId(userInfo, userInfo.getUserId());

		TokenUserInfoDto tokenUserInfoDto = CopyTools.copy(userInfo, TokenUserInfoDto.class);
		redisComponent.saveTokenInfo(tokenUserInfoDto);

		return tokenUserInfoDto;
	}

    @Override
    public UserInfoVO getUserDetailInfo(String currentUserId, String userId) throws BusinessException {
        UserInfo userInfo = selectByUserId(userId);
		if (null == userInfo) {
			throw new BusinessException(ResponseCodeEnum.CODE_404);
		}
		UserInfoVO userInfoVO = CopyTools.copy(userInfo, UserInfoVO.class);

		Long focusCount = userFocusMapper.selectFocusCount(userId);
		Long fanCount = userFocusMapper.selectFansCount(userId);
		userInfoVO.setFocusCount(focusCount);
		userInfoVO.setFansCount(fanCount);
		if (currentUserId == null) {
			userInfoVO.setHavaFocus(false);
		} else {
			UserFocus dbUserFocus = userFocusMapper.selectByUserIdAndFocusUserId(currentUserId, userId);
			if (dbUserFocus != null) {
				userInfoVO.setHavaFocus(true);
			} else {
				userInfoVO.setHavaFocus(false);
			}
		}
		//TODO 点赞数，播放数
		return userInfoVO;
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateUserInfo(UserInfo userInfo, TokenUserInfoDto tokenUserInfoDto) throws BusinessException {
		UserInfo dbInfo = userInfoMapper.selectByUserId(userInfo.getUserId());
		if (!dbInfo.getNickName().equals(userInfo.getNickName()) && dbInfo.getCurrentCoinCount() < Constants.UPDATE_NICK_NAME_COIN) {
			throw new BusinessException("硬币不足，无法修改昵称");
		}
		if (!dbInfo.getNickName().equals(userInfo.getNickName())) {
			Long count = this.userInfoMapper.updateCoinCount(userInfo.getUserId(), -Constants.UPDATE_NICK_NAME_COIN);
			if (count == 0L) {
				throw new BusinessException("硬币不足，无法修改昵称");
			}
		}

		userInfoMapper.updateByUserId(userInfo, userInfo.getUserId());

		Boolean updateTokenUserInfo = false;
		if (!userInfo.getAvatar().equals(tokenUserInfoDto.getAvatar())) {
			tokenUserInfoDto.setAvatar(userInfo.getAvatar());
			updateTokenUserInfo = true;
		}
		if (!tokenUserInfoDto.getNickName().equals(userInfo.getNickName())) {
			tokenUserInfoDto.setNickName(userInfo.getNickName());
			updateTokenUserInfo = true;
		}
		if (updateTokenUserInfo) {
			redisComponent.updateTokenUserInfo(tokenUserInfoDto);
		}
	}

	@Override
	public UserCountInfoDto getUserCountInfo(String userId) {
		UserInfo userInfo = userInfoMapper.selectByUserId(userId);
		Integer fansCount = userFocusMapper.selectFansCount(userId).intValue();
		Integer focusCount = userFocusMapper.selectFocusCount(userId).intValue();
		UserCountInfoDto userCountInfoDto = new UserCountInfoDto();
		userCountInfoDto.setFocusCount(focusCount);
		userCountInfoDto.setFansCount(fansCount);
		userCountInfoDto.setCurrentCoinCount(userInfo.getCurrentCoinCount());
		return userCountInfoDto;
	}
}