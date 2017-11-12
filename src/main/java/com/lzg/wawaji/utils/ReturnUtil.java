package com.lzg.wawaji.utils;

import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.constants.BaseConstant;

public class ReturnUtil {

	/**
	 * 返回检查用户信息是否存在json
	 *
	 * @param userId 用户Id
	 * @return
	 */
	public static String getReturnCheckJson(Long userId) {
		JSONObject returnJson = new JSONObject();
		returnJson.put(BaseConstant.SUCCESS, true);
		returnJson.put(BaseConstant.USER_ID, userId);
		returnJson.put(BaseConstant.USER_INFO, false);
		return returnJson.toJSONString();
	}

	/**
	 * 返回错误Json
	 *
	 * @return
	 */
	public static String getReturnErrJson() {
		JSONObject returnJson = new JSONObject();
		returnJson.put(BaseConstant.REASON, BaseConstant.ERROR_MAG);
		returnJson.put(BaseConstant.SUCCESS, false);
		return returnJson.toJSONString();
	}

	/**
	 * 获得返回原因Json
	 *
	 * @param reason 原因
	 * @return
	 */
	public static String getReasonReturnJson(String reason) {
		JSONObject returnJson = new JSONObject();
		returnJson.put(BaseConstant.REASON, reason);
		returnJson.put(BaseConstant.SUCCESS, false);
		return returnJson.toJSONString();
	}

}
