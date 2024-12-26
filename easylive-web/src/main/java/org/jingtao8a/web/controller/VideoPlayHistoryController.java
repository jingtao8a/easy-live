package org.jingtao8a.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.annotation.GlobalInterceptor;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.entity.query.VideoPlayHistoryQuery;
import org.jingtao8a.service.VideoPlayHistoryService;
import org.jingtao8a.vo.PaginationResultVO;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Validated
@Slf4j
@RequestMapping("/history")
public class VideoPlayHistoryController extends ABaseController {
    @Resource
    private VideoPlayHistoryService videoPlayHistoryService;

    @RequestMapping("/loadHistory")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO loadHistory(Integer pageNo) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        VideoPlayHistoryQuery videoPlayHistoryQuery = new VideoPlayHistoryQuery();
        videoPlayHistoryQuery.setUserId(tokenUserInfoDto.getUserId());
        videoPlayHistoryQuery.setOrderBy("last_update_time desc");
        videoPlayHistoryQuery.setPageNo(pageNo == null ? 1L : pageNo.longValue());
        videoPlayHistoryQuery.setQueryVideoDetail(true);
        PaginationResultVO resultVO = videoPlayHistoryService.findListByPage(videoPlayHistoryQuery);
        return getSuccessResponseVO(resultVO);
    }
}
