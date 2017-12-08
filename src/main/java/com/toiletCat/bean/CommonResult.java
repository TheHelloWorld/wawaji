package com.toiletCat.bean;

import com.toiletCat.enums.CommonCodeMessage;
import com.toiletCat.interfaces.ICodeMessage;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * @author ChenCe
 */
public class CommonResult<T>  implements ICodeMessage, Serializable {

    private static final long serialVersionUID = 1L;


    // FIELDS
    private String code = CommonCodeMessage.SUCCESS.code;

    private String message = "操作成功";


    public T value;


    // CONSTRUCTORS
    public CommonResult() {
    }

    public CommonResult(ICodeMessage codeMessage) {
        this.setCodeMessage(codeMessage);
    }


    public void setCodeMessage(ICodeMessage codeMessage) {
        this.setCode(codeMessage.getCode());
        this.setMessage(codeMessage.getMessage());
    }

    // sugars
    public boolean isSysError() {
        return codeEquals(CommonCodeMessage.SYSTEM_ERROR);
    }

    public boolean success() {
        return codeEquals(CommonCodeMessage.SUCCESS);
    }

    public boolean codeEquals(ICodeMessage codeMessage) {
        return StringUtils.equals(code, codeMessage.getCode());
    }

    // BEAN METHODS


    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }


}
