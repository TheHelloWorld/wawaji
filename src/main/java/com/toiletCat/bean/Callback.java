package com.toiletCat.bean;

import com.toiletCat.enums.CommonCodeMessage;
import com.toiletCat.interfaces.ICodeMessage;

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
