package org.jingtao8a.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
@Description:用户关注表
@Date:2024-12-19
*/
@Data
@ToString
public class UserFocus implements Serializable {
	/**
	 * 用户ID
	*/
	private String userId;
	/**
	 * 被关注用户ID
	*/
	private String focusUserId;
	/**
	 * 关注时间
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date focusTime;

	private String otherNickName;

	private String otherUserId;

	private String otherPersonIntroduction;

	private String otherAvatar;

	private Integer focusType;//为1表示互粉
}