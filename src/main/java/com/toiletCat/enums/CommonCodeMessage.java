package com.toiletCat.enums;

import com.toiletCat.interfaces.ICodeMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ChenCe
 */
public enum CommonCodeMessage implements ICodeMessage {

    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常"),
    OTHER_MESSAGE("OTHER_MESSAGE", "其他信息"),
    SUCCESS("SUCCESS", "成功"),

    ;

    public String code;
    public String message;


    CommonCodeMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }



    private static Map<String, CommonCodeMessage> mapping = new HashMap<>();

    static {
        for (CommonCodeMessage codeMessage : CommonCodeMessage.values()) {
            mapping.put(codeMessage.getCode(), codeMessage);
        }
    }

    public static CommonCodeMessage parse(String val) {
        return mapping.get(val);
    }



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
}
