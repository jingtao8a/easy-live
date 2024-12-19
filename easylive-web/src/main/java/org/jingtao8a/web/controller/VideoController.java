package org.jingtao8a.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.component.RedisComponent;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.entity.po.UserAction;
import org.jingtao8a.entity.po.VideoInfo;
import org.jingtao8a.entity.po.VideoInfoFile;
import org.jingtao8a.entity.query.UserActionQuery;
import org.jingtao8a.entity.query.VideoInfoFileQuery;
import org.jingtao8a.entity.query.VideoInfoQuery;
import org.jingtao8a.enums.ResponseCodeEnum;
import org.jingtao8a.enums.UserActionTypeEnum;
import org.jingtao8a.enums.VideoRecommendTypeEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.service.UserActionService;
import org.jingtao8a.service.VideoInfoFileService;
import org.jingtao8a.service.VideoInfoService;
import org.jingtao8a.vo.PaginationResultVO;
import org.jingtao8a.vo.ResponseVO;
import org.jingtao8a.vo.VideoInfoResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;

@RestController
@Validated
@Slf4j
@RequestMapping("/video")
public class VideoController extends ABaseController {
    @Resource
    private VideoInfoService videoInfoService;

    @Resource
    private VideoInfoFileService videoInfoFileService;

    @Resource
    private UserActionService userActionService;
    @Autowired
    private RedisComponent redisComponent;

    @RequestMapping("/loadRecommendVideo")
    public ResponseVO loadRecommendVideo() {
        VideoInfoQuery query = new VideoInfoQuery();
        query.setQueryUserInfo(true);
        query.setOrderBy("v.create_time desc");
        query.setRecommendType(VideoRecommendTypeEnum.RECOMMEND.getType());
        List<VideoInfo> recommendVideoList = videoInfoService.findListByParam(query);
        return getSuccessResponseVO(recommendVideoList);
    }

    @RequestMapping("/loadVideo")
    public ResponseVO loadVideo(Integer pCategoryId, Integer categoryId, Integer pageNo) {
        VideoInfoQuery query = new VideoInfoQuery();
        query.setPCategoryId(pCategoryId);
        query.setCategoryId(categoryId);
        if (pageNo != null) {
            query.setPageNo(Long.valueOf(pageNo));
        }
        query.setQueryUserInfo(true);
        query.setOrderBy("v.create_time desc");
        query.setRecommendType(VideoRecommendTypeEnum.NO_RECOMMEND.getType());
        PaginationResultVO<VideoInfo> recommendVideoList = videoInfoService.findListByPage(query);
        return getSuccessResponseVO(recommendVideoList);
    }

    @RequestMapping("/getVideoInfo")
    public ResponseVO getVideoInfo(@NotEmpty String videoId) throws BusinessException {
        VideoInfo videoInfo = videoInfoService.selectByVideoId(videoId);
        if (videoInfo == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        //获取用户行为 (视频点赞、投币、收藏)
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        List<UserAction> userActions = new ArrayList<>();
        if (tokenUserInfoDto != null) {//登入状态
            UserActionQuery userActionQuery = new UserActionQuery();
            userActionQuery.setUserId(tokenUserInfoDto.getUserId());
            userActionQuery.setVideoId(videoId);
            userActionQuery.setActionTypeArray(new Integer[]{UserActionTypeEnum.VIDEO_LIKE.getType(), UserActionTypeEnum.VIDEO_COIN.getType(), UserActionTypeEnum.VIDEO_COLLECT.getType()});
            userActions = userActionService.findListByParam(userActionQuery);
        }
        VideoInfoResultVO resultVO = new VideoInfoResultVO();
        resultVO.setVideoInfo(videoInfo);
        resultVO.setUserActionList(userActions);
        return getSuccessResponseVO(resultVO);
    }

    @RequestMapping("/loadVideoPList")
    public ResponseVO loadVideoPList(@NotEmpty String videoId) throws BusinessException {
        VideoInfoFileQuery query = new VideoInfoFileQuery();
        query.setVideoId(videoId);
        query.setOrderBy("file_index asc");
        List<VideoInfoFile> fileList = videoInfoFileService.findListByParam(query);
        return getSuccessResponseVO(fileList);
    }

    @RequestMapping("/reportVideoPlayOnline")
    public ResponseVO reportVideoPlayOnline(@NotEmpty String fileId, @NotEmpty String deviceId) throws BusinessException {
        return getSuccessResponseVO(redisComponent.reportVideoPlayOnline(fileId, deviceId));
    }
}
