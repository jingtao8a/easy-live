package org.jingtao8a.web.task;

import org.jingtao8a.service.StatisticsInfoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SysTask {
    @Resource
    private StatisticsInfoService statisticsInfoService;

    //定时任务
    @Scheduled(cron = "0 0 0 * * ?")
    public void statisticsData() {
        statisticsInfoService.statisticData();
    }
}
