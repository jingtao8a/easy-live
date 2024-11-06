package org.jingtao8a.web.controller;
import java.util.List;

import com.wf.captcha.ArithmeticCaptcha;
import org.jingtao8a.entity.po.UserInfo;
import org.jingtao8a.entity.query.UserInfoQuery;
import org.jingtao8a.service.UserInfoService;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
/**
* 用户信息 Controller
*/

@RestController
public class AccountController extends ABaseController {

	@Resource
	private UserInfoService userInfoService;

	@RequestMapping("/checkCode")
	public ResponseVO checkCode(HttpSession session) {
		ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(100, 42);
		String code = arithmeticCaptcha.text();
		session.setAttribute("checkCode", code);
		String checkCodeBase64 = arithmeticCaptcha.toBase64();
		return getSuccessResponseVO(checkCodeBase64);
	}

	@RequestMapping("/register")
	public ResponseVO register(HttpSession session, String checkCode) {
		String myCheckCode = (String) session.getAttribute("checkCode");
		return getSuccessResponseVO(myCheckCode.equalsIgnoreCase(checkCode));
	}
}