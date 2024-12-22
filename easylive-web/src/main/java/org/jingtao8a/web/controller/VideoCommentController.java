package org.jingtao8a.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.constants.Constants;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.entity.po.UserAction;
import org.jingtao8a.entity.po.VideoComment;
import org.jingtao8a.entity.po.VideoInfo;
import org.jingtao8a.entity.query.UserActionQuery;
import org.jingtao8a.entity.query.VideoCommentQuery;
import org.jingtao8a.enums.CommentTopTypeEnum;
import org.jingtao8a.enums.ResponseCodeEnum;
import org.jingtao8a.enums.UserActionTypeEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.service.UserActionService;
import org.jingtao8a.service.VideoCommentService;
import org.jingtao8a.service.VideoInfoService;
import org.jingtao8a.vo.PaginationResultVO;
import org.jingtao8a.vo.ResponseVO;
import org.jingtao8a.vo.VideoCommentResultVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequestMapping("/comment")
public class VideoCommentController extends ABaseController{
    @Resource
    private VideoCommentService videoCommentService;

    @Resource
    private VideoInfoService videoInfoService;

    @Resource
    private UserActionService userActionService;

    @RequestMapping("/postComment")
    public ResponseVO postComment(@NotEmpty String videoId,
                                  @NotEmpty @Size(max=500) String content,
                                  Integer replyCommentId,
                                  @Size(max=50) String imgPath) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入无法评论");
        }
        VideoComment videoComment = new VideoComment();
        videoComment.setVideoId(videoId);
        videoComment.setContent(content);
        videoComment.setImgPath(imgPath);
        videoComment.setUserId(tokenUserInfoDto.getUserId());
        videoComment.setNickName(tokenUserInfoDto.getNickName());
        videoComment.setAvatar(tokenUserInfoDto.getAvatar());
        videoCommentService.postComment(videoComment, replyCommentId);
        return getSuccessResponseVO(videoComment);
    }

    @RequestMapping("/loadComment")
    public ResponseVO loadComment(@NotEmpty String videoId, String pageNo, Integer orderType) throws BusinessException {
        VideoInfo videoInfo = videoInfoService.selectByVideoId(videoId);
        if (videoInfo == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (videoInfo.getInteraction() != null && videoInfo.getInteraction().contains(Constants.ONE.toString())) {
            return getSuccessResponseVO(new VideoCommentResultVO());
        }
        VideoCommentQuery videoCommentQuery = new VideoCommentQuery();
        videoCommentQuery.setVideoId(videoId);
        try {
            videoCommentQuery.setPageNo(Long.valueOf(pageNo));
        } catch (Exception e) {
            videoCommentQuery.setPageNo(0L);
        }
        videoCommentQuery.setPCommentId(0);
        String orderBy = (orderType == null || orderType == 0) ? "like_count desc, comment_id desc" : "comment_id desc";
        videoCommentQuery.setOrderBy(orderBy);
        videoCommentQuery.setTopType(CommentTopTypeEnum.NO_TOP.getType());
        videoCommentQuery.setLoadChildren(true);
        PaginationResultVO<VideoComment> commentPaginationResultVO = videoCommentService.findListByPage(videoCommentQuery);

        List<VideoComment> topCommentList = topComment(videoId);//TODO 置顶评论，可能有BUG
        if (!topCommentList.isEmpty()) {
            List<VideoComment> commentList = commentPaginationResultVO.getList();
            topCommentList.addAll(commentList);
            commentPaginationResultVO.setList(topCommentList);
        }
        //获取用户行为 (评论喜欢、讨厌)
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        List<UserAction> userActions = new ArrayList<>();
        if (tokenUserInfoDto != null) {//登入状态
            UserActionQuery userActionQuery = new UserActionQuery();
            userActionQuery.setUserId(tokenUserInfoDto.getUserId());
            userActionQuery.setVideoId(videoId);
            userActionQuery.setActionTypeArray(new Integer[]{UserActionTypeEnum.COMMENT_LIKE.getType(), UserActionTypeEnum.COMMENT_HATE.getType()});
            userActions = userActionService.findListByParam(userActionQuery);
        }
        VideoCommentResultVO videoCommentResultVO = new VideoCommentResultVO();
        videoCommentResultVO.setCommentData(commentPaginationResultVO);
        videoCommentResultVO.setUserActionList(userActions);
        return getSuccessResponseVO(videoCommentResultVO);
    }

    private List<VideoComment> topComment(String videoId) {
        VideoCommentQuery videoCommentQuery = new VideoCommentQuery();
        videoCommentQuery.setVideoId(videoId);
        videoCommentQuery.setTopType(CommentTopTypeEnum.TOP.getType());
        videoCommentQuery.setLoadChildren(true);
        List<VideoComment> videoComments = videoCommentService.findListByParam(videoCommentQuery);
        return videoComments;
    }


    @RequestMapping("/topComment")
    public ResponseVO topComment(@NotNull Integer commentId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        videoCommentService.topComment(commentId, tokenUserInfoDto.getUserId());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/cancelTopComment")
    public ResponseVO cancelTopComment(@NotNull Integer commentId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        videoCommentService.cancelTopComment(commentId, tokenUserInfoDto.getUserId());
        return getSuccessResponseVO(null);
    }


    @RequestMapping("/userDelComment")
    public ResponseVO userDelComment(@NotNull Integer commentId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        videoCommentService.deleteComment(commentId, tokenUserInfoDto.getUserId());
        return getSuccessResponseVO(null);
    }
}
