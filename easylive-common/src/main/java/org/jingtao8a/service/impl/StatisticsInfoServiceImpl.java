package org.jingtao8a.service.impl;

import org.jingtao8a.component.RedisComponent;
import org.jingtao8a.constants.Constants;
import org.jingtao8a.entity.po.*;
import org.jingtao8a.entity.query.*;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.enums.StatisticTypeEnum;
import org.jingtao8a.enums.UserActionTypeEnum;
import org.jingtao8a.mapper.StatisticsInfoMapper;
import org.jingtao8a.mapper.UserFocusMapper;
import org.jingtao8a.mapper.UserInfoMapper;
import org.jingtao8a.mapper.VideoInfoMapper;
import org.jingtao8a.service.StatisticsInfoService;
import org.jingtao8a.utils.DateUtils;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
/**
@Description:StatisticsInfoService
@Date:2024-12-25
*/
@Service("statisticsInfoService")
public class StatisticsInfoServiceImpl implements StatisticsInfoService {

	@Resource
	private StatisticsInfoMapper<StatisticsInfo, StatisticsInfoQuery> statisticsInfoMapper;

	@Resource
	private RedisComponent redisComponent;
    @Resource
    private VideoInfoMapper<VideoInfo, VideoInfoQuery> videoInfoMapper;
    @Resource
    private UserFocusMapper<UserFocus, UserFocusQuery> userFocusMapper;
    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<StatisticsInfo> findListByParam(StatisticsInfoQuery query) {
		return this.statisticsInfoMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(StatisticsInfoQuery query) {
		return this.statisticsInfoMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<StatisticsInfo> findListByPage(StatisticsInfoQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<StatisticsInfo> userInfoList = findListByParam(query);
		PaginationResultVO<StatisticsInfo> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(StatisticsInfo bean) {
		return statisticsInfoMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(StatisticsInfo bean) {
		return statisticsInfoMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<StatisticsInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return statisticsInfoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<StatisticsInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return statisticsInfoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据StatisticsDateAndUserIdAndDataType查询
	*/
	@Override
	public StatisticsInfo selectByStatisticsDateAndUserIdAndDataType(String statisticsDate, String userId, Integer dataType) {
		return statisticsInfoMapper.selectByStatisticsDateAndUserIdAndDataType(statisticsDate, userId, dataType);
	}

	/**
	 * 根据StatisticsDateAndUserIdAndDataType更新
	*/
	@Override
	public Long updateByStatisticsDateAndUserIdAndDataType(StatisticsInfo bean, String statisticsDate, String userId, Integer dataType) {
		return statisticsInfoMapper.updateByStatisticsDateAndUserIdAndDataType(bean, statisticsDate, userId, dataType);
	}

	/**
	 * 根据StatisticsDateAndUserIdAndDataType删除
	*/
	@Override
	public Long deleteByStatisticsDateAndUserIdAndDataType(String statisticsDate, String userId, Integer dataType) {
		return statisticsInfoMapper.deleteByStatisticsDateAndUserIdAndDataType(statisticsDate, userId, dataType);
	}

	@Override
	public List<StatisticsInfo> statisticData() {

		List<StatisticsInfo> statisticsInfoList = new ArrayList();

		final String statisticsDate = DateUtils.getPreviousDayDate(1);
		//统计播放量
		Map<String, Integer> videoPlayCountMap = redisComponent.getVideoPlayCount(statisticsDate);
		List<String> videoPlayKeys = new ArrayList<>(videoPlayCountMap.keySet());
		videoPlayKeys = videoPlayKeys.stream().map(item -> item.substring(item.lastIndexOf(":") + 1)).collect(Collectors.toList());

		VideoInfoQuery videoInfoQuery = new VideoInfoQuery();
		videoInfoQuery.setVideoIdArray(videoPlayKeys.toArray(new String[videoPlayKeys.size()]));
		List<VideoInfo> videoInfoList = videoInfoMapper.selectList(videoInfoQuery);
		Map<String, Integer> videoCountMap = videoInfoList.stream().collect(Collectors.groupingBy(VideoInfo::getUserId,
				Collectors.summingInt(
						item -> videoPlayCountMap.get(Constants.REDIS_KEY_VIDEO_PLAY_COUNT + statisticsDate + ":" + item.getVideoId())
				)));
		videoCountMap.forEach((k, v) -> {
			StatisticsInfo statisticsInfo = new StatisticsInfo();
			statisticsInfo.setStatisticsDate(statisticsDate);
			statisticsInfo.setUserId(k);
			statisticsInfo.setDataType(StatisticTypeEnum.PLAY.getType());
			statisticsInfo.setStatisticsCount(v);
			statisticsInfoList.add(statisticsInfo);
		});

		//统计粉丝量
		List<StatisticsInfo> fansDataList = statisticsInfoMapper.selectStatisticsFans(statisticsDate);
		for (StatisticsInfo statisticsInfo : fansDataList) {
			statisticsInfo.setStatisticsDate(statisticsDate);
			statisticsInfo.setDataType(StatisticTypeEnum.FANS.getType());
		}
		statisticsInfoList.addAll(fansDataList);

		//统计评论
		List<StatisticsInfo> commentDataList = statisticsInfoMapper.selectStatisticsComment(statisticsDate);
		for (StatisticsInfo statisticsInfo : commentDataList) {
			statisticsInfo.setStatisticsDate(statisticsDate);
			statisticsInfo.setDataType(StatisticTypeEnum.COMMENT.getType());
		}
		statisticsInfoList.addAll(commentDataList);

		//统计弹幕
		List<StatisticsInfo> danmuDataList = statisticsInfoMapper.selectStatisticsDanmu(statisticsDate);
		for (StatisticsInfo statisticsInfo : danmuDataList) {
			statisticsInfo.setStatisticsDate(statisticsDate);
			statisticsInfo.setDataType(StatisticTypeEnum.DANMU.getType());
		}
		statisticsInfoList.addAll(danmuDataList);

		//统计 点赞、收藏、投币
		List<StatisticsInfo> statisticsInfosOthers = statisticsInfoMapper.selectStatisticsOthers(statisticsDate, new Integer[]{UserActionTypeEnum.VIDEO_LIKE.getType(), UserActionTypeEnum.VIDEO_COIN.getType(), UserActionTypeEnum.VIDEO_COLLECT.getType()});
		for (StatisticsInfo statisticsInfo : statisticsInfosOthers) {
			statisticsInfo.setStatisticsDate(statisticsDate);
			UserActionTypeEnum userActionTypeEnum = UserActionTypeEnum.getEnum(statisticsInfo.getDataType());
			switch (userActionTypeEnum) {
				case VIDEO_LIKE:
					statisticsInfo.setDataType(StatisticTypeEnum.LIKE.getType());
					break;
				case VIDEO_COIN:
					statisticsInfo.setDataType(StatisticTypeEnum.COIN.getType());
					break;
				case VIDEO_COLLECT:
					statisticsInfo.setDataType(StatisticTypeEnum.COLLECTION.getType());
					break;
			}
		}
		statisticsInfoList.addAll(statisticsInfosOthers);
		statisticsInfoMapper.insertBatch(statisticsInfoList);
		return statisticsInfoList;
	}

	@Override
	public Map<String, Integer> getStatisticsInfoActualTime(String userId) {
		Map<String, Integer> result = statisticsInfoMapper.selectTotalCountInfo(userId);
		if (userId != null) {//web
			result.put("userCount", userFocusMapper.selectFansCount(userId).intValue());//粉丝数
		} else { //admin
			result.put("userCount", userInfoMapper.selectCount(new UserInfoQuery()).intValue());//用户数
		}
		return result;
	}

	@Override
	public List<StatisticsInfo> findListTotalInfoByParam(StatisticsInfoQuery statisticsInfoQuery) {
		return statisticsInfoMapper.selectListTotalInfoByParam(statisticsInfoQuery);
	}

	@Override
	public List<StatisticsInfo> findListUserCountTotalInfoByParam(StatisticsInfoQuery statisticsInfoQuery) {
		return statisticsInfoMapper.selectListUserCountTotalInfoByParam(statisticsInfoQuery);
	}

}