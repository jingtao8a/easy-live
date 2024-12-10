package org.jingtao8a.admin.interceptor;

import io.netty.util.Constant;
import org.jingtao8a.component.RedisComponent;
import org.jingtao8a.constants.Constants;
import org.jingtao8a.enums.ResponseCodeEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.utils.StringTools;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AppInterceptor implements HandlerInterceptor {
    private final static String URL_ACCOUNT = "/account";
    private final static String URL_FILE = "/file";

    @Resource
    private RedisComponent redisComponent;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (null == handler) {
            return false;
        }
        if (!(handler instanceof HandlerMethod)) {//请求静态资源直接返回true
            return true;
        }
        if (request.getRequestURI().contains(URL_ACCOUNT)) {//请求url包含/account直接返回true
            return true;
        }
        String token = request.getHeader(Constants.TOKEN_ADMIN);//从请求头中查找TOKEN_ADMIN
        //获取图片
        if (request.getRequestURI().contains(URL_FILE)) {//从cookie中查找TOKEN_ADMIN
            token = getTokenFromCookie(request);
        }
        if (StringTools.isEmpty(token)) {//请求头和cookie中均没没找到TOKEN_ADMIN
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }
        Object sessionObj = redisComponent.getTokenInfo4Admin(token);
        if (null == sessionObj) {//redis中的token已经失效
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }
        return true;
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Constants.TOKEN_ADMIN)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
