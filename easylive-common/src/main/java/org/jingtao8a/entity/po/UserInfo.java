package org.jingtao8a.entity.po;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
/**
@Description:用户信息
@Date:2024-11-06
*/
@Data
@ToString
public class UserInfo implements Serializable {
	/**
	 * 用户id
	*/
	private String userId;
	/**
	 * 昵称
	*/
	private String nickName;
	/**
	 * 邮箱
	*/
	private String email;
	/**
	 * 密码
	*/
	private String password;
	/**
	 * 0：女 1：男 2：未知
	*/
	private Integer sex;
	/**
	 * 出生日期
	*/
	private String birthday;
	/**
	 * 学校
	*/
	private String school;
	/**
	 * 个人简介
	*/
	private String personIntroduction;
	/**
	 * 加入时间
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date joinTime;
	/**
	 * 最后登入时间
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;
	/**
	 * 最后登入ip
	*/
	private String lastLoginIp;
	/**
	 * 0：禁用 1：正常
	*/
	private Integer status;
	/**
	 * 空间公告
	*/
	private String noticeInfo;
	/**
	 * 硬币总数量
	*/
	private Integer totalCoinCount;
	/**
	 * 当前硬币数
	*/
	private Integer currentCoinCount;
	/**
	 * 主题
	*/
	private Integer theme;
}