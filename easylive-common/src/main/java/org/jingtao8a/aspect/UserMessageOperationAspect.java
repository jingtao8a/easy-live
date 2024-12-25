package org.jingtao8a.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.jingtao8a.annotation.RecordMessage;
import org.jingtao8a.component.RedisComponent;
import org.jingtao8a.constants.Constants;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.enums.MessageTypeEnum;
import org.jingtao8a.enums.UserActionTypeEnum;
import org.jingtao8a.service.UserMessageService;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
@Slf4j
public class UserMessageOperationAspect {
    @Resource
    private RedisComponent redisComponent;

    @Resource
    private UserMessageService userMessageService;

    @Around("@annotation(org.jingtao8a.annotation.RecordMessage)")
    public ResponseVO interceptroDo(ProceedingJoinPoint joinPoint) throws Throwable {
        ResponseVO responseVO = (ResponseVO) joinPoint.proceed();
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        RecordMessage recordMessage = method.getAnnotation(RecordMessage.class);
        if (recordMessage != null) {
            saveMessage(recordMessage, joinPoint.getArgs(), method.getParameters());
        }
        return responseVO;
    }

    private void saveMessage(RecordMessage recordMessage, Object[] args, Parameter[] parameters) {
        //postComment
        String videoId = null;
        String content = null;
        Integer replyCommentId = null;
        //doAction
        Integer actionType = null;
        //auditVideo
        String reason = null;
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getName().equals("videoId")) {
                videoId = (String)args[i];
            } else if (parameters[i].getName().equals("actionType")) {
                actionType = (Integer)args[i];
            } else if (parameters[i].getName().equals("content")) {
                content = (String)args[i];
            } else if (parameters[i].getName().equals("reason")) {
                reason = (String)args[i];
            } else if (parameters[i].getName().equals("replyCommentId")) {
                replyCommentId = (Integer) args[i];
            }
        }
        MessageTypeEnum messageTypeEnum = recordMessage.messageType();
        UserActionTypeEnum userActionTypeEnum = UserActionTypeEnum.getEnum(actionType);
        if (userActionTypeEnum != null) {
            if (UserActionTypeEnum.VIDEO_COLLECT == userActionTypeEnum) {
                messageTypeEnum = MessageTypeEnum.COLLECTION;
            } else if (UserActionTypeEnum.VIDEO_LIKE != userActionTypeEnum) {
                messageTypeEnum = MessageTypeEnum.LIKE;
            } else {
                return;
            }
        }
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        String sendUserId = null;
        if (tokenUserInfoDto != null) {
            sendUserId = tokenUserInfoDto.getUserId();
        }
        userMessageService.saveUserMessage(videoId, content, replyCommentId, reason, messageTypeEnum, sendUserId);
    }

    private TokenUserInfoDto getTokenUserInfoDto() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(Constants.TOKEN_WEB);//autologin时，前端负责将cookies中的TOKEN_WEB单独放在header中
        return redisComponent.getTokenInfo(token);
    }
}
