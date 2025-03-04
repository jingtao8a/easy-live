package org.jingtao8a.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.annotation.GlobalInterceptor;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.entity.po.*;
import org.jingtao8a.entity.query.*;
import org.jingtao8a.enums.ResponseCodeEnum;
import org.jingtao8a.enums.VideoStatusEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.service.StatisticsInfoService;
import org.jingtao8a.service.VideoInfoFilePostService;
import org.jingtao8a.service.VideoInfoPostService;
import org.jingtao8a.service.VideoInfoService;
import org.jingtao8a.service.impl.VideoCommentServiceImpl;
import org.jingtao8a.service.impl.VideoDanmuServiceImpl;
import org.jingtao8a.utils.DateUtils;
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
import java.util.stream.Collectors;

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
    private VideoCommentServiceImpl videoCommentService;
    @Resource
    private VideoDanmuServiceImpl videoDanmuService;
    @Resource
    private StatisticsInfoService statisticsInfoService;

    @RequestMapping("/postVideo")
    @GlobalInterceptor(checkLogin = true)
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
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO loadVideoList(Integer status, Long pageNo, String videoNameFuzzy) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
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
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO getVideoCountInfo() throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
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
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO getVideoByVideoId(@NotEmpty String videoId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        VideoInfoPost videoInfoPost = videoInfoPostService.selectByVideoId(videoId);
        if (videoInfoPost == null || !videoInfoPost.getUserId().equals(tokenUserInfoDto.getUserId())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
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

    @RequestMapping("/saveVideoInteraction")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO saveVideoInteraction(@NotEmpty String videoId, String interaction) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        videoInfoPostService.changeInteraction(videoId, tokenUserInfoDto.getUserId(), interaction);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/deleteVideo")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO deleteVideo(@NotEmpty String videoId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        videoInfoPostService.deleteVideo(videoId, tokenUserInfoDto.getUserId());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadAllVideo")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO loadAllVideo() throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        VideoInfoQuery videoInfoQuery = new VideoInfoQuery();
        videoInfoQuery.setUserId(tokenUserInfoDto.getUserId());
        videoInfoQuery.setOrderBy("create_time desc");
        List<VideoInfo> videoInfoList = videoInfoService.findListByParam(videoInfoQuery);
        return getSuccessResponseVO(videoInfoList);
    }

    @RequestMapping("/loadComment")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO loadComment(Integer pageNo, Integer pageSize, String videoId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        VideoCommentQuery videoCommentQuery = new VideoCommentQuery();
        videoCommentQuery.setVideoId(videoId);
        videoCommentQuery.setVideoUserId(tokenUserInfoDto.getUserId());
        videoCommentQuery.setOrderBy("comment_id desc");
        videoCommentQuery.setPageNo(pageNo == null ? null: pageNo.longValue());
        videoCommentQuery.setPageSize(pageSize == null ? null: pageSize.longValue());
        videoCommentQuery.setQueryVideoInfo(true);
        PaginationResultVO<VideoComment> resultVO = videoCommentService.findListByPage(videoCommentQuery);
        return getSuccessResponseVO(resultVO);
    }

    @RequestMapping("/delComment")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO delComment(@NotNull Integer commentId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        videoCommentService.deleteComment(commentId, tokenUserInfoDto.getUserId());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadDanmu")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO loadDanmu(Integer pageNo, Integer pageSize, String videoId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        VideoDanmuQuery videoDanmuQuery = new VideoDanmuQuery();
        videoDanmuQuery.setVideoId(videoId);
        videoDanmuQuery.setVideoUserId(tokenUserInfoDto.getUserId());
        videoDanmuQuery.setOrderBy("danmu_id desc");
        videoDanmuQuery.setPageNo(pageNo == null ? null: pageNo.longValue());
        videoDanmuQuery.setPageSize(pageSize == null ? null: pageSize.longValue());
        videoDanmuQuery.setQueryVideoInfo(true);
        PaginationResultVO<VideoDanmu> resultVO = videoDanmuService.findListByPage(videoDanmuQuery);
        return getSuccessResponseVO(resultVO);
    }

    @RequestMapping("/delDanmu")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO delDanmu(@NotNull Integer danmuId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        videoDanmuService.deleteDanmu(danmuId, tokenUserInfoDto.getUserId());
        return getSuccessResponseVO(null);
    }


    @RequestMapping("/getActualTimeStatisticsInfo")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO getActualTimeStatisticsInfo() {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        //获取前一天的统计信息，存储在preDayDataMap中
        String preDate = DateUtils.getPreviousDayDate(1);
        StatisticsInfoQuery statisticsInfoQuery = new StatisticsInfoQuery();
        statisticsInfoQuery.setUserId(tokenUserInfoDto.getUserId());
        statisticsInfoQuery.setStatisticsDate(preDate);
        List<StatisticsInfo> preDayStatisticsInfoList = statisticsInfoService.findListByParam(statisticsInfoQuery);
        Map<Integer, Integer> preDayDataMap = preDayStatisticsInfoList.stream().collect(Collectors.toMap(
                item -> item.getDataType(),
                item -> item.getStatisticsCount(),
                (data1, data2) -> data1
        ));
        //获取总的统计信息，存储在totalCountInfo中
        Map<String, Integer> totalCountInfo = statisticsInfoService.getStatisticsInfoActualTime(tokenUserInfoDto.getUserId());
        Map<String, Object> result = new HashMap<>();
        result.put("preDayData", preDayDataMap);
        result.put("totalCountInfo", totalCountInfo);
        return getSuccessResponseVO(result);
    }

    @RequestMapping("/getWeekStatisticsInfo")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO getWeekStatisticsInfo(@NotNull Integer dataType) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        List<String> dateList = DateUtils.getPreviousDayDates(7);
        StatisticsInfoQuery statisticsInfoQuery = new StatisticsInfoQuery();
        statisticsInfoQuery.setUserId(tokenUserInfoDto.getUserId());
        statisticsInfoQuery.setDataType(dataType);
        statisticsInfoQuery.setStatisticsDateStart(dateList.get(0));
        statisticsInfoQuery.setStatisticsDateEnd(dateList.get(dateList.size() - 1));
        statisticsInfoQuery.setOrderBy("statistics_date asc");
        List<StatisticsInfo> statisticsInfoList = statisticsInfoService.findListByParam(statisticsInfoQuery);
        Map<String, StatisticsInfo> dataMap = statisticsInfoList.stream().collect(Collectors.toMap(
                item -> item.getStatisticsDate(),
                item -> item,
                (data1, data2) -> data1
        ));
        List<StatisticsInfo> resultDataList = new ArrayList<>();
        for (String date : dateList) {
            StatisticsInfo statisticsInfo = dataMap.get(date);
            if (statisticsInfo == null) {
                statisticsInfo = new StatisticsInfo();
                statisticsInfo.setStatisticsDate(date);
                statisticsInfo.setStatisticsCount(0);
                statisticsInfo.setDataType(dataType);
                statisticsInfo.setUserId(tokenUserInfoDto.getUserId());
            }
            resultDataList.add(statisticsInfo);
        }
        return getSuccessResponseVO(resultDataList);
    }
}
