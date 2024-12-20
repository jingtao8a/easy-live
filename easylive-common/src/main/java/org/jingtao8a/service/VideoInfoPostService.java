package org.jingtao8a.service;

import org.jingtao8a.entity.po.VideoInfoFilePost;
import org.jingtao8a.entity.po.VideoInfoPost;
import org.jingtao8a.entity.query.VideoInfoPostQuery;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.vo.PaginationResultVO;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
@Description:视频信息Service
@Date:2024-12-11
*/
public interface VideoInfoPostService {

	/**
	 * 根据条件查询列表
	*/
	List<VideoInfoPost> findListByParam(VideoInfoPostQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(VideoInfoPostQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<VideoInfoPost> findListByPage(VideoInfoPostQuery param);

	/**
	 * 新增
	*/
	Long add(VideoInfoPost bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(VideoInfoPost bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<VideoInfoPost> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<VideoInfoPost> listBean);

	/**
	 * 根据VideoId查询
	*/
	VideoInfoPost selectByVideoId(String videoId);

	/**
	 * 根据VideoId更新
	*/
	Long updateByVideoId(VideoInfoPost bean, String videoId);

	/**
	 * 根据VideoId删除
	*/
	Long deleteByVideoId(String videoId);

	void saveVideoInfoPost(VideoInfoPost videoInfoPost, List<VideoInfoFilePost> videoInfoFilePostList) throws BusinessException;

    void auditVideo(String videoId, Integer status, String reason) throws BusinessException;

    void changeInteraction(String videoId, String userId, String interaction);
}