package org.jingtao8a.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.service.StatisticsInfoService;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@Validated
@RequestMapping("/test")
public class TestController extends ABaseController{
    @Resource
    private StatisticsInfoService statisticsInfoService;

    @RequestMapping("/test1")
    public ResponseVO test1(){
        return getSuccessResponseVO(statisticsInfoService.statisticData());
    }
}
