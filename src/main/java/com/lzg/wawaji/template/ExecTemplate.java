package com.lzg.wawaji.template;

import com.alibaba.fastjson.JSON;
import com.lzg.wawaji.bean.Callback;
import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.enums.CommonCodeMessage;
import org.slf4j.Logger;

public final class ExecTemplate {

    /**
     * 公共执行方法
     * @param callback 回调函数
     * @param logger 日志
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static CommonResult exec(Callback callback, Logger logger, String method, String params) {
        logger.info(method + " start");

        CommonResult commonResult = new CommonResult();

        try {
            callback.setResult(commonResult);
            callback.exec();
        } catch (Throwable e) {
            logger.error(BaseConstant.LOG_ERR_MSG +" "+ method + " params:" + params + "error: " + e, e);
            callback.setCodeMessage(CommonCodeMessage.SYSTEM_ERROR);
        }

        logger.info("exec return:" + JSON.toJSONString(commonResult));
        logger.info(method + " end");

        return callback.getResult();
    }


}
