package org.jingtao8a.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.annotation.GlobalInterceptor;
import org.jingtao8a.constants.Constants;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.entity.po.UserAction;
import org.jingtao8a.entity.po.UserInfo;
import org.jingtao8a.entity.po.VideoInfo;
import org.jingtao8a.entity.query.UserActionQuery;
import org.jingtao8a.entity.query.UserFocusQuery;
import org.jingtao8a.entity.query.VideoInfoQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.enums.UserActionTypeEnum;
import org.jingtao8a.enums.VideoOrderTypeEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.service.UserActionService;
import org.jingtao8a.service.UserFocusService;
import org.jingtao8a.service.UserInfoService;
import org.jingtao8a.service.VideoInfoService;
import org.jingtao8a.vo.PaginationResultVO;
import org.jingtao8a.vo.ResponseVO;
import org.jingtao8a.vo.UserInfoVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.*;

@RestController
@Validated
@Slf4j
@RequestMapping("/uhome")
public class UHomeController extends ABaseController{
    @Resource
    private UserInfoService userInfoService;

    @Resource
    private VideoInfoService videoInfoService;

    @Resource
    private UserFocusService userFocusService;

    @Resource
    private UserActionService userActionService;

    @RequestMapping("/getUserInfo")
    public ResponseVO getUserInfo(@NotEmpty String userId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        String currentUserId = null;
        if (tokenUserInfoDto != null) {
            currentUserId = tokenUserInfoDto.getUserId();
        }
        UserInfoVO userInfoVO = userInfoService.getUserDetailInfo(currentUserId, userId);
        return getSuccessResponseVO(userInfoVO);
    }

    @RequestMapping("/updateUserInfo")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO updateUserInfo(@NotEmpty @Size(max=20) String nickName,
                                     @NotEmpty @Size(max=100) String avatar,
                                     @NotNull Integer sex,
                                     @Size(max=10) String birthday,
                                     @Size(max=150) String school,
                                     @Size(max=80) String personIntroduction,
                                     @Size(max=300) String noticeInfo) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(tokenUserInfoDto.getUserId());
        userInfo.setNickName(nickName);
        userInfo.setAvatar(avatar);
        userInfo.setSex(sex);
        userInfo.setBirthday(birthday);
        userInfo.setSchool(school);
        userInfo.setPersonIntroduction(personIntroduction);
        userInfo.setNoticeInfo(noticeInfo);

        userInfoService.updateUserInfo(userInfo, tokenUserInfoDto);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/saveTheme")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO saveTheme(@NotNull @Min(1) @Max(10) Integer theme) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        UserInfo userInfo = new UserInfo();
        userInfo.setTheme(theme);
        userInfoService.updateByUserId(userInfo, tokenUserInfoDto.getUserId());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/focus")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO focus(@NotNull @Size(max=10) String focusUserId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        userFocusService.focusUser(tokenUserInfoDto.getUserId(), focusUserId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/cancelFocus")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO cancelFocus(@NotNull @Size(max=10) String focusUserId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        userFocusService.cancelFocus(tokenUserInfoDto.getUserId(), focusUserId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadFocusList")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO loadFocusList(Integer pageNo, Integer pageSize) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        UserFocusQuery userFocusQuery = new UserFocusQuery();
        userFocusQuery.setUserId(tokenUserInfoDto.getUserId());
        try {
            userFocusQuery.setPageNo(Long.valueOf(pageNo));
        } catch (Exception e) {
            userFocusQuery.setPageNo(0L);
        }

        userFocusQuery.setOrderBy("focus_time desc");
        userFocusQuery.setQueryType(Constants.ZERO);
        PaginationResultVO resultVO = userFocusService.findListByPage(userFocusQuery);
        return getSuccessResponseVO(resultVO);
    }

    @RequestMapping("/loadFansList")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO loadFansList(Integer pageNo, Integer pageSize) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        UserFocusQuery userFocusQuery = new UserFocusQuery();
        userFocusQuery.setFocusUserId(tokenUserInfoDto.getUserId());
        try {
            userFocusQuery.setPageNo(Long.valueOf(pageNo));
        } catch (Exception e) {
            userFocusQuery.setPageNo(0L);
        }
        userFocusQuery.setOrderBy("focus_time desc");
        userFocusQuery.setQueryType(Constants.ONE);
        PaginationResultVO resultVO = userFocusService.findListByPage(userFocusQuery);
        return getSuccessResponseVO(resultVO);
    }

    @RequestMapping("/loadVideoList")
    public ResponseVO loadVideoList(@NotEmpty String userId,
                                    Integer type,//主页 或者 投稿页
                                    Integer pageNo,
                                    String videoName,//名字模糊查询
                                    Integer orderType//投稿页 按最新、最多播放、最多收藏 查询
                                    ) throws BusinessException {
        VideoInfoQuery videoInfoQuery = new VideoInfoQuery();
        if (type != null) {//主页
            videoInfoQuery.setPageSize(Long.valueOf(PageSize.SIZE15.getSize()));
            videoInfoQuery.setPageNo(0L);
        } else {//投稿页
            try {
                videoInfoQuery.setPageNo(Long.valueOf(pageNo));
            } catch (Exception e) {
                videoInfoQuery.setPageNo(0L);
            }
        }
        VideoOrderTypeEnum videoOrderTypeEnum = VideoOrderTypeEnum.getEnum(orderType);
        if (videoOrderTypeEnum == null) {
            videoOrderTypeEnum = VideoOrderTypeEnum.CREATE_TIME;
        }
        videoInfoQuery.setOrderBy(videoOrderTypeEnum.getField() + " desc");
        videoInfoQuery.setVideoNameFuzzy(videoName);
        videoInfoQuery.setUserId(userId);
        PaginationResultVO<VideoInfo> paginationResultVO = videoInfoService.findListByPage(videoInfoQuery);
        return getSuccessResponseVO(paginationResultVO);
    }

    @RequestMapping("/loadUserCollection")
    public ResponseVO loadUserCollection(@NotEmpty String userId, Integer pageNo) throws BusinessException {
        UserActionQuery userActionQuery = new UserActionQuery();
        userActionQuery.setActionType(UserActionTypeEnum.VIDEO_COLLECT.getType());
        userActionQuery.setUserId(userId);
        try {
            userActionQuery.setPageNo(Long.valueOf(pageNo));
        } catch (Exception e) {
            userActionQuery.setPageNo(0L);
        }
        userActionQuery.setOrderBy("action_time desc");
        userActionQuery.setQueryVideoInfo(true);
        PaginationResultVO<UserAction> paginationResultVO = userActionService.findListByPage(userActionQuery);
        return getSuccessResponseVO(paginationResultVO);
    }
}
