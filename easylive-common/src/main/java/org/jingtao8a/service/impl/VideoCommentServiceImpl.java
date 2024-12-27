package org.jingtao8a.service.impl;

import org.jingtao8a.constants.Constants;
import org.jingtao8a.entity.po.UserInfo;
import org.jingtao8a.entity.po.VideoComment;
import org.jingtao8a.entity.po.VideoInfo;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.UserInfoQuery;
import org.jingtao8a.entity.query.VideoCommentQuery;
import org.jingtao8a.entity.query.VideoInfoQuery;
import org.jingtao8a.enums.CommentTopTypeEnum;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.enums.ResponseCodeEnum;
import org.jingtao8a.enums.UserActionTypeEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.mapper.UserInfoMapper;
import org.jingtao8a.mapper.VideoCommentMapper;
import org.jingtao8a.mapper.VideoInfoMapper;
import org.jingtao8a.service.VideoCommentService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
@Description:VideoCommentService
@Date:2024-12-17
*/
@Service("videoCommentService")
public class VideoCommentServiceImpl implements VideoCommentService {

	@Resource
	private VideoCommentMapper<VideoComment, VideoCommentQuery> videoCommentMapper;

	@Resource
	private VideoInfoMapper<VideoInfo, VideoInfoQuery> videoInfoMapper;

	@Resource
	private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<VideoComment> findListByParam(VideoCommentQuery query) {
		if (query.getLoadChildren() != null && query.getLoadChildren()) {
			return this.videoCommentMapper.selectListWithChildren(query);
		}
		return this.videoCommentMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(VideoCommentQuery query) {
		return this.videoCommentMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<VideoComment> findListByPage(VideoCommentQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<VideoComment> userInfoList = findListByParam(query);
		PaginationResultVO<VideoComment> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(VideoComment bean) {
		return videoCommentMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(VideoComment bean) {
		return videoCommentMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<VideoComment> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoCommentMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<VideoComment> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoCommentMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据CommentId查询
	*/
	@Override
	public VideoComment selectByCommentId(Integer commentId) {
		return videoCommentMapper.selectByCommentId(commentId);
	}

	/**
	 * 根据CommentId更新
	*/
	@Override
	public Long updateByCommentId(VideoComment bean, Integer commentId) {
		return videoCommentMapper.updateByCommentId(bean, commentId);
	}

	/**
	 * 根据CommentId删除
	*/
	@Override
	public Long deleteByCommentId(Integer commentId) {
		return videoCommentMapper.deleteByCommentId(commentId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void postComment(VideoComment videoComment, Integer replyCommentId) throws BusinessException {
		VideoInfo videoInfo = videoInfoMapper.selectByVideoId(videoComment.getVideoId());
		if (videoInfo == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		if (videoInfo.getInteraction() != null && videoInfo.getInteraction().contains(Constants.ONE.toString())) {
			throw new BusinessException("UP主已经关闭评论区");
		}
		if (replyCommentId != null) {
			VideoComment replyComment = videoCommentMapper.selectByCommentId(replyCommentId);
			if (replyComment == null) {
				throw new BusinessException(ResponseCodeEnum.CODE_600);
			}
			if (replyComment.getPCommentId() == 0) {//回复一级评论
				videoComment.setPCommentId(replyComment.getCommentId());
			} else {//回复二级评论
				videoComment.setPCommentId(replyComment.getPCommentId());
				videoComment.setReplyUserId(replyComment.getUserId());
			}
			UserInfo replyUserInfo = userInfoMapper.selectByUserId(replyComment.getUserId());
			videoComment.setReplyNickName(replyUserInfo.getNickName());
		} else {
			videoComment.setPCommentId(0);
		}
		videoComment.setPostTime(new Date());
		videoComment.setVideoUserId(videoInfo.getUserId());
		videoCommentMapper.insert(videoComment);
		if (videoComment.getPCommentId() == 0) {
			videoInfoMapper.updateCountInfo(videoComment.getVideoId(), UserActionTypeEnum.VIDEO_COMMENT.getField(), Constants.ONE);
		}
	}

    @Override
	@Transactional(rollbackFor = Exception.class)
    public void topComment(Integer commentId, String userId) throws BusinessException {
		cancelTopComment(commentId, userId);
		VideoComment videoComment = new VideoComment();
		videoComment.setTopType(CommentTopTypeEnum.TOP.getType());
		videoCommentMapper.updateByCommentId(videoComment, commentId);
	}

	@Override
	public void cancelTopComment(Integer commentId, String userId) throws BusinessException {
		VideoComment dbVideoComment = videoCommentMapper.selectByCommentId(commentId);
		if (dbVideoComment == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		VideoInfo videoInfo = videoInfoMapper.selectByVideoId(dbVideoComment.getVideoId());
		if (videoInfo == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		if (!videoInfo.getUserId().equals(userId)) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		VideoComment videoComment = new VideoComment();
		videoComment.setTopType(CommentTopTypeEnum.NO_TOP.getType());

		VideoCommentQuery videoCommentQuery = new VideoCommentQuery();
		videoCommentQuery.setVideoId(dbVideoComment.getVideoId());
		videoCommentQuery.setTopType(CommentTopTypeEnum.TOP.getType());
		videoCommentMapper.updateByParam(videoComment, videoCommentQuery);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteComment(Integer commentId, String userId) throws BusinessException {
		VideoComment dbVideoComment = videoCommentMapper.selectByCommentId(commentId);
		if (dbVideoComment == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		VideoInfo videoInfo = videoInfoMapper.selectByVideoId(dbVideoComment.getVideoId());
		if (videoInfo == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		if (userId != null && !videoInfo.getUserId().equals(userId) && !dbVideoComment.getUserId().equals(userId)) {//只有video的user和comment的user可以删除该评论
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		videoCommentMapper.deleteByCommentId(commentId);
		if (dbVideoComment.getPCommentId() == 0) {
			videoInfoMapper.updateCountInfo(videoInfo.getVideoId(), UserActionTypeEnum.VIDEO_COMMENT.getField(), -1);
			//删除二级评论
			VideoCommentQuery videoCommentQuery = new VideoCommentQuery();
			videoCommentQuery.setPCommentId(dbVideoComment.getCommentId());
			videoCommentMapper.deleteByParam(videoCommentQuery);
		}
		//TODO 删除相关用户行为user_action
	}

}