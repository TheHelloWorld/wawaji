package com.toiletCat.constants;

/**
 * 基本静态常量
 *
 * @author 董凡东
 */
public class BaseConstant {

	//************************* session中存放的变量名 *********************************
	/**
	 * 用户id
	 */
	public static final String USER_ID = "userId";

	public static final String REASON = "reason";

	public static final String USER_INFO = "user_info";

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

	// ******** 没有权限的struts2返回结果，需要在struts.xml中配置  ******************
	/**
	 * 没有登录
	 */
	public static final String UNLOGIN = "unLogin";

	/**
	 * 异常信息
	 **/
	public static final String ERROR_MAG = "服务器维护中,请稍后重试";

	/**
	 * 默认用户首次去向
	 **/
	public static final Long DEDAULT_FIRST_TARGET = -1L;

	/**
	 * 用户信息已存在
	 **/
	public static final String USER_INFO_REPEAT = "用户信息已提交,请勿重复提交";

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
	 * 普通log
	 **/
	public static final String LOG_MSG = "wawaji msg:";

	/**
	 * 异常日志log key
	 **/
	public static final String LOG_ERR_MSG = "wawaji errMsg:";

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
	public static final Integer DEFAULT_PAGE_SIZE = 15;

	/**
	 * 用户游戏币提示
	 */
	public static final String DEDUCTION_COIN_FAIL = "扣除游戏币失败,请新操作";

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
	 * 游戏房间被占用提示语
	 */
	public static final String GAME_ROOM_ALREADY_IN_UES = "游戏房间已被占用";

	/**
	 * 用户游戏房间最大幸运值
	 */
	public static final Integer MAX_USER_ROOM_LUCKY_NUM = 50;

	/**
	 * 游戏房间幸运累加值
	 */
	public static Integer GAME_ROOM_LUCKY_ADD_NUM = 1;

	/**
	 * redis配置文件名
	 */
	public static final String REDIS = "redis";

	/**
	 * 机器正在被使用key
	 */
	public static final String MACHINE_IN_USE = "machine_#{}_in_use";

	/**
	 * 娃娃机房间围观人数
	 */
	public static final String MACHINE_ROOM_VIEWER = "machine_room_viewer_#{}";

	/**
	 * 游戏房间围观人数
	 */
	public static final String GAME_ROOM_VIEWER = "game_room_viewer_#{}";

	public static final String COOKIE_USER_NO = "toilet_cat_user_no";

}
