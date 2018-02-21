package com.toiletCat.constants;

public class RedisConstant {

    private RedisConstant() {

    }

    /**
     * redis配置文件名
     */
    public static final String REDIS = "redis";

    /**
     * 占位符
     */
    public static final String PLACEHOLDER = "#{}";

    /**
     * 充值返回结果锁超时时间
     */
    public static final Integer RECHARGE_RESULT_LOCK_TIME_OUT = 30;

    /**
     * 短信验证相关
     */
    public static final String SMS_MOBILE_NO = "sms_" + PLACEHOLDER;


    /**
     * 机器正在被使用key
     */
    public static final String MACHINE_IN_USE = "machine_" + PLACEHOLDER + "_in_use";

    /**
     * 娃娃机房间围观人数
     */
    public static final String MACHINE_ROOM_VIEWER = "machine_room_viewer_" + PLACEHOLDER;

    /**
     * 游戏房间围观人数
     */
    public static final String GAME_ROOM_VIEWER = "game_room_viewer_" + PLACEHOLDER;

    /**
     * 游戏房间真实在线人数
     */
    public static final String REAL_GAME_ROOM_VIEWER = "real_game_room_viewer_" + PLACEHOLDER;

    /**
     * 每天限制充值的key
     */
    public static final String RECHARGE_LIMIT_NUM_BY_USER = "recharge_limit_num_" + PLACEHOLDER;

    /**
     * 用户首充标志位
     */
    public static final String FIRST_RECHARGE_FLAG = "first_recharge_" + PLACEHOLDER;

    /**
     * 是首充
     */
    public static final String IS_FIRST = "is_first";

    /**
     * 非首充
     */
    public static final String IS_NOT_FIRST = "is_not_first";

    /**
     * 微信分享js ticket
     */
    public static final String WX_SHARE_JS_API_TICKET = "wx_share_js_api_ticket";

    /**
     * 微信分享js access_token
     */
    public static final String WX_SHARE_ACCESS_TOKEN = "wx_share_access_token";

    /**
     * 微信分享key过期时间
     */
    public static final Integer WX_EXPIRE_TIME = 118 *60;
}
