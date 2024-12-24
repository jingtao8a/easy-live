package org.jingtao8a.entity.query;
import lombok.Data;
import lombok.ToString;
import java.util.Date;
import java.util.List;

/**
@Description:用户信息
@Date:2024-11-06
*/
@Data
@ToString
public class UserInfoQuery extends BaseQuery {
	/**
	 * 用户id
	*/
	private String userId;
	private String userIdFuzzy;

	/**
	 * 昵称
	*/
	private String nickName;
	private String nickNameFuzzy;

	/**
	 * 邮箱
	*/
	private String email;
	private String emailFuzzy;

	/**
	 * 密码
	*/
	private String password;
	private String passwordFuzzy;

	/**
	 * 0：女 1：男 2：未知
	*/
	private Integer sex;
	/**
	 * 出生日期
	*/
	private String birthday;
	private String birthdayFuzzy;

	/**
	 * 学校
	*/
	private String school;
	private String schoolFuzzy;

	/**
	 * 个人简介
	*/
	private String personIntroduction;
	private String personIntroductionFuzzy;

	/**
	 * 加入时间
	*/
	private Date joinTime;
	private String joinTimeStart;
	private String joinTimeEnd;

	/**
	 * 最后登入时间
	*/
	private Date lastLoginTime;
	private String lastLoginTimeStart;
	private String lastLoginTimeEnd;

	/**
	 * 最后登入ip
	*/
	private String lastLoginIp;
	private String lastLoginIpFuzzy;

	/**
	 * 0：禁用 1：正常
	*/
	private Integer status;
	/**
	 * 空间公告
	*/
	private String noticeInfo;
	private String noticeInfoFuzzy;

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
	/**
	 * 头像
	 */
	private String avatar;
	private String avatarFuzzy;

	private List<String> userIdList;
}