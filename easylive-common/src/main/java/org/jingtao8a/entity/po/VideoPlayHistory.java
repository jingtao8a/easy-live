package org.jingtao8a.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
@Description:视频播放历史
@Date:2024-12-25
*/
@Data
@ToString
public class VideoPlayHistory implements Serializable {
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
	 * 最后更新时间
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;
}