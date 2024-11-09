package org.jingtao8a.web.controller;

import com.wf.captcha.ArithmeticCaptcha;
import org.jingtao8a.component.RedisComponent;
import org.jingtao8a.constants.Constants;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.service.UserInfoService;
import org.jingtao8a.utils.StringTools;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

/**
* 用户信息 Controller
*/

@RestController
@RequestMapping("/account")
@Validated
public class AccountController extends ABaseController {

	@Resource
	private UserInfoService userInfoService;

	@Resource
	private RedisComponent redisComponent;

	@RequestMapping("/checkCode")
	public ResponseVO checkCode() {
		ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(100, 42);
		String checkCode = arithmeticCaptcha.text();

		String checkCodeKey = redisComponent.saveCheckCode(checkCode);
		String checkCodeBase64 = arithmeticCaptcha.toBase64();

		Map<String, String> result = new HashMap<>();
		result.put("checkCode", checkCodeBase64);
		result.put("checkCodeKey", checkCodeKey);

		return getSuccessResponseVO(result);
	}

	@RequestMapping("/register")
	public ResponseVO register(@NotEmpty @Email @Size(max = 150) String email,
							   @NotEmpty @Size(max = 20) String nickName,
							   @NotEmpty @Pattern(regexp = Constants.REGEX_PASSWORD) String registerPassword,
							   @NotEmpty String checkCodeKey,
							   @NotEmpty String checkCode) throws BusinessException {

		try {
			if (!checkCode.equalsIgnoreCase(redisComponent.getCheckCode(checkCodeKey))) {
				throw new BusinessException("图片验证码错误");
			}
			userInfoService.register(email, nickName, registerPassword);
			return getSuccessResponseVO(null);
		} finally {
			redisComponent.cleanCheckCode(checkCodeKey);
		}
	}

	@RequestMapping("/login")
	public ResponseVO login(HttpServletRequest request, HttpServletResponse response,
							@NotEmpty @Email @Size(max = 150) String email,
							@NotEmpty String password,
							@NotEmpty String checkCodeKey,
							@NotEmpty String checkCode) throws BusinessException {
		try {
			if (!checkCode.equalsIgnoreCase(redisComponent.getCheckCode(checkCodeKey))) {
				throw new BusinessException("图片验证码错误");
			}
			String ip = getIpAddr();
			TokenUserInfoDto tokenUserInfoDto = userInfoService.login(email, password, ip);
			saveToken2Cookie(response, tokenUserInfoDto.getToken());
			// TODO 设置 粉丝数 关注数 硬币数
			return getSuccessResponseVO(tokenUserInfoDto);
		} finally {
			redisComponent.cleanCheckCode(checkCodeKey);
			Cookie[] cookies = request.getCookies();
			String token = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Constants.TOKEN_WEB)) {
					token = cookie.getValue();
					break;
				}
			}
			if (!StringTools.isEmpty(token)) {
				redisComponent.cleanToken(token);
			}
		}
	}

	@RequestMapping("/autoLogin")
	public ResponseVO autoLogin(HttpServletResponse response) throws BusinessException {
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
		if (null == tokenUserInfoDto) {
			return getSuccessResponseVO(null);
		}

		if (tokenUserInfoDto.getExpireAt() - System.currentTimeMillis() < Constants.REDIS_KEY_EXPIRES_ONE_DAY) {//只剩一天时间就要过期了
			redisComponent.cleanToken(tokenUserInfoDto.getToken());//删除旧的 token
			redisComponent.saveTokenInfo(tokenUserInfoDto);//重新存储tokenUserInfoDto，更新生命周期，使用新的token
			saveToken2Cookie(response, tokenUserInfoDto.getToken());
		}
		// TODO 设置 粉丝数 关注数 硬币数
		return getSuccessResponseVO(tokenUserInfoDto);
	}

	@RequestMapping("/logout")
	public ResponseVO logout(HttpServletResponse response) {
		cleanCookie(response);
		return getSuccessResponseVO(null);
	}
}