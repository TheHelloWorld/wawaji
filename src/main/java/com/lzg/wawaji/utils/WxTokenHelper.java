package com.lzg.wawaji.utils;

import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.bean.AccessToken;
import com.lzg.wawaji.bean.WxUserInfo;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WxTokenHelper {

	private static final Logger logger = LoggerFactory.getLogger(WxTokenHelper.class);

	private static final String access_token_url =
			"https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type" +
					"=authorization_code";

	private static final String get_userInfo_url = "https://api.weixin.qq" +
			".com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	private volatile static AccessToken accessToken = null;

	/**
	 * 设置链接超时时间
	 **/
	private static final int timeout = 15000;


	private static AccessToken getWxAccessToken(String code) {
		accessToken = null;

		String requestUrl = access_token_url.replace("APPID", "wxa25ba776b86f29c7")
				.replace("SECRET", "347a665a2381b5f24df725b26a93a1d8")
				.replace("CODE", code);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setOpenid(jsonObject.getString("openid"));
				accessToken.setExpiresIn(jsonObject.getString("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				logger.error("token获取失败");

			}
		}
		return accessToken;
	}

	private static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;

		StringBuilder builder = new StringBuilder();
		try {

			URL url = new URL(requestUrl);

			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			// 设置链接超时时间,默认15s
			httpUrlConn.setConnectTimeout(timeout);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}


			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			try (InputStream inputStream = httpUrlConn.getInputStream();
			     InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			     BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
				String str;
				while ((str = bufferedReader.readLine()) != null) {
					builder.append(str);
				}
			} catch (Exception e1) {
				logger.error(BaseConstant.LOG_ERR_MSG + " httpRequest steam error:" + e1, e1);
			}

			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(builder.toString());
		} catch (ConnectTimeoutException cte) {
			logger.error(BaseConstant.LOG_ERR_MSG + " WeiXin server connection timed out");
		} catch (ConnectException ce) {
			logger.error(BaseConstant.LOG_ERR_MSG + " WeiXin server connection error:" + ce, ce);
		} catch (Exception e) {
			logger.error(BaseConstant.LOG_ERR_MSG + " httpRequest error:" + e, e);
		}
		return jsonObject;
	}

	/**
	 * 根据用户微信code获取用户微信信息
	 *
	 * @param code 微信code
	 * @return
	 */
	public static WxUserInfo getUserInfo(String code) {

		if (StringUtils.isBlank(code)) {
			logger.warn("微信code为空");
			return null;
		}
		accessToken = getWxAccessToken(code);
		if (accessToken != null) {
			// 拼装创建菜单的url
			String url = get_userInfo_url
					.replace("ACCESS_TOKEN", accessToken.getToken()).replace("OPENID",
							accessToken.getOpenid());
			// 调用接口创建菜单
			JSONObject jsonObject = httpRequest(url, "GET", null);

			if(jsonObject == null) {
				logger.error("{}WxTokenHelper jsonObject is null",BaseConstant.LOG_ERR_MSG);
				return null;
			}

			logger.info("WxTokenHelper jsonObject:{}", jsonObject.toString());
			WxUserInfo wui = new WxUserInfo();
			wui.setOpenId(jsonObject.optString("openid"));
			wui.setNickName(jsonObject.optString("nickname"));
			wui.setUnionId(jsonObject.optString("unionid"));

			return wui;

		}
		return null;
	}
}
