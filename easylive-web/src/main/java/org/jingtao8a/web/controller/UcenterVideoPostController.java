package org.jingtao8a.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.entity.po.VideoInfoFilePost;
import org.jingtao8a.entity.po.VideoInfoPost;
import org.jingtao8a.entity.query.VideoInfoFilePostQuery;
import org.jingtao8a.entity.query.VideoInfoPostQuery;
import org.jingtao8a.enums.ResponseCodeEnum;
import org.jingtao8a.enums.VideoStatusEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.service.VideoInfoFilePostService;
import org.jingtao8a.service.VideoInfoFileService;
import org.jingtao8a.service.VideoInfoPostService;
import org.jingtao8a.service.VideoInfoService;
import org.jingtao8a.utils.JsonUtils;
import org.jingtao8a.vo.PaginationResultVO;
import org.jingtao8a.vo.ResponseVO;
import org.jingtao8a.vo.VideoPostEditInfoVO;
import org.jingtao8a.vo.VideoStatusCountInfoVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@RestController
@RequestMapping("/ucenter")
@Validated
@Slf4j
public class UcenterVideoPostController extends ABaseController{
    @Resource
    private VideoInfoPostService videoInfoPostService;
    @Resource
    private VideoInfoFilePostService videoInfoFilePostService;
    @Resource
    private VideoInfoService videoInfoService;
    @Resource
    private VideoInfoFileService videoInfoFileService;

    @RequestMapping("/postVideo")
    public ResponseVO postVideo(String videoId,
                                @NotEmpty String videoCover,
                                @NotEmpty @Size(max=100) String videoName,
                                @NotNull Integer pCategoryId,
                                Integer categoryId,
                                @NotNull Integer postType,
                                @NotEmpty @Size(max=300) String tags,
                                @Size(max=2000) String introduction,
                                @Size(max = 3) String interaction,
                                @NotEmpty String uploadFileList) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        List<VideoInfoFilePost> videoInfoFilePostList = JsonUtils.convertJson2List(uploadFileList, VideoInfoFilePost.class);
        //只有create_time last_update_time status origin_info duration 未初始化
        VideoInfoPost videoInfoPost = new VideoInfoPost();
        videoInfoPost.setVideoId(videoId);
        videoInfoPost.setVideoCover(videoCover);
        videoInfoPost.setVideoName(videoName);
        videoInfoPost.setUserId(tokenUserInfoDto.getUserId());
        videoInfoPost.setPCategoryId(pCategoryId);
        videoInfoPost.setCategoryId(categoryId);
        videoInfoPost.setPostType(postType);
        videoInfoPost.setTags(tags);
        videoInfoPost.setIntroduction(introduction);
        videoInfoPost.setInteraction(interaction);

        videoInfoPostService.saveVideoInfoPost(videoInfoPost, videoInfoFilePostList);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadVideoList")
    public ResponseVO loadVideoList(Integer status, Long pageNo, String videoNameFuzzy) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        VideoInfoPostQuery videoInfoPostQuery = new VideoInfoPostQuery();
        videoInfoPostQuery.setUserId(tokenUserInfoDto.getUserId());
        videoInfoPostQuery.setPageNo(pageNo);
        videoInfoPostQuery.setOrderBy("v.create_time desc");
        videoInfoPostQuery.setVideoName(videoNameFuzzy);
        videoInfoPostQuery.setQueryCountInfo(true);
        if (status != null) {
            if (status == -1) {//进行中的video
                videoInfoPostQuery.setExcludeStatusArray(new Integer[]{VideoStatusEnum.STATUS3.getStatus(), VideoStatusEnum.STATUS4.getStatus()});
            } else {//指定status的video
                videoInfoPostQuery.setStatus(status);
            }
        }//else 全部video
        PaginationResultVO resultVO = videoInfoPostService.findListByPage(videoInfoPostQuery);
        return getSuccessResponseVO(resultVO);
    }

    @RequestMapping("/getVideoCountInfo")
    public ResponseVO getVideoCountInfo() throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        VideoInfoPostQuery videoInfoPostQuery = new VideoInfoPostQuery();
        videoInfoPostQuery.setUserId(tokenUserInfoDto.getUserId());
        videoInfoPostQuery.setStatus(VideoStatusEnum.STATUS3.getStatus());//审核通过的
        Long auditPassCount = videoInfoPostService.findCountByParam(videoInfoPostQuery);

        videoInfoPostQuery.setStatus(VideoStatusEnum.STATUS4.getStatus());//审核不通过的
        Long auditFailCount = videoInfoPostService.findCountByParam(videoInfoPostQuery);

        videoInfoPostQuery.setStatus(null);
        videoInfoPostQuery.setExcludeStatusArray(new Integer[]{VideoStatusEnum.STATUS3.getStatus(), VideoStatusEnum.STATUS4.getStatus()});
        Long inProgress = videoInfoPostService.findCountByParam(videoInfoPostQuery);

        VideoStatusCountInfoVO videoStatusCountInfoVO = new VideoStatusCountInfoVO();
        videoStatusCountInfoVO.setAuditPassCount(auditPassCount);
        videoStatusCountInfoVO.setAuditFailCount(auditFailCount);
        videoStatusCountInfoVO.setInProgress(inProgress);
        return getSuccessResponseVO(videoStatusCountInfoVO);
    }

    @RequestMapping("/getVideoByVideoId")
    public ResponseVO getVideoByVideoId(@NotEmpty String videoId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        VideoInfoPost videoInfoPost = videoInfoPostService.selectByVideoId(videoId);
        if (videoInfoPost == null || !videoInfoPost.getUserId().equals(tokenUserInfoDto.getUserId())) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        VideoInfoFilePostQuery videoInfoFilePostQuery = new VideoInfoFilePostQuery();
        videoInfoFilePostQuery.setVideoId(videoId);
        videoInfoFilePostQuery.setOrderBy("file_index asc");
        List<VideoInfoFilePost> videoInfoFilePostList = videoInfoFilePostService.findListByParam(videoInfoFilePostQuery);

        VideoPostEditInfoVO videoPostEditInfoVO = new VideoPostEditInfoVO();
        videoPostEditInfoVO.setVideoInfo(videoInfoPost);
        videoPostEditInfoVO.setVideoInfoFileList(videoInfoFilePostList);
        return getSuccessResponseVO(videoPostEditInfoVO);
    }
}
