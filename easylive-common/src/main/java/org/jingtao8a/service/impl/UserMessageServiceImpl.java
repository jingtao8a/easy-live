package org.jingtao8a.service.impl;

import org.jingtao8a.entity.po.UserMessage;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.UserMessageQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.mapper.UserMessageMapper;
import org.jingtao8a.service.UserMessageService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
@Description:UserMessageService
@Date:2024-12-25
*/
@Service("userMessageService")
public class UserMessageServiceImpl implements UserMessageService {

	@Resource
	private UserMessageMapper<UserMessage, UserMessageQuery> userMessageMapper;
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

}