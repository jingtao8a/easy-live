package org.jingtao8a.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.annotation.GlobalInterceptor;
import org.jingtao8a.entity.po.StatisticsInfo;
import org.jingtao8a.entity.query.StatisticsInfoQuery;
import org.jingtao8a.entity.query.UserInfoQuery;
import org.jingtao8a.enums.StatisticTypeEnum;
import org.jingtao8a.service.StatisticsInfoService;
import org.jingtao8a.service.UserInfoService;
import org.jingtao8a.utils.DateUtils;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Validated
@Slf4j
@RequestMapping("/index")
public class IndexController extends ABaseController {
    @Resource
    private StatisticsInfoService statisticsInfoService;
    @Resource
    private UserInfoService userInfoService;

    @RequestMapping("/getActualTimeStatisticsInfo")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO getActualTimeStatisticsInfo() {
        //获取前一天的统计信息，存储在preDayDataMap中
        String preDate = DateUtils.getPreviousDayDate(1);
        StatisticsInfoQuery statisticsInfoQuery = new StatisticsInfoQuery();
        statisticsInfoQuery.setStatisticsDate(preDate);
        List<StatisticsInfo> preDayStatisticsInfoList = statisticsInfoService.findListTotalInfoByParam(statisticsInfoQuery);
        Integer userCount = userInfoService.findCountByParam(new UserInfoQuery()).intValue();
        Map<Integer, Integer> preDayDataMap = preDayStatisticsInfoList.stream().collect(Collectors.toMap(
                item -> item.getDataType(),
                item -> item.getStatisticsCount(),
                (data1, data2) -> data1
        ));
        preDayDataMap.put(StatisticTypeEnum.FANS.getType(), userCount);
        //获取总的统计信息，存储在totalCountInfo中
        Map<String, Integer> totalCountInfo = statisticsInfoService.getStatisticsInfoActualTime(null);
        Map<String, Object> result = new HashMap<>();
        result.put("preDayData", preDayDataMap);
        result.put("totalCountInfo", totalCountInfo);
        return getSuccessResponseVO(result);
    }

    @RequestMapping("/getWeekStatisticsInfo")
    public ResponseVO getWeekStatisticsInfo(@NotNull Integer dataType) {
        List<String> dateList = DateUtils.getPreviousDayDates(7);
        StatisticsInfoQuery statisticsInfoQuery = new StatisticsInfoQuery();
        statisticsInfoQuery.setDataType(dataType);
        statisticsInfoQuery.setStatisticsDateStart(dateList.get(0));
        statisticsInfoQuery.setStatisticsDateEnd(dateList.get(dateList.size() - 1));
        statisticsInfoQuery.setOrderBy("statistics_date asc");
        List<StatisticsInfo> statisticsInfoList = null;
        if (!StatisticTypeEnum.FANS.getType().equals(dataType)) {
            statisticsInfoList = statisticsInfoService.findListTotalInfoByParam(statisticsInfoQuery);
        } else {
            statisticsInfoList = statisticsInfoService.findListUserCountTotalInfoByParam(statisticsInfoQuery);
        }
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
            }
            resultDataList.add(statisticsInfo);
        }
        return getSuccessResponseVO(resultDataList);
    }
}
