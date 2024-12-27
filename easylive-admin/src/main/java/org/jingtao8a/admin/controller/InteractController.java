package org.jingtao8a.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.entity.po.VideoComment;
import org.jingtao8a.entity.po.VideoDanmu;
import org.jingtao8a.entity.query.VideoCommentQuery;
import org.jingtao8a.entity.query.VideoDanmuQuery;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.service.VideoCommentService;
import org.jingtao8a.service.VideoDanmuService;
import org.jingtao8a.vo.PaginationResultVO;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@RestController
@Validated
@Slf4j
@RequestMapping("/interact")
public class InteractController extends ABaseController {
    @Resource
    private VideoCommentService videoCommentService;
    @Resource
    private VideoDanmuService videoDanmuService;

    @RequestMapping("/loadComment")
    public ResponseVO loadComment(Integer pageNo, Integer pageSize, String videoNameFuzzy) {
        VideoCommentQuery videoCommentQuery = new VideoCommentQuery();
        videoCommentQuery.setOrderBy("comment_id desc");
        videoCommentQuery.setPageNo(pageNo == null ? 0L : pageNo.longValue());
        videoCommentQuery.setPageSize(pageSize == null ? 20L : pageSize.longValue());
        videoCommentQuery.setQueryVideoInfo(true);
        videoCommentQuery.setVideoNameFuzzy(videoNameFuzzy);
        PaginationResultVO<VideoComment> resultVO = videoCommentService.findListByPage(videoCommentQuery);
        return getSuccessResponseVO(resultVO);
    }

    @RequestMapping("/delComment")
    public ResponseVO delComment(@NotNull Integer commentId) throws BusinessException {
        videoCommentService.deleteComment(commentId, null);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadDanmu")
    public ResponseVO loadDanmu(Integer pageNo, Integer pageSize, String videoNameFuzzy) {
        VideoDanmuQuery videoDanmuQuery = new VideoDanmuQuery();
        videoDanmuQuery.setOrderBy("danmu_id desc");
        videoDanmuQuery.setPageNo(pageNo == null ? 0L : pageNo.longValue());
        videoDanmuQuery.setPageSize(pageSize == null ? 20L : pageSize.longValue());
        videoDanmuQuery.setQueryVideoInfo(true);
        videoDanmuQuery.setVideoNameFuzzy(videoNameFuzzy);
        PaginationResultVO<VideoDanmu> resultVO = videoDanmuService.findListByPage(videoDanmuQuery);
        return getSuccessResponseVO(resultVO);
    }

    @RequestMapping("/delDanmu")
    public ResponseVO delDanmu(@NotNull Integer danmuId) throws BusinessException {
        videoDanmuService.deleteDanmu(danmuId, null);
        return getSuccessResponseVO(null);
    }
}
