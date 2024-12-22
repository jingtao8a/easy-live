package org.jingtao8a.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.entity.po.UserVideoSeries;
import org.jingtao8a.entity.po.UserVideoSeriesVideo;
import org.jingtao8a.entity.po.VideoInfo;
import org.jingtao8a.entity.query.UserVideoSeriesQuery;
import org.jingtao8a.entity.query.UserVideoSeriesVideoQuery;
import org.jingtao8a.entity.query.VideoInfoQuery;
import org.jingtao8a.enums.ResponseCodeEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.service.*;
import org.jingtao8a.vo.ResponseVO;
import org.jingtao8a.vo.UserVideoSeriesDetailVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Validated
@Slf4j
@RequestMapping("/uhome/series")
public class UHomeVideoSeriesController extends ABaseController {
    @Resource
    private VideoInfoService videoInfoService;

    @Resource
    private UserVideoSeriesService userVideoSeriesService;

    @Resource
    private UserVideoSeriesVideoService userVideoSeriesVideoService;

    @RequestMapping("/loadVideoSeries")
    public ResponseVO loadVideoSeries(@NotEmpty String userId) {
        List<UserVideoSeries> userVideoSeriesList = userVideoSeriesService.getUserAllSeries(userId);
        return getSuccessResponseVO(userVideoSeriesList);
    }

    @RequestMapping("/saveVideoSeries")
    public ResponseVO saveVideoSeries(Integer seriesId,
                                      @NotEmpty @Size(max=100) String seriesName,
                                      @Size(max=200) String seriesDescription,
                                      String videoIds) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        UserVideoSeries userVideoSeries = new UserVideoSeries();
        userVideoSeries.setSeriesId(seriesId);
        userVideoSeries.setSeriesName(seriesName);
        userVideoSeries.setSeriesDescription(seriesDescription);
        userVideoSeries.setUserId(tokenUserInfoDto.getUserId());
        userVideoSeriesService.saveUserVideoSeries(userVideoSeries, videoIds);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadAllVideo")
    public ResponseVO loadAllVideo(Integer seriesId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }

        VideoInfoQuery videoInfoQuery = new VideoInfoQuery();
        if (seriesId != null) {
            UserVideoSeriesVideoQuery videoSeriesVideoQuery = new UserVideoSeriesVideoQuery();
            videoSeriesVideoQuery.setSeriesId(seriesId);
            videoSeriesVideoQuery.setUserId(tokenUserInfoDto.getUserId());
            List<UserVideoSeriesVideo> userVideoSeriesVideoList = userVideoSeriesVideoService.findListByParam(videoSeriesVideoQuery);
            List<String> videoIdList = userVideoSeriesVideoList.stream().map(item->item.getVideoId()).collect(Collectors.toList());
            videoInfoQuery.setExcludeVideoIdArray(videoIdList.toArray(new String[0]));//过滤
        }
        videoInfoQuery.setUserId(tokenUserInfoDto.getUserId());
        List<VideoInfo> videoInfoList = videoInfoService.findListByParam(videoInfoQuery);
        return getSuccessResponseVO(videoInfoList);
    }

    @RequestMapping("/getVideoSeriesDetail")
    public ResponseVO getVideoSeriesDetail(@NotNull Integer seriesId) throws BusinessException {
        UserVideoSeries userVideoSeries = userVideoSeriesService.selectBySeriesId(seriesId);
        if (userVideoSeries == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        UserVideoSeriesVideoQuery userVideoSeriesVideoQuery = new UserVideoSeriesVideoQuery();
        userVideoSeriesVideoQuery.setSeriesId(seriesId);
        userVideoSeriesVideoQuery.setQueryVideoInfo(true);
        userVideoSeriesVideoQuery.setOrderBy("sort asc");
        List<UserVideoSeriesVideo> userVideoSeriesVideoList = userVideoSeriesVideoService.findListByParam(userVideoSeriesVideoQuery);
        UserVideoSeriesDetailVO userVideoSeriesDetailVO = new UserVideoSeriesDetailVO();
        userVideoSeriesDetailVO.setVideoSeries(userVideoSeries);
        userVideoSeriesDetailVO.setSeriesVideoList(userVideoSeriesVideoList);
        return getSuccessResponseVO(userVideoSeriesDetailVO);
    }

    @RequestMapping("/saveSeriesVideo")
    public ResponseVO saveSeriesVideo(@NotNull Integer seriesId, @NotEmpty String videoIds) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        userVideoSeriesService.saveUserVideoSeriesVideo(tokenUserInfoDto.getUserId(), seriesId, videoIds);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delSeriesVideo")
    public ResponseVO delSeriesVideo(@NotNull Integer seriesId, @NotEmpty String videoId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        userVideoSeriesService.delUserVideoSeriesVideo(tokenUserInfoDto.getUserId(), seriesId, videoId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delVideoSeries")
    public ResponseVO delVideoSeries(@NotNull Integer seriesId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        userVideoSeriesService.delVideoSeries(tokenUserInfoDto.getUserId(), seriesId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/changeVideoSeriesSort")
    public ResponseVO changeVideoSeriesSort(@NotEmpty String seriesIds) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        userVideoSeriesService.changeVideoSeriesSort(tokenUserInfoDto.getUserId(), seriesIds);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadVideoSeriesWithVideo")
    public ResponseVO loadVideoSeriesWithVideo(@NotEmpty String userId) throws BusinessException {
        UserVideoSeriesQuery userVideoSeriesQuery = new UserVideoSeriesQuery();
        userVideoSeriesQuery.setUserId(userId);
        userVideoSeriesQuery.setOrderBy("sort asc");
        List<UserVideoSeries> videoSeries = userVideoSeriesService.findListWithVideoList(userVideoSeriesQuery);
        return getSuccessResponseVO(videoSeries);
    }
}
