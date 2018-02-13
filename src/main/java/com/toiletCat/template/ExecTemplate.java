package com.toiletCat.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.enums.CommonCodeMessage;
import org.slf4j.Logger;

public final class ExecTemplate {

    /**
     * 公共执行方法
     * @param callback 回调函数
     * @param logger 日志
     * @param checkFlag 是否校验参数标志位
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static CommonResult exec(Callback callback, Logger logger, Boolean checkFlag, String method, Object params) {

        String inputParam = JSON.toJSONString(params, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty);

        logger.info(method + " start params: " + inputParam);

        CommonResult commonResult = new CommonResult();

        try {
            callback.setResult(commonResult);

            // 参数校验
            if(!checkParam(checkFlag, JSONObject.parseObject(inputParam), logger, method)) {

                callback.setCodeMessage(CommonCodeMessage.PARAM_ERROR);

                return callback.getResult();
            }

            callback.exec();

        } catch (Throwable t) {

            logger.error(BaseConstant.LOG_ERR_MSG +" "+ method + " params: " + inputParam + " error: " + t.getMessage(),
                    t);

            callback.setCodeMessage(CommonCodeMessage.SYSTEM_ERROR);
        }

        logger.info(method + " end");

        return callback.getResult();
    }

    /**
     * 检查参数是否为空
     * @param checkFlag 是否做检查标志位
     * @param json 参数
     * @param logger 日志
     * @param method 方法
     * @return
     */
    private static Boolean checkParam(Boolean checkFlag, JSONObject json, Logger logger, String method) {

        if(!checkFlag) {

            return true;
        }

        for(String key : json.keySet()) {

            if(json.get(key) == null) {

                logger.error(method + " param " + key + " is null");

                return false;
            }
        }

        return true;
    }


}
