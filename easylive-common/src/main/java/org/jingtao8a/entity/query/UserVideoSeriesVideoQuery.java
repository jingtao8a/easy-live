package org.jingtao8a.entity.query;
import lombok.Data;
import lombok.ToString;
/**
@Description:用户视频序列归档视频
@Date:2024-12-19
*/
@Data
@ToString
public class UserVideoSeriesVideoQuery extends BaseQuery {
	/**
	 * 列表ID
	*/
	private Integer seriesId;
	/**
	 * 视频ID
	*/
	private String videoId;
	private String videoIdFuzzy;

	/**
	 * 用户ID
	*/
	private String userId;
	private String userIdFuzzy;

	/**
	 * 排序
	*/
	private Integer sort;

	private Boolean queryVideoInfo;
}