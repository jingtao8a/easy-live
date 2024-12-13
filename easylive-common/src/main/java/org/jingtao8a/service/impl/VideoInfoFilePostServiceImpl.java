package org.jingtao8a.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jingtao8a.component.RedisComponent;
import org.jingtao8a.config.AppConfig;
import org.jingtao8a.constants.Constants;
import org.jingtao8a.dto.UploadingFileDto;
import org.jingtao8a.entity.po.VideoInfoFilePost;
import org.jingtao8a.entity.po.VideoInfoPost;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.entity.query.VideoInfoFilePostQuery;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.enums.VideoFileTransferResultEnum;
import org.jingtao8a.enums.VideoStatusEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.mapper.VideoInfoFilePostMapper;
import org.jingtao8a.mapper.VideoInfoPostMapper;
import org.jingtao8a.service.VideoInfoFilePostService;
import org.jingtao8a.utils.FFmpegUtils;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.RandomAccess;

/**
@Description:VideoInfoFilePostService
@Date:2024-12-11
*/
@Service("videoInfoFilePostService")
@Slf4j
public class VideoInfoFilePostServiceImpl implements VideoInfoFilePostService {

	@Resource
	private VideoInfoFilePostMapper<VideoInfoFilePost, VideoInfoFilePostQuery> videoInfoFilePostMapper;
    @Resource
    private RedisComponent redisComponent;
	@Resource
	private AppConfig appConfig;
	@Resource
	private FFmpegUtils fFmpegUtils;
    @Autowired
    private VideoInfoPostMapper videoInfoPostMapper;

	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<VideoInfoFilePost> findListByParam(VideoInfoFilePostQuery query) {
		return this.videoInfoFilePostMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(VideoInfoFilePostQuery query) {
		return this.videoInfoFilePostMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<VideoInfoFilePost> findListByPage(VideoInfoFilePostQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<VideoInfoFilePost> userInfoList = findListByParam(query);
		PaginationResultVO<VideoInfoFilePost> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(VideoInfoFilePost bean) {
		return videoInfoFilePostMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(VideoInfoFilePost bean) {
		return videoInfoFilePostMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<VideoInfoFilePost> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoInfoFilePostMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<VideoInfoFilePost> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return videoInfoFilePostMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据FileId查询
	*/
	@Override
	public VideoInfoFilePost selectByFileId(String fileId) {
		return videoInfoFilePostMapper.selectByFileId(fileId);
	}

	/**
	 * 根据FileId更新
	*/
	@Override
	public Long updateByFileId(VideoInfoFilePost bean, String fileId) {
		return videoInfoFilePostMapper.updateByFileId(bean, fileId);
	}

	/**
	 * 根据FileId删除
	*/
	@Override
	public Long deleteByFileId(String fileId) {
		return videoInfoFilePostMapper.deleteByFileId(fileId);
	}

	/**
	 * 根据UploadId查询
	*/
	@Override
	public VideoInfoFilePost selectByUploadId(String uploadId) {
		return videoInfoFilePostMapper.selectByUploadId(uploadId);
	}

	/**
	 * 根据UploadId更新
	*/
	@Override
	public Long updateByUploadId(VideoInfoFilePost bean, String uploadId) {
		return videoInfoFilePostMapper.updateByUploadId(bean, uploadId);
	}

	/**
	 * 根据UploadId删除
	*/
	@Override
	public Long deleteByUploadId(String uploadId) {
		return videoInfoFilePostMapper.deleteByUploadId(uploadId);
	}

	@Override
	public void transferVideoFile(VideoInfoFilePost videoInfoFilePost) {
		VideoInfoFilePost updateFilePost = new VideoInfoFilePost();
		try {
			UploadingFileDto fileDto = redisComponent.getUploadingFileDto(videoInfoFilePost.getUserId(), videoInfoFilePost.getUploadId());
			String tempFilePath = appConfig.getProjectFolder() + Constants.FILE_FOLDER + Constants.FILE_TEMP + fileDto.getFilePath();
			File tempFile = new File(tempFilePath);
			String targetFilePath = appConfig.getProjectFolder() + Constants.FILE_FOLDER + Constants.FILE_VIDEO + fileDto.getFilePath();
			File targetFile = new File(targetFilePath);
			FileUtils.copyDirectory(tempFile, targetFile);
			//删除临时目录
			FileUtils.forceDelete(tempFile);
			redisComponent.delUploadingFileDto(videoInfoFilePost.getUserId(), videoInfoFilePost.getUploadId());

			//合并文件
			String completeVideo = targetFilePath + Constants.TEMP_VIDEO_NAME;
			this.union(targetFilePath, completeVideo, true);

			//获取播放时长
			Integer duration = fFmpegUtils.getVideoInfoDuration(completeVideo);
			updateFilePost.setDuration(duration);
			updateFilePost.setFileSize(new File(completeVideo).length());
			updateFilePost.setFilePath(Constants.FILE_VIDEO + fileDto.getFilePath());
			updateFilePost.setTransferResult(VideoFileTransferResultEnum.SUCCESS.getStatus());

			this.convertVideo2Ts(completeVideo);
		} catch (Exception e) {
			log.error("文件转码失败", e);
			updateFilePost.setTransferResult(VideoFileTransferResultEnum.FAIL.getStatus());
		} finally {
			videoInfoFilePostMapper.updateByFileId(updateFilePost, videoInfoFilePost.getFileId());
			VideoInfoFilePostQuery filePostQuery = new VideoInfoFilePostQuery();
			filePostQuery.setVideoId(videoInfoFilePost.getVideoId());
			filePostQuery.setTransferResult(VideoFileTransferResultEnum.FAIL.getStatus());
			Long failCount = videoInfoFilePostMapper.selectCount(filePostQuery);
			if (failCount > 0L) {
				VideoInfoPost updateVideoInfoPost = new VideoInfoPost();
				updateVideoInfoPost.setStatus(VideoStatusEnum.STATUS1.getStatus());
				videoInfoPostMapper.updateByVideoId(updateVideoInfoPost, videoInfoFilePost.getVideoId());
				return;
			}
			filePostQuery.setTransferResult(VideoFileTransferResultEnum.TRANSFER.getStatus());
			Long transferCount = videoInfoFilePostMapper.selectCount(filePostQuery);
			if (transferCount == 0L) {
				Integer duration = videoInfoFilePostMapper.sumDuration(videoInfoFilePost.getVideoId());
				VideoInfoPost updateVideoInfoPost = new VideoInfoPost();
				updateVideoInfoPost.setStatus(VideoStatusEnum.STATUS2.getStatus());
				updateVideoInfoPost.setDuration(duration);
				videoInfoPostMapper.updateByVideoId(updateVideoInfoPost, videoInfoFilePost.getVideoId());
			}
		}
	}

	private void convertVideo2Ts(String completeVideo) throws BusinessException {
		File videoFile = new File(completeVideo);
		File tsFolder = videoFile.getParentFile();
		String codec = fFmpegUtils.getVideoCodec(completeVideo);
		if (Constants.VIDEO_CODE_HEVC.equals(codec)) {//如果编码为hevc,则转为Mp4
			String tempFileName = completeVideo + Constants.VIDEO_CODE_TEMP_FILE_SUFFIX;
			new File(completeVideo).renameTo(new File(tempFileName));
			fFmpegUtils.convertHevc2Mp4(tempFileName, completeVideo);
			new File(tempFileName).delete();
		}
		fFmpegUtils.convertVideo2Ts(tsFolder, completeVideo);
		videoFile.delete();
	}

	private void union(String dirPath, String toFilePath, Boolean delSource) throws BusinessException {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			throw new BusinessException("目录不存在");
		}
		File[] fileList = dir.listFiles();
		File targetFile = new File(toFilePath);
		try {
			RandomAccessFile writeFile = new RandomAccessFile(targetFile, "rw");
			byte[] b = new byte[1024 * 10];
			for (int i = 0; i < fileList.length; i++) {
				int len = -1;
				//创建读块文件的对象
				File chunkFile = new File(dirPath + File.separator + i);
				RandomAccessFile readFile = null;
				try {
					readFile = new RandomAccessFile(chunkFile, "r");
					while ((len = readFile.read(b)) != -1) {
						writeFile.write(b, 0, len);
					}
				} catch (Exception e) {
					throw new BusinessException("合并文件失败");
				} finally {
					readFile.close();
				}
			}
		} catch (Exception e) {
			throw new BusinessException("合并文件" + dirPath + "出错了");
		} finally {
			if (delSource) {
				for (int i = 0; i < fileList.length; i++) {
					fileList[i].delete();
				}
			}
		}
	}
}