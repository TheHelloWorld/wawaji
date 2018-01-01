package com.toiletCat.utils;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;

public class JSONUtil {

    private JSONUtil() {

    }

    /**
     * 获得返回字符串
     * @param result 执行结果
     * @return
     */
    public static String getReturnBeanString(CommonResult result) {

        if(result.success()) {

            return getSuccessReturnJSON(result.getValue());

        } else if(result.otherMsg()) {

            return getOtherMsgJson(result.getValue());
        }

        return getErrorJson();
    }

    /**
     * 获得返回字符串
     * @param result 执行结果
     * @param str 字符串
     * @return
     */
    public static String getReturnStrString(CommonResult result, String str) {

        if(result.success()) {

            return getSuccessReturnJSON(str);

        } else if(result.otherMsg()) {

            return getOtherMsgJson(result.getValue());
        }

        return getErrorJson();
    }

    /**
     * 获得返回结果json值
     * @param obj 单个bean
     * @return
     */
    public static String getSuccessReturnJSON(Object obj) {

        JSONObject json = new JSONObject();

        json.put("is_success", BaseConstant.SUCCESS);
        json.put("result", obj);

        return json.toJSONString();
    }

    /**
     * 获得总数据数和每页数据数
     * @param result 总记录数
     * @param pageSize 每页数据数
     * @return
     */
    public static String getTotalCountAndPageSize(CommonResult<Integer> result, int pageSize) {

        if(result.success()) {

            JSONObject json = new JSONObject();

            json.put("is_success", BaseConstant.SUCCESS);
            json.put("totalCount", result.getValue());
            json.put("pageSize", pageSize);

            return json.toJSONString();
        }

        return getErrorJson();
    }

    /**
     * 其他消息json
     * @param obj 执行结果
     * @return
     */
    public static String getOtherMsgJson(Object obj) {
        JSONObject json = new JSONObject();

        json.put("is_success", BaseConstant.FAIL);
        json.put("result", String.valueOf(obj));

        return json.toJSONString();
    }

    /**
     * 获得执行失败json
     * @return
     */
    public static String getErrorJson() {

        JSONObject json = new JSONObject();

        json.put("is_success", BaseConstant.FAIL);
        json.put("result", BaseConstant.SYSTEM_ERROR);

        return json.toJSONString();
    }

}
