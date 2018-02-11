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
	public static final String SYSTEM_ERROR = "系统升级维护中,请稍候重试";

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
	public static final String V_CODE_ERR_MSG = "验证码错误,请重试";

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
	public static final String DEDUCTION_COIN_FAIL = "扣除马桶币失败,请新操作";

	/**
	 * 游戏币不足提示
	 */
	public static final String NOT_ENOUGH_COIN = "当前马桶币不足,请充值";

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
	 * 放入cookie中的key
	 */
	public static final String COOKIE_USER_NO = "toilet_cat_user_no";

	/**
	 * 用户邀请码redis key
	 */
	public static final String USER_INVITATION_CODE = "user_invitation_code";

	/**
	 * 充值返回结果(成功)
	 */
	public static final String RECHARGE_RESULT_TRADE_STATUS = "TRADE_SUCCESS";

	/**
	 * 后台登录用户名
	 */
	public static final String BACK_USER_NAME = "toiletCat_Back_Admin_Root_Name";

	/**
	 * 后台登录密码
	 */
	public static final String BACK_USER_PASSWORD = "asdkljfhalksjvoiualknxcvlkj123lsjchvlkjasdf";

	/**
	 * 默认用户邀请码用户编号
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

	/**
	 * http get 请求
	 */
	public static final String HTTP_GET = "GET";

	/**
	 * http post 请求
	 */
	public static final String HTTP_POST = "POST";

	/**
	 * 默认头像标识
	 */
	public static final String DEFAULT_HEAD_FLAG = "default_head_flag";

	/**
	 * 默认用户名标识
	 */
	public static final String DEFAULT_NAME_FLAG = "default_name_flag";

	/**
	 * 默认为空的值
	 */
	public static final String DEFAULT_NULL = "0";

}
