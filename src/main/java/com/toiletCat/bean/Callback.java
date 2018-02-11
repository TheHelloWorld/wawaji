package com.toiletCat.bean;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.enums.CommonCodeMessage;
import com.toiletCat.interfaces.ICodeMessage;
import org.slf4j.Logger;

public abstract class Callback<T> {

    private CommonResult result;

    private Object arg;


    // METHODS
    public abstract void exec();

    public void setCodeMessage(ICodeMessage codeMessage) {
        this.result.setCodeMessage(codeMessage);
    }


    public void respondTo(ICodeMessage codeMessage) {
        setCodeMessage(codeMessage);
    }

    public void respondSysError() {
        setCodeMessage(CommonCodeMessage.SYSTEM_ERROR);
    }

    public void setOtherMsg() {
        setCodeMessage(CommonCodeMessage.OTHER_MESSAGE);
    }


    public void got(T obj) {
        result.setValue(obj);
    }

    /**
     * 检查参数是否为空
     * @param checkFlag 是否做检查标志位
     * @param json 参数
     * @param logger 日志
     * @param method 方法
     * @return
     */
    public Boolean checkParam(Boolean checkFlag, JSONObject json, Logger logger, String method) {

        if(!checkFlag) {

            return true;
        }

        for(String key : json.keySet()) {

            if(json.get(key) == null) {

                logger.error(method + "param " + key + " is null");

                return false;
            }
        }

        return true;
    }
    // BEAN METHODS


    public Object getArg() {
        return arg;
    }

    public void setArg(Object arg) {
        this.arg = arg;
    }

    public CommonResult getResult() {
        return result;
    }

    public void setResult(CommonResult result) {
        this.result = result;
    }

}
