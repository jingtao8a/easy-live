package org.jingtao8a.entity.query;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
/**
@Description:视频弹幕
@Date:2024-12-17
*/
@Data
@ToString
public class VideoDanmuQuery extends BaseQuery {
	/**
	 * 自增ID
	*/
	private Integer danmuId;
	/**
	 * 视频ID
	*/
	private String videoId;
	private String videoIdFuzzy;

	/**
	 * 视频文件ID
	*/
	private String fileId;
	private String fileIdFuzzy;

	/**
	 * 用户ID
	*/
	private String userId;
	private String userIdFuzzy;

	/**
	 * 发布时间
	*/
	private Date postTime;
	private String postTimeStart;
	private String postTimeEnd;

	/**
	 * 内容
	*/
	private String text;
	private String textFuzzy;

	/**
	 * 展示位置
	*/
	private Integer mode;
	/**
	 * 颜色
	*/
	private String color;
	private String colorFuzzy;

	/**
	 * 展示时间
	*/
	private Integer time;
}