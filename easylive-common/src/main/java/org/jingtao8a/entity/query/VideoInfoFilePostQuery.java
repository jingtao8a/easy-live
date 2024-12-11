package org.jingtao8a.entity.query;
import lombok.Data;
import lombok.ToString;
/**
@Description:视频文件信息
@Date:2024-12-11
*/
@Data
@ToString
public class VideoInfoFilePostQuery extends BaseQuery {
	/**
	 * 文件唯一ID
	*/
	private String fileId;
	private String fileIdFuzzy;

	/**
	 * 上传ID
	*/
	private String uploadId;
	private String uploadIdFuzzy;

	/**
	 * 用户ID
	*/
	private String userId;
	private String userIdFuzzy;

	/**
	 * 视频ID
	*/
	private String videoId;
	private String videoIdFuzzy;

	/**
	 * 文件索引
	*/
	private Integer fileIndex;
	/**
	 * 文件名
	*/
	private String fileName;
	private String fileNameFuzzy;

	/**
	 * 文件大小
	*/
	private Long fileSize;
	/**
	 * 文件路径
	*/
	private String filePath;
	private String filePathFuzzy;

	/**
	 * 0:无更新 1:有更新
	*/
	private Integer updateType;
	/**
	 * 0:转码中 1:转码成功 2:转码失败
	*/
	private Integer transferResult;
	/**
	 * 持续时间(seconds)
	*/
	private Integer duration;
}