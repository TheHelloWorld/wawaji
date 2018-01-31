package com.toiletCat.constants;

import java.util.HashMap;
import java.util.Map;

public class ConfigConstant {

    private ConfigConstant() {

    }

    /**
     * 配置文件Map
     */
    public static Map<String,String> configMap = new HashMap<>();

    /**
     * 用户默认游戏币
     */
    public static final String USER_DEFAULT_COIN = "user_default_coin";

    /**
     * 用户邀请获得游戏币数
     */
    public static final String USER_INVITE_COIN = "user_invite_coin";

    /**
     * 几件以上包邮
     */
    public static final String FREE_DELIVER_NUM = "free_deliver_num";

    /**
     * 用户战利品邮费
     */
    public static final String USER_DELIVER_COIN = "user_deliver_coin";

    /**
     * 到达阈值前用户幸运值添加随机数上限
     */
    public static final String USER_LUCKY_NUM_BEFORE_THRESHOLD = "user_lucky_num_before_threshold";

    /**
     * 用户幸运值阈值
     */
    public static final String USER_LUCKY_NUM_THRESHOLD = "user_lucky_num_threshold";

    /**
     * 到达阈值后用户幸运值添加随机数上限
     */
    public static final String USER_LUCKY_NUM_AFTER_THRESHOLD = "user_lucky_num_after_threshold";

    /**
     * 特殊用户编号集合(以,分割)
     */
    public static final String VIP_USER_NO_LIST = "vip_user_no_list";

    /**
     * 特殊用户用户游戏房间增长值
     */
    public static final String VIP_USER_GAME_ROOM_ADD_NUM = "vip_user_game_room_add_num";

//	/**
//	 * 重置游戏房间幸运值下限(上限为下限+10以内的一个数)
//	 */
//	public static final String RESET_GAME_ROOM_LUCKY_NUM = "reset_game_room_lucky_num";
}
