package com.toiletCat.constants;

import com.toiletCat.entity.MoneyForCoin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基本静态常量
 *
 * @author liuzikun
 */
public class BaseConstant {

	/**
	 * 占位符
	 */
	public static final String PLACEHOLDER = "#{}";

	/**
	 * 管理员id
	 */
	public static final String MANAGER_ID = "managerId";

	/**
	 * 验证码
	 */
	public static final String VALIDATE_CODE = "vcode";

	/**
	 * 手机验证码
	 */
	public static final String TICKET = "ticket";

	/**
	 * 成功标志
	 **/
	public static final String SUCCESS = "success";

	/**
	 * 失败标志
	 **/
	public static final String FAIL = "fail";

	/**
	 * 抓取失败
	 */
	public static final String CATCH_FAIL = "catch fail";

	/**
	 * 抓取成功
	 */
	public static final String CATCH_SUCCESS = "catch success";

	/**
	 * 系统错误
	 */
	public static final String SYSTEM_ERROR = "系统升级维护中,请稍候";

	/**
	 * 马桶猫
	 */
	public static final String TOILER_CAT = "ToiletCat";

	/**
	 * 普通log
	 **/
	public static final String LOG_MSG = "toiletCat msg:";

	/**
	 * 异常日志log key
	 **/
	public static final String LOG_ERR_MSG = "toiletCat errMsg:";

	/**
	 * 验证码异常信息
	 **/
	public static final String VCODE_ERR_MSG = "验证码错误,请重试";

	/**
	 * 获取session异常
	 **/
	public static final String SESSION_ERR_MSG = "系统升级维护中,请稍候重试";

	/**
	 * 默认每页数据数
	 */
	public static final Integer DEFAULT_PAGE_SIZE = 10;

	/**
	 * 用户游戏币提示
	 */
	public static final String DEDUCTION_COIN_FAIL = "扣除游戏币失败,请新操作";

	/**
	 * 游戏币不足提示
	 */
	public static final String NOT_ENOUGH_COIN = "当前游戏币不足,请充值";

	/**
	 * 用户最大地址数
	 */
	public static final Integer MAX_USER_ADDRESS = 5;

	/**
	 * 地址过多提示语
	 */
	public static final String OVER_MAX_USER_ADDRESS = "地址最多只能填5个";

	/**
	 * 机器被占用提示语
	 */
	public static final String MACHINE_ALREADY_IN_UES = "机器已被占用";

	/**
	 * 用户游戏房间最大幸运值
	 */
	public static final Integer MAX_USER_ROOM_LUCKY_NUM = 100;

	/**
	 * redis配置文件名
	 */
	public static final String REDIS = "redis";

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
	 * 放入cookie中的key
	 */
	public static final String COOKIE_USER_NO = "toilet_cat_user_no";

	/**
	 * 用户邀请码redis key
	 */
	public static final String USER_INVITATION_CODE = "user_invitation_code";

	/**
	 * 短信验证相关
	 */
	public static final String SMS_MOBILE_NO = "sms_" + PLACEHOLDER;

	/**
	 * 充值返回结果(成功)
	 */
	public static final String RECHARGE_RESULT_TRADE_STATUS = "TRADE_SUCCESS";

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

	/**
	 * 后台登录用户名
	 */
	public static final String BACK_USER_NAME = "toiletCat_Back_Admin_Root_Name";

	/**
	 * 后台登录密码
	 */
	public static final String BACK_USER_PASSWORD = "asdkljfhalksjvoiualknxcvlkj123lsjchvlkjasdf";

	/**
	 * 默认用户邀请码
	 */
	public static final String DEFAULT_INVITATION_USER_NO = "0";

	/**
	 * 最大邀请数量
	 */
	public static final Integer INVITE_MAX_NUM = 10;

	/**
	 * 钱和游戏币对应关系map
	 */
	public static Map<Double, MoneyForCoin> moneyForCoinMap = new LinkedHashMap<>();

}
