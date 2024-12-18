package org.jingtao8a.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.constants.Constants;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.entity.po.UserAction;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.service.UserActionService;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.validation.constraints.*;

@RestController
@Validated
@Slf4j
@RequestMapping("/userAction")
public class UserActionController extends ABaseController {
    @Resource
    private UserActionService userActionService;

    @RequestMapping("/doAction")
    public ResponseVO doAction(@NotEmpty String videoId,
                               @NotNull Integer actionType,
                               @Max(2) @Min(1) Integer actionCount,
                               Integer commentId) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        UserAction userAction = new UserAction();
        userAction.setVideoId(videoId);
        userAction.setActionType(actionType);
        actionCount = actionCount == null ? Constants.ONE : actionCount;
        userAction.setActionCount(actionCount);
        commentId = commentId == null ? 0 : commentId;
        userAction.setCommentId(commentId);
        userAction.setUserId(tokenUserInfoDto.getUserId());
        userActionService.saveAction(userAction);
        return getSuccessResponseVO(null);
    }
}
