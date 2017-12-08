package com.toiletCat.utils;

import com.toiletCat.constants.BaseConstant;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpSession;

public class Validate {

	/**
	 * 检验图片验证码
	 *
	 * @param vCode 图片验证码
	 * @return
	 */
	public static String validateVCode(String vCode) {
		if (StringUtils.isBlank(vCode)) {
			return "请填写图片验证码";
		}
		HttpSession session = CommonHandle.getSession();
		if (session == null) {
			return BaseConstant.SESSION_ERR_MSG;
		}

		String obj = (String) session.getAttribute(BaseConstant.VALIDATE_CODE);

		if (vCode.equalsIgnoreCase(obj)) {
			return BaseConstant.SUCCESS;
		}
		return "图片验证码错误";
	}

	/**
	 * 验证码手机验证码
	 *
	 * @param ticket 手机验证码
	 * @return
	 */
	public static String validateTicket(String ticket) {
		if (StringUtils.isBlank(ticket)) {
			return "请填写验证码";
		}
		HttpSession session = CommonHandle.getSession();
		if (session == null) {
			return BaseConstant.SESSION_ERR_MSG;
		}

		String obj = (String) session.getAttribute(BaseConstant.TICKET);

		if (ticket.equalsIgnoreCase(obj)) {
			return BaseConstant.SUCCESS;
		}
		return "验证码错误";
	}

}
