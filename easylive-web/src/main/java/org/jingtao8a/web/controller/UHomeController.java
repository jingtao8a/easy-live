package org.jingtao8a.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.entity.po.UserAction;
import org.jingtao8a.entity.po.UserInfo;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.service.UserActionService;
import org.jingtao8a.service.UserFocusService;
import org.jingtao8a.service.UserInfoService;
import org.jingtao8a.service.VideoInfoService;
import org.jingtao8a.utils.CopyTools;
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
    public ResponseVO updateUserInfo(@NotEmpty @Size(max=20) String nickName,
                                     @NotEmpty @Size(max=100) String avatar,
                                     @NotNull Integer sex,
                                     @Size(max=10) String birthday,
                                     @Size(max=150) String school,
                                     @Size(max=80) String personIntroduction,
                                     @Size(max=300) String noticeInfo) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
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
    public ResponseVO saveTheme(@NotNull @Min(1) @Max(10) Integer theme) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setTheme(theme);
        userInfoService.updateByUserId(userInfo, tokenUserInfoDto.getUserId());
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/focus")
    public ResponseVO focus(@NotNull @Size(max=10) String focusUserId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        userFocusService.focusUser(tokenUserInfoDto.getUserId(), focusUserId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/cancelFocus")
    public ResponseVO cancelFocus(@NotNull @Size(max=10) String focusUserId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        if (tokenUserInfoDto == null) {
            throw new BusinessException("未登入");
        }
        userFocusService.cancelFocus(tokenUserInfoDto.getUserId(), focusUserId);
        return getSuccessResponseVO(null);
    }
}
