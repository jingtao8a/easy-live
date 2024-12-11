package org.jingtao8a.entity.po;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
/**
@Description:视频文件信息
@Date:2024-12-11
*/
@Data
@ToString
public class VideoInfoFile implements Serializable {
	/**
	 * 文件唯一ID
	*/
	private String fileId;
	/**
	 * 用户ID
	*/
	private String userId;
	/**
	 * 视频ID
	*/
	private String videoId;
	/**
	 * 文件索引
	*/
	private Integer fileIndex;
	/**
	 * 文件名
	*/
	private String fileName;
	/**
	 * 文件大小
	*/
	private Long fileSize;
	/**
	 * 文件路径
	*/
	private String filePath;
	/**
	 * 持续时间(seconds)
	*/
	private Integer duration;
}