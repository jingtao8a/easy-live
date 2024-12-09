package org.jingtao8a.admin.controller;

import com.wf.captcha.ArithmeticCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.component.RedisComponent;
import org.jingtao8a.config.AppConfig;
import org.jingtao8a.constants.Constants;
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
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

/**
* 用户信息 Controller
*/

@RestController
@RequestMapping("/account")
@Validated
@Slf4j
public class AccountController extends ABaseController {

	@Resource
	private UserInfoService userInfoService;

	@Resource
	private RedisComponent redisComponent;

	@Resource
	private AppConfig appConfig;

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

	@RequestMapping("/login")
	public ResponseVO login(HttpServletRequest request, HttpServletResponse response,
							@NotEmpty String account,
							@NotEmpty String password,
							@NotEmpty String checkCodeKey,
							@NotEmpty String checkCode) throws BusinessException {
		try {
			if (!checkCode.equalsIgnoreCase(redisComponent.getCheckCode(checkCodeKey))) {
				throw new BusinessException("图片验证码错误");
			}
//			log.info("{}{}", appConfig.getAdminAccount(), appConfig.getAdminPassword());
			if (!account.equals(appConfig.getAdminAccount()) || !password.equals(StringTools.encodeByMd5(appConfig.getAdminPassword()))) {
				throw new BusinessException("用户名或密码错误");
			}
			String token = redisComponent.saveTokenInfo4Admin(account);
			saveToken2Cookie(response, token);
			return getSuccessResponseVO(account);
		} finally {
			redisComponent.cleanCheckCode(checkCodeKey);
			Cookie[] cookies = request.getCookies();
			if (null != cookies) {
				String token = null;
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(Constants.TOKEN_ADMIN)) {
						token = cookie.getValue();
						break;
					}
				}
				if (!StringTools.isEmpty(token)) {
					redisComponent.cleanToken4Admin(token);
				}
			}
		}
	}

	@RequestMapping("/logout")
	public ResponseVO logout(HttpServletResponse response) {
		cleanCookie(response);
		return getSuccessResponseVO(null);
	}
}