package com.toiletCat.constants;

public class RechargeConstant {

    private RechargeConstant() {

    }

    /**
     * 微信统一下单url
     */
    public static final String WE_CHAT_REQUEST_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 查询订单url
     */
    public static final String QUERY_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 回调通信成功返回code
     */
    public static final String SUCCESS_RETURN_CODE = "SUCCESS";

    /**
     * 回调通信失败返回code
     */
    public static final String FAIL_RETURN_CODE = "FAIL";

    /**
     * 回调处理成功返回消息
     */
    public static final String SUCCESS_RETURN_MSG = "OK";

    /**
     * 回调处理失败返回消息
     */
    public static final String FAIL_RETURN_MSG = "失败";

    /**
     * 回调签名字段key
     */
    public static final String SIGN = "sign";

    /**
     * 充值成功返回结果
     */
    public static final String SUCCESS_RESULT_CODE = "SUCCESS";

    /**
     * 充值失败返回结果
     */
    public static final String FAIL_RESULT_CODE = "FAIL";
}
