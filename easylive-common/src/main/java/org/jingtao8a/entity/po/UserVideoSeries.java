package org.jingtao8a.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
@Description:用户视频序列归档
@Date:2024-12-19
*/
@Data
@ToString
public class UserVideoSeries implements Serializable {
	/**
	 * 列表ID
	*/
	private Integer seriesId;
	/**
	 * 列表名称
	*/
	private String seriesName;
	/**
	 * 描述
	*/
	private String seriesDescription;
	/**
	 * 用户ID
	*/
	private String userId;
	/**
	 * 排序
	*/
	private Integer sort;
	/**
	 * 更新时间
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	private String cover;
}