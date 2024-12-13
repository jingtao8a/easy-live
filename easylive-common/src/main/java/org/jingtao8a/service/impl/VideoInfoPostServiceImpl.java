package org.jingtao8a.service.impl;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.apache.commons.lang3.ArrayUtils;
import org.jingtao8a.component.RedisComponent;
import org.jingtao8a.constants.Constants;
import org.jingtao8a.entity.po.VideoInfoFilePost;
import org.jingtao8a.entity.po.VideoInfoPost;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.VideoInfoFilePostQuery;
import org.jingtao8a.entity.query.VideoInfoPostQuery;
import org.jingtao8a.enums.*;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.mapper.VideoInfoFilePostMapper;
import org.jingtao8a.mapper.VideoInfoPostMapper;
import org.jingtao8a.service.VideoInfoPostService;
import org.jingtao8a.utils.StringTools;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
@Description:VideoInfoPostService
@Date:2024-12-11
*/
@Service("videoInfoPostService")
public class VideoInfoPostServiceImpl implements VideoInfoPostService {

	@Resource
	private VideoInfoPostMapper<VideoInfoPost, VideoInfoPostQuery> videoInfoPostMapper;

	@Resource
	private VideoInfoFilePostMapper<VideoInfoFilePost, VideoInfoFilePostQuery> videoInfoFilePostMapper;

    @Autowired
    private RedisComponent redisComponent;

	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<VideoInfoPost> findListByParam(VideoInfoPostQuery query) {
		return this.videoInfoPostMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(VideoInfoPostQuery query) {
		return this.videoInfoPostMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<VideoInfoPost> findListByPage(VideoInfoPostQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<VideoInfoPost> userInfoList = findListByParam(query);
		PaginationResultVO<VideoInfoPost> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(VideoInfoPost bean) {
		return videoInfoPostMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(VideoInfoPost bean) {
		return videoInfoPostMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<VideoInfoPost> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoInfoPostMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<VideoInfoPost> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoInfoPostMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据VideoId查询
	*/
	@Override
	public VideoInfoPost selectByVideoId(String videoId) {
		return videoInfoPostMapper.selectByVideoId(videoId);
	}

	/**
	 * 根据VideoId更新
	*/
	@Override
	public Long updateByVideoId(VideoInfoPost bean, String videoId) {
		return videoInfoPostMapper.updateByVideoId(bean, videoId);
	}

	/**
	 * 根据VideoId删除
	*/
	@Override
	public Long deleteByVideoId(String videoId) {
		return videoInfoPostMapper.deleteByVideoId(videoId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveVideoInfoPost(VideoInfoPost videoInfoPost, List<VideoInfoFilePost> videoInfoFilePostList) throws BusinessException {
		if (videoInfoFilePostList.size() > redisComponent.getSysSettingDto().getVideoPCount()) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		if (!StringTools.isEmpty(videoInfoPost.getVideoId())) {//更新操作
			VideoInfoPost videoInfoPostDb = this.videoInfoPostMapper.selectByVideoId(videoInfoPost.getVideoId());
			// 数据库中不存在该videoInfoPost
			if (videoInfoPostDb == null) {
				throw new BusinessException(ResponseCodeEnum.CODE_600);
			}
			// 转码中和待审核
			if (ArrayUtils.contains(new Integer[]{VideoStatusEnum.STATUS0.getStatus(), VideoStatusEnum.STATUS2.getStatus()}, videoInfoPostDb.getStatus())) {
				throw new BusinessException(ResponseCodeEnum.CODE_600);
			}
		}
		Date curDate = new Date();
		String videoId = videoInfoPost.getVideoId();
		List<VideoInfoFilePost> deleteVideoInfoFilePostList = new ArrayList<>();
		List<VideoInfoFilePost> addVideoInfoFilePostList = videoInfoFilePostList.stream().filter(item->item.getFileId() == null).collect(Collectors.toList());

		if (StringTools.isEmpty(videoId)) {//添加操作
			//只有origin_info duration 未初始化
			videoId = StringTools.getRandomString(Constants.LENGTH_10);
			videoInfoPost.setVideoId(videoId);
			videoInfoPost.setCreateTime(curDate);
			videoInfoPost.setLastUpdateTime(curDate);
			videoInfoPost.setStatus(VideoStatusEnum.STATUS0.getStatus());
			this.videoInfoPostMapper.insert(videoInfoPost);
		} else {//更新操作
			VideoInfoFilePostQuery videoInfoFilePostQuery = new VideoInfoFilePostQuery();
			videoInfoFilePostQuery.setVideoId(videoId);
			videoInfoFilePostQuery.setUserId(videoInfoPost.getUserId());
			List<VideoInfoFilePost> dbVideoInfoFilePostList = this.videoInfoFilePostMapper.selectList(videoInfoFilePostQuery);
			Map<String, VideoInfoFilePost> uploadFileMap = videoInfoFilePostList.stream().collect(Collectors.toMap(item->item.getUploadId(), Function.identity(), (data1, data2)->data2));

			boolean updateFileName = false;
			//遍历数据库中的dbVideoInfoFilePost
			for (VideoInfoFilePost dbFileInfo : dbVideoInfoFilePostList) {
				VideoInfoFilePost updateFile = uploadFileMap.get(dbFileInfo.getUploadId());
				if (updateFile == null) {
					deleteVideoInfoFilePostList.add(dbFileInfo);
				} else if (!updateFile.getFileName().equals(dbFileInfo.getFileName())) {
					updateFileName = true;
				}
			}

			videoInfoPost.setLastUpdateTime(curDate);
			Boolean changeVideoInfo = this.changeVideoInfoPost(videoInfoPost);

			if (addVideoInfoFilePostList != null && !addVideoInfoFilePostList.isEmpty()) {
				videoInfoPost.setStatus(VideoStatusEnum.STATUS0.getStatus());
			} else if (changeVideoInfo || updateFileName) {//更新了videoInfoPost的标题，封面，标签，简介 或者 file的名字
				videoInfoPost.setStatus(VideoStatusEnum.STATUS2.getStatus());
			}
			this.videoInfoPostMapper.updateByVideoId(videoInfoPost, videoId);
		}
		//操作VideoInfoFilePost
		if (!deleteVideoInfoFilePostList.isEmpty()) {
			List<String> delFileIdList = deleteVideoInfoFilePostList.stream().map(item->item.getFileId()).collect(Collectors.toList());
			this.videoInfoFilePostMapper.deleteBatchByFileIdList(delFileIdList, videoInfoPost.getUserId());
			List<String> delFilePathList = deleteVideoInfoFilePostList.stream().map(item->item.getFilePath()).collect(Collectors.toList());
			redisComponent.addFile2DelQueue(videoId, delFilePathList);
		}

		//更新视频
		Integer index = 1;
		for (VideoInfoFilePost videoInfoFilePost : videoInfoFilePostList) {
			videoInfoFilePost.setUserId(videoInfoPost.getUserId());
			videoInfoFilePost.setVideoId(videoId);
			videoInfoFilePost.setFileIndex(index++);
			if (videoInfoFilePost.getFileId() == null) {
				videoInfoFilePost.setFileId(StringTools.getRandomString(Constants.LENGTH_20));
				videoInfoFilePost.setUpdateType(VideoFileUpdateTypeEnum.UPDATE.getStatus());
				videoInfoFilePost.setTransferResult(VideoFileTransferResultEnum.TRANSFER.getStatus());
			}
		}
		this.videoInfoFilePostMapper.insertOrUpdateBatch(videoInfoFilePostList);

		if (addVideoInfoFilePostList != null && !addVideoInfoFilePostList.isEmpty()) {
			redisComponent.addFile2TransferQueue(addVideoInfoFilePostList);
		}
	}

	private Boolean changeVideoInfoPost(VideoInfoPost videoInfoPost) {
		VideoInfoPost dbInfo = this.videoInfoPostMapper.selectByVideoId(videoInfoPost.getVideoId());
		//标题，封面，标签，简介
		if (!videoInfoPost.equals(dbInfo.getVideoName())
				|| !videoInfoPost.getVideoCover().equals(dbInfo.getVideoCover())
				|| !videoInfoPost.getTags().equals(dbInfo.getTags())
				|| !videoInfoPost.getInteraction().equals(dbInfo.getInteraction())) {
			return true;
		}
		return false;
	}

}