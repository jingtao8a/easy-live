package org.jingtao8a.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
@Description:用户消息表
@Date:2024-12-25
*/
@Data
@ToString
public class UserMessage implements Serializable {
	/**
	 * 消息ID 自增
	*/
	private Integer messageId;
	/**
	 * 用户ID
	*/
	private String userId;
	/**
	 * 视频ID
	*/
	private String videoId;
	/**
	 * 
	*/
	private Integer messageType;
	/**
	 * 发送人ID
	*/
	private String sendUserId;
	/**
	 * 0:未读 1:已读
	*/
	private Integer readType;
	/**
	 * 创建时间
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 扩展信息
	*/
	private String extendJson;
}