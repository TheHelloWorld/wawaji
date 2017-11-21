package com.lzg.wawaji.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.constants.BaseConstant;


public class JSONUtil {

    private JSONUtil() {

    }

    /**
     * 获得分页返回值
     * @param jsonArray 查询结果list
     * @param totalCount 总页数
     * @param pageSize 分页数量
     * @return
     */
    public static String getQueryAllByPage(JSONArray jsonArray, int totalCount, int pageSize) {

        JSONObject json = new JSONObject();

        json.put("result", BaseConstant.SUCCESS);
        json.put("list", jsonArray);
        json.put("totalCount", totalCount);
        json.put("pageSize", pageSize);

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

        json.put("result", BaseConstant.SUCCESS);
        json.put("totalCount", totalCount);
        json.put("pageSize", pageSize);

        return json.toJSONString();

    }

    public static String getErrorJson() {

        JSONObject json = new JSONObject();

        json.put("result", BaseConstant.FAIL);

        return json.toJSONString();
    }
}
