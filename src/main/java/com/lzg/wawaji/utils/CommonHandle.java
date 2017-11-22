package com.lzg.wawaji.utils;


import com.lzg.wawaji.constants.BaseConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一些公用操作方法类
 *
 * @ClassName: CommonHandle
 * @description
 */
public class CommonHandle {

	private CommonHandle() {

	}

	/**
	 * 获得session，可能为null
	 *
	 * @return HttpSession
	 */
	public static HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);
	}

	/**
	 * 获得session，如果没有session，则新创建一个
	 *
	 * @return HttpSession
	 */
	public static HttpSession getSessionNew() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);
	}

	/**
	 * 获得httpRequest
	 *
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获得httpRequest
	 *
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	/**
	 * 存储当前登录管理员ID
	 *
	 * @param managerId 管理员id
	 */
	public static void setManagerId(String managerId) {
		getSessionNew().setAttribute(BaseConstant.MANAGER_ID, managerId);
	}


	/**
	 * 获得当前登录管理员的ID
	 *
	 * @return String
	 */
	public static String getManagerId() {
		HttpSession session = getSession();
		if (session == null) {
			return null;
		} else {
			return (String) session.getAttribute(BaseConstant.MANAGER_ID);
		}
	}


	/**
	 * 注销（干掉所有session）
	 *
	 * @return boolean  true:注销成功，false注销失败，可能无session
	 */
	public static boolean removeAllSession() {
		HttpSession session = getSession();
		if (session == null) {
			return false;
		} else {
			session.invalidate();
			return true;
		}
	}

	/**
	 * 去掉内容中的html标签
	 *
	 * @param content
	 * @return String
	 */
	public static String delHtml(String content) {
		if (content == null) {
			return null;
		}
		Pattern p = Pattern.compile("(<\\s*([^>]*)\\s*>)|(&nbsp;)|(&rdquo;)|(&ldquo;)");
		Matcher m = p.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}


	/*********************************cookie操作 开始*********************************************/

	/**
	 * 设置cookie中的值
	 *
	 * @param key    存储的key
	 * @param value  要存储的值
	 * @param maxAge cookie最大可用时间
	 *               void
	 */
	public static void setCookieValue(String key, String value, Integer maxAge) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().addCookie(cookie);
	}


	/**
	 * 获得cookie中的值
	 *
	 * @param key 存储的key
	 * @return String
	 */
	public static String getCookieValue(String key) {
		Cookie[] cookies = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(key)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 存储当前登录管理员ID，存放到cookie里
	 *
	 * @param managerId 管理员id
	 * @param maxAge    cookie时间,单位:秒
	 */
	public static void setManagerIdCookie(String managerId, int maxAge) {
		setCookieValue(BaseConstant.MANAGER_ID, managerId, maxAge);
	}


	/**
	 * 获得当前登录管理员的ID
	 *
	 * @return String
	 */
	public static String getManagerIdCookie() {
		return getCookieValue(BaseConstant.MANAGER_ID);
	}

	/*********************************cookie操作 结束*********************************************/
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}

}
