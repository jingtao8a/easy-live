package org.jingtao8a.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.jingtao8a.dto.UserMessageExtendDto;
import org.jingtao8a.entity.po.UserMessage;
import org.jingtao8a.entity.po.VideoComment;
import org.jingtao8a.entity.po.VideoInfo;
import org.jingtao8a.entity.po.VideoInfoPost;
import org.jingtao8a.entity.query.*;
import org.jingtao8a.enums.MessageReadTypeEnum;
import org.jingtao8a.enums.MessageTypeEnum;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.mapper.UserMessageMapper;
import org.jingtao8a.mapper.VideoCommentMapper;
import org.jingtao8a.mapper.VideoInfoMapper;
import org.jingtao8a.mapper.VideoInfoPostMapper;
import org.jingtao8a.service.UserMessageService;
import org.jingtao8a.utils.JsonUtils;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
/**
@Description:UserMessageService
@Date:2024-12-25
*/
@Service("userMessageService")
@Slf4j
public class UserMessageServiceImpl implements UserMessageService {

	@Resource
	private UserMessageMapper<UserMessage, UserMessageQuery> userMessageMapper;

	@Resource
	private VideoInfoMapper<VideoInfo, VideoInfoQuery> videoInfoMapper;

	@Resource
	private VideoCommentMapper<VideoComment, VideoCommentQuery> videoCommentMapper;

    @Resource
    private VideoInfoPostMapper<VideoInfoPost, VideoInfoPostQuery> videoInfoPostMapper;

	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<UserMessage> findListByParam(UserMessageQuery query) {
		return this.userMessageMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(UserMessageQuery query) {
		return this.userMessageMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<UserMessage> findListByPage(UserMessageQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<UserMessage> userInfoList = findListByParam(query);
		PaginationResultVO<UserMessage> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(UserMessage bean) {
		return userMessageMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(UserMessage bean) {
		return userMessageMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<UserMessage> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return userMessageMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<UserMessage> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return userMessageMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据MessageId查询
	*/
	@Override
	public UserMessage selectByMessageId(Integer messageId) {
		return userMessageMapper.selectByMessageId(messageId);
	}

	/**
	 * 根据MessageId更新
	*/
	@Override
	public Long updateByMessageId(UserMessage bean, Integer messageId) {
		return userMessageMapper.updateByMessageId(bean, messageId);
	}

	/**
	 * 根据MessageId删除
	*/
	@Override
	public Long deleteByMessageId(Integer messageId) {
		return userMessageMapper.deleteByMessageId(messageId);
	}

    @Override
	@Async
    public void saveUserMessage(String videoId, String content, Integer replyCommentId, String reason, MessageTypeEnum messageTypeEnum, String sendUserId) {
		UserMessage userMessage = new UserMessage();
		String userId = null;
		VideoInfoPost videoInfoPost = videoInfoPostMapper.selectByVideoId(videoId);
		if (videoInfoPost == null) {
			return;
		}
		userId = videoInfoPost.getUserId();
		UserMessageExtendDto userMessageExtendDto = new UserMessageExtendDto();
		log.info("saveUserMessage");
		if (messageTypeEnum == MessageTypeEnum.LIKE || messageTypeEnum == MessageTypeEnum.COLLECTION) {
			//doAction(视频收藏、点赞 已经记录的，不再记录)
			UserMessageQuery userMessageQuery = new UserMessageQuery();
			userMessageQuery.setVideoId(videoId);
			userMessageQuery.setUserId(userId);
			userMessageQuery.setSendUserId(sendUserId);
			userMessageQuery.setMessageType(messageTypeEnum.getType());
			Integer count = userMessageMapper.selectCount(userMessageQuery).intValue();
			if (count > 0) {
				return;
			}
		} else if (messageTypeEnum == MessageTypeEnum.COMMENT) {
			//postComment
			userMessageExtendDto.setMessageContent(content);
			if (replyCommentId != null) {
				VideoComment videoComment = videoCommentMapper.selectByCommentId(replyCommentId);
				if (videoComment != null) {
					userId = videoComment.getUserId();
					userMessageExtendDto.setMessageContentReply(videoComment.getContent());
				}
			}
			if (sendUserId.equals(userId)) {//回复自己的评论不记录
				return;
			}
			userMessage.setExtendJson(JsonUtils.convertObj2Json(userMessageExtendDto));
		} else if (messageTypeEnum == MessageTypeEnum.SYS) {
			//auditVideo
			log.info("audit......");
			userMessageExtendDto.setMessageContent(reason);
			userMessageExtendDto.setAuditStatus(videoInfoPost.getStatus());
			userMessage.setExtendJson(JsonUtils.convertObj2Json(userMessageExtendDto));
		} else {
			return;
		}
		userMessage.setUserId(userId);
		userMessage.setVideoId(videoId);
		userMessage.setMessageType(messageTypeEnum.getType());
		userMessage.setSendUserId(sendUserId);
		userMessage.setReadType(MessageReadTypeEnum.NO_READ.getType());
		userMessage.setCreateTime(new Date());
		userMessageMapper.insert(userMessage);
    }
}