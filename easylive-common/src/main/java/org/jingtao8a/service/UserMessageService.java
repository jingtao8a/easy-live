package org.jingtao8a.service;

import org.jingtao8a.entity.po.UserMessage;
import org.jingtao8a.entity.query.UserMessageQuery;
import org.jingtao8a.enums.MessageTypeEnum;
import org.jingtao8a.vo.PaginationResultVO;

import java.util.List;

/**
@Description:用户消息表Service
@Date:2024-12-25
*/
public interface UserMessageService {

	/**
	 * 根据条件查询列表
	*/
	List<UserMessage> findListByParam(UserMessageQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(UserMessageQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<UserMessage> findListByPage(UserMessageQuery param);

	/**
	 * 新增
	*/
	Long add(UserMessage bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(UserMessage bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<UserMessage> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<UserMessage> listBean);

	/**
	 * 根据MessageId查询
	*/
	UserMessage selectByMessageId(Integer messageId);

	/**
	 * 根据MessageId更新
	*/
	Long updateByMessageId(UserMessage bean, Integer messageId);

	/**
	 * 根据MessageId删除
	*/
	Long deleteByMessageId(Integer messageId);

    void saveUserMessage(String videoId, String content, Integer replyCommentId, String reason, MessageTypeEnum messageTypeEnum, String sendUserId);
}