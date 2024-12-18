package org.jingtao8a.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.entity.po.VideoComment;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.service.VideoCommentService;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@RestController
@Validated
@Slf4j
@RequestMapping("/comment")
public class VideoCommentController extends ABaseController{
    @Resource
    private VideoCommentService videoCommentService;

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
}
