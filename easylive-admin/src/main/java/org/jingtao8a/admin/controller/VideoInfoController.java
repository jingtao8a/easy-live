package org.jingtao8a.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.annotation.RecordMessage;
import org.jingtao8a.entity.po.VideoInfoFilePost;
import org.jingtao8a.entity.query.VideoInfoFilePostQuery;
import org.jingtao8a.entity.query.VideoInfoPostQuery;
import org.jingtao8a.enums.MessageTypeEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.service.VideoInfoFilePostService;
import org.jingtao8a.service.VideoInfoFileService;
import org.jingtao8a.service.VideoInfoPostService;
import org.jingtao8a.service.VideoInfoService;
import org.jingtao8a.vo.PaginationResultVO;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@RestController
@Validated
@Slf4j
@RequestMapping("/videoInfo")
public class VideoInfoController extends ABaseController {
    @Resource
    private VideoInfoPostService videoInfoPostService;
    @Resource
    private VideoInfoFilePostService videoInfoFilePostService;
    @Resource
    private VideoInfoService videoInfoService;
    @Resource
    private VideoInfoFileService videoInfoFileService;

    @RequestMapping("/loadVideoList")
    public ResponseVO loadVideoList(VideoInfoPostQuery videoInfoPostQuery) {
        videoInfoPostQuery.setOrderBy("v.last_update_time desc");
        videoInfoPostQuery.setQueryCountInfo(true);//关联查询VideoInfo
        videoInfoPostQuery.setQueryUserInfo(true);//关联查询UserInfo
        PaginationResultVO resultVO = videoInfoPostService.findListByPage(videoInfoPostQuery);
        return getSuccessResponseVO(resultVO);
    }

    @RequestMapping("/auditVideo")
    @RecordMessage(messageType = MessageTypeEnum.SYS)
    public ResponseVO auditVideo(@NotEmpty String videoId, @NotNull Integer status, String reason) throws BusinessException {
        videoInfoPostService.auditVideo(videoId, status, reason);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/recommendVideo")
    public ResponseVO recommendVideo(@NotEmpty String videoId) throws BusinessException {
        videoInfoService.recommendVideo(videoId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/deleteVideo")
    public ResponseVO deleteVideo(@NotEmpty String videoId) throws BusinessException {
        videoInfoPostService.deleteVideo(videoId, null);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadVideoPList")
    public ResponseVO loadVideoPList(@NotEmpty String videoId) {
        VideoInfoFilePostQuery videoInfoFilePostQuery = new VideoInfoFilePostQuery();
        videoInfoFilePostQuery.setVideoId(videoId);
        videoInfoFilePostQuery.setOrderBy("file_index asc");
        List<VideoInfoFilePost> videoInfoFilePostList = videoInfoFilePostService.findListByParam(videoInfoFilePostQuery);
        return getSuccessResponseVO(videoInfoFilePostList);
    }
}
