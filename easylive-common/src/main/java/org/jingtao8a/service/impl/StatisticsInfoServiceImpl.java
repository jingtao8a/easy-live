package org.jingtao8a.service.impl;

import org.jingtao8a.entity.po.StatisticsInfo;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.StatisticsInfoQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.mapper.StatisticsInfoMapper;
import org.jingtao8a.service.StatisticsInfoService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
@Description:StatisticsInfoService
@Date:2024-12-25
*/
@Service("statisticsInfoService")
public class StatisticsInfoServiceImpl implements StatisticsInfoService {

	@Resource
	private StatisticsInfoMapper<StatisticsInfo, StatisticsInfoQuery> statisticsInfoMapper;
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

}