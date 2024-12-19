package org.jingtao8a.service.impl;

import org.jingtao8a.constants.Constants;
import org.jingtao8a.entity.po.UserAction;
import org.jingtao8a.entity.po.UserInfo;
import org.jingtao8a.entity.po.VideoComment;
import org.jingtao8a.entity.po.VideoInfo;
import org.jingtao8a.entity.query.*;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.enums.ResponseCodeEnum;
import org.jingtao8a.enums.UserActionTypeEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.mapper.UserActionMapper;
import org.jingtao8a.mapper.UserInfoMapper;
import org.jingtao8a.mapper.VideoCommentMapper;
import org.jingtao8a.mapper.VideoInfoMapper;
import org.jingtao8a.service.UserActionService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
@Description:UserActionService
@Date:2024-12-17
*/
@Service("userActionService")
public class UserActionServiceImpl implements UserActionService {

	@Resource
	private UserActionMapper<UserAction, UserActionQuery> userActionMapper;

	@Resource
	private VideoInfoMapper<VideoInfo, VideoInfoQuery> videoInfoMapper;

	@Resource
	private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

	@Resource
	private VideoCommentMapper<VideoComment, VideoCommentQuery> videoCommentMapper;

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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveAction(UserAction userAction) throws BusinessException {
		VideoInfo videoInfo = videoInfoMapper.selectByVideoId(userAction.getVideoId());
		if (videoInfo == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		userAction.setVideoUserId(videoInfo.getUserId());
		userAction.setActionTime(new Date());

		UserAction dbAction = userActionMapper.selectByVideoIdAndCommentIdAndActionTypeAndUserId(userAction.getVideoId(), userAction.getCommentId(), userAction.getActionType(), userAction.getUserId());
		UserActionTypeEnum userActionTypeEnum = UserActionTypeEnum.getEnum(userAction.getActionType());
		int changeCount = Constants.ZERO, opposeChangeCount = Constants.ZERO;

		switch (userActionTypeEnum) {
			case VIDEO_LIKE://自己可以给自己 点赞、收藏
			case VIDEO_COLLECT:
				if (dbAction != null) {//重复操作表示取消 视频喜欢、视频收藏
					userActionMapper.deleteByActionId(dbAction.getActionId());
					changeCount = -Constants.ONE;
				} else {
					userActionMapper.insert(userAction);
					changeCount = Constants.ONE;
				}
				videoInfoMapper.updateCountInfo(userAction.getVideoId(), userActionTypeEnum.getField(), changeCount);
				//TODO 更新es
				break;
			case VIDEO_COIN://自己不可以给自己 投币
				if (userAction.getUserId().equals(videoInfo.getUserId())) {
					throw new BusinessException("UP主不能给自己投币");
				}
				if (dbAction != null) {
					throw new BusinessException("对本稿件的投币枚数已用完");
				}
				Long count = userInfoMapper.updateCoinCount(userAction.getUserId(), -userAction.getActionCount());//减少投币者的硬币数量
				if (count == 0L) {
					throw new BusinessException("硬币数量不够");
				}
				count = userInfoMapper.updateCoinCount(videoInfo.getUserId(), userAction.getActionCount());//增加视频UP主的硬币数量
				if (count == 0L) {
					throw new BusinessException("投币失败");
				}
				userActionMapper.insert(userAction);//添加UserAction
				videoInfoMapper.updateCountInfo(userAction.getVideoId(), userActionTypeEnum.getField(), userAction.getActionCount());//更新视频投币数量
				break;
			case COMMENT_HATE:
			case COMMENT_LIKE:
				UserActionTypeEnum opposeTypeEnum = UserActionTypeEnum.COMMENT_LIKE == userActionTypeEnum ? UserActionTypeEnum.COMMENT_HATE : UserActionTypeEnum.COMMENT_LIKE;
				if (dbAction != null) {//重复操作表示取消
					userActionMapper.deleteByActionId(dbAction.getActionId());
					changeCount = -Constants.ONE;
				} else {
					UserAction opposeUserAction = userActionMapper.selectByVideoIdAndCommentIdAndActionTypeAndUserId(userAction.getVideoId(), userAction.getCommentId(), opposeTypeEnum.getType(), userAction.getUserId());
					if (opposeUserAction != null) {
						userActionMapper.deleteByActionId(opposeUserAction.getActionId());//将对立面评论action删除
						opposeChangeCount = -Constants.ONE;

					}
					userActionMapper.insert(userAction);
					changeCount = Constants.ONE;
				}
				videoCommentMapper.updateCountInfo(userAction.getCommentId(), userActionTypeEnum.getField(), changeCount, opposeTypeEnum.getField(), opposeChangeCount);
				break;
		}
	}

}