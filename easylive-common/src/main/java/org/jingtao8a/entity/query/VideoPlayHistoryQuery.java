package org.jingtao8a.entity.query;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
/**
@Description:视频播放历史
@Date:2024-12-25
*/
@Data
@ToString
public class VideoPlayHistoryQuery extends BaseQuery {
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
	 * 最后更新时间
	*/
	private Date lastUpdateTime;
	private String lastUpdateTimeStart;
	private String lastUpdateTimeEnd;

    public Boolean queryVideoDetail;
}