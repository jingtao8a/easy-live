package org.jingtao8a.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
@Description:视频弹幕
@Date:2024-12-17
*/
@Data
@ToString
public class VideoDanmu implements Serializable {
	/**
	 * 自增ID
	*/
	private Integer danmuId;
	/**
	 * 视频ID
	*/
	private String videoId;
	/**
	 * 视频文件ID
	*/
	private String fileId;
	/**
	 * 用户ID
	*/
	private String userId;
	/**
	 * 发布时间
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date postTime;
	/**
	 * 内容
	*/
	private String text;
	/**
	 * 展示位置
	*/
	private Integer mode;
	/**
	 * 颜色
	*/
	private String color;
	/**
	 * 展示时间
	*/
	private Integer time;
}