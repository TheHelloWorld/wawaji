package com.lzg.wawaji.utils;

import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.constants.BaseConstant;

public class JSONUtil {

    private JSONUtil() {

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
     * @param totalCount 总记录数
     * @param pageSize 每页数据数
     * @return
     */
    public static String getTotalCountAndPageSize(int totalCount, int pageSize) {

        JSONObject json = new JSONObject();

        json.put("is_success", BaseConstant.SUCCESS);
        json.put("totalCount", totalCount);
        json.put("pageSize", pageSize);

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
