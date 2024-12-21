package org.jingtao8a.service;

import org.jingtao8a.entity.po.VideoDanmu;
import org.jingtao8a.entity.query.VideoDanmuQuery;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.vo.PaginationResultVO;

import java.util.List;

/**
@Description:视频弹幕Service
@Date:2024-12-17
*/
public interface VideoDanmuService {

	/**
	 * 根据条件查询列表
	*/
	List<VideoDanmu> findListByParam(VideoDanmuQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(VideoDanmuQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<VideoDanmu> findListByPage(VideoDanmuQuery param);

	/**
	 * 新增
	*/
	Long add(VideoDanmu bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(VideoDanmu bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<VideoDanmu> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<VideoDanmu> listBean);

	/**
	 * 根据DanmuId查询
	*/
	VideoDanmu selectByDanmuId(Integer danmuId);

	/**
	 * 根据DanmuId更新
	*/
	Long updateByDanmuId(VideoDanmu bean, Integer danmuId);

	/**
	 * 根据DanmuId删除
	*/
	Long deleteByDanmuId(Integer danmuId);

    void saveVideoDanmu(VideoDanmu videoDanmu) throws BusinessException;

	void deleteDanmu(Integer danmuId, String userId) throws BusinessException;
}