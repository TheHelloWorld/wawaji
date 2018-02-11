package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.*;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.constants.RedisConstant;
import com.toiletCat.constants.ToiletCatConfigConstant;
import com.toiletCat.dao.CatchRecordDao;
import com.toiletCat.dao.UserDao;
import com.toiletCat.entity.*;
import com.toiletCat.enums.*;
import com.toiletCat.service.*;
import com.toiletCat.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@SuppressWarnings("all")
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private CatchRecordDao catchRecordDao;

    @Autowired
    private MachineService machineService;

    @Autowired
    private GameRoomService gameRoomService;

    @Autowired
    private UserSpendRecordService userSpendRecordService;

    @Autowired
    private UserRechargeRecordService userRechargeRecordService;

    @Autowired
    private UserGameRoomService userGameRoomService;

    @Autowired
    private UserToyService userToyService;

    @Autowired
    private ToiletCatConfigService toiletCatConfigService;

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private MoneyForCoinService moneyForCoinService;

    /**
     * 根据手机号用户注册或登录
     *
     * @param mobileNo 手机号
     */
    @Override
    public CommonResult<User> registerOrLoginUserByMobileNo(final String mobileNo) {

        JSONObject json = new JSONObject();
        json.put("mobileNo", mobileNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                User user;

                // 若当前用户为新用户则添加用户
                if (userDao.countUserByMobileNo(mobileNo) == 0) {

                    user = new User();

                    PropertiesUtil systemProperties = PropertiesUtil.getInstance("system");
                    // 用户手机号
                    user.setMobileNo(mobileNo);

                    Integer defaultUserCoin = Integer.valueOf(
                            toiletCatConfigService.getConfigByKey(ToiletCatConfigConstant.USER_DEFAULT_COIN));

                    // 用户游戏币数
                    user.setUserCoin(defaultUserCoin);

                    String defaultUserImg = systemProperties.getProperty("user_default_img")
                            + "defaultHead" + RandomIntUtil.getRandomNumByHighBound(5) + ".png";

                    // 用户头像
                    user.setUserImg(defaultUserImg);

                    String userNo = UUIDUtil.generateUUID();
                    // 用户编号
                    user.setUserNo(userNo);
                    // 用户名
                    user.setUserName(RandomIntUtil.getRandomString(10));

                    user.setOpenId(BaseConstant.DEFAULT_NULL);

                    String invitationCode = "";

                    try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

                        while (true) {

                            // 生成用户邀请码
                            invitationCode = RandomIntUtil.getRandomString(5);

                            // 判断邀请码是否重复
                            if(redisUtil.sadd(BaseConstant.USER_INVITATION_CODE, invitationCode) == 1L) {

                                // 若不重复则跳出
                                break;
                            }
                        }

                    } catch(Exception e) {

                        logger.error("registerOrLoginUserByMobileNo redis error:" + e, e);

                    }

                    // 用户邀请码
                    user.setInvitationCode(invitationCode);

                    // 用户邀请状态
                    user.setInvitationUserNo(BaseConstant.DEFAULT_INVITATION_USER_NO);
                    
                    userDao.addUser(user);

                } else {

                    user = userDao.getUserByMobileNo(mobileNo);

                    rechargeService.getInitRechargeResultByOrderInfo(user.getUserNo());

                    user = userDao.getUserByUserNo(user.getUserNo());
                }

                got(user);

            }
        }, true, "registerOrLoginUserByMobileNo", json);
    }

    /**
     * 根据微信用户信息注册或登录
     *
     * @param openId 微信用户信息
     */
    @Override
    public CommonResult<User> registerOrLoginUserByWxUserInfo(final WxUserInfo wxUserInfo) {

        JSONObject json = new JSONObject();
        json.put("wxUserInfo", wxUserInfo);

        return exec(new Callback() {
            @Override
            public void exec() {

                User user;

                // 若当前用户为新用户则添加用户
                if (userDao.countUserByOpenId(wxUserInfo.getOpenId()) == 0) {

                    user = new User();

                    PropertiesUtil systemProperties = PropertiesUtil.getInstance("system");

                    // 用户openId
                    user.setOpenId(wxUserInfo.getOpenId());

                    // 用户手机号
                    user.setMobileNo(BaseConstant.DEFAULT_NULL);

                    Integer defaultUserCoin = Integer.valueOf(
                            toiletCatConfigService.getConfigByKey(ToiletCatConfigConstant.USER_DEFAULT_COIN));

                    // 用户游戏币数
                    user.setUserCoin(defaultUserCoin);

                    String headImgUrl = wxUserInfo.getHeadImgUrl();

                    // 若微信获取头像为空
                    if(BaseConstant.DEFAULT_HEAD_FLAG.equals(headImgUrl)) {

                        headImgUrl = systemProperties.getProperty("user_default_img")
                                + "defaultHead" + RandomIntUtil.getRandomNumByHighBound(5) + ".png";
                    }

                    // 用户头像
                    user.setUserImg(headImgUrl);

                    String userNo = UUIDUtil.generateUUID();

                    // 用户编号
                    user.setUserNo(userNo);

                    String nickName = wxUserInfo.getNickName();

                    // 若微信获取昵称为空
                    if(BaseConstant.DEFAULT_NAME_FLAG.equals(nickName)) {

                        nickName = RandomIntUtil.getRandomString(10);
                    }

                    // 用户名
                    user.setUserName(nickName);

                    String invitationCode = "";

                    try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

                        while (true) {

                            // 生成用户邀请码
                            invitationCode = RandomIntUtil.getRandomString(5);

                            // 判断邀请码是否重复
                            if(redisUtil.sadd(BaseConstant.USER_INVITATION_CODE, invitationCode) == 1L) {

                                // 若不重复则跳出
                                break;
                            }
                        }

                    } catch(Exception e) {

                        logger.error("registerOrLoginUserByOpenId redis error:" + e, e);

                    }

                    // 用户邀请码
                    user.setInvitationCode(invitationCode);

                    // 用户邀请状态
                    user.setInvitationUserNo(BaseConstant.DEFAULT_INVITATION_USER_NO);

                    userDao.addUser(user);

                } else {

                    user = userDao.getUserByOpenId(wxUserInfo.getOpenId());

                    // 查询用户所有初始化订单信息
                    rechargeService.getInitRechargeResultByOrderInfo(user.getUserNo());

                    user = userDao.getUserByUserNo(user.getUserNo());
                }

                got(user);

            }
        }, true, "registerOrLoginUserByOpenId", json);
    }

    /**
     * 验证短信验证码
     *
     * @param ticket
     * @param mobileNo
     * @return
     */
    @Override
    public CommonResult<String> verifyCode(final String ticket, final String mobileNo) {

        JSONObject json = new JSONObject();
        json.put("ticket", ticket);
        json.put("mobileNo", mobileNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                try (RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

                    String verifyCode = redisUtil.get(RedisConstant.SMS_MOBILE_NO.replace(
                            RedisConstant.PLACEHOLDER, mobileNo));

                    // 若验证码不对
                    if (!ticket.equals(verifyCode)) {

                        setOtherMsg();

                        got(BaseConstant.V_CODE_ERR_MSG);

                        return;
                    }

                    got(BaseConstant.SUCCESS);

                    return;

                } catch (Exception e) {

                    logger.error("{} verifyCode redis error:" + e, BaseConstant.LOG_ERR_MSG, e);

                    setOtherMsg();

                    got(BaseConstant.V_CODE_ERR_MSG);
                }
            }
        }, true, "verifyCode", json);
    }

    /**
     * 根据用户编号,娃娃机编号判断用户是否可以进行游戏若可以则直接扣除相应游戏币数
     *
     * @param userNo    用户编号
     * @param machineNo 娃娃机编号
     * @return
     */
    @Override
    @Transactional
    public CommonResult<String> userPlayMachine(final String userNo, final String machineNo) {

        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("machineNo", machineNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                // 获得用户当前游戏币数
                Integer userCoin = userDao.getUserCoinByUserNo(userNo);
                // 获得机器信息
                CommonResult<Integer> needCoinResult = machineService.getCoinByMachineNo(machineNo);

                Integer needCoin;

                if (needCoinResult.success()) {

                    needCoin = needCoinResult.getValue();

                } else {

                    logger.error("userPlayMachine getCoinByMachineNo error");

                    setOtherMsg();

                    got(BaseConstant.DEDUCTION_COIN_FAIL);

                    return;
                }

                // 判断用户游戏币是否足够
                if (userCoin < needCoin) {

                    logger.info("userPlayMachine 用户游戏币不足,用户游戏币数:{},所需游戏币数:{}", userCoin, needCoin);

                    setOtherMsg();

                    got(BaseConstant.NOT_ENOUGH_COIN);

                    return;
                }

                try (RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

                    String key = RedisConstant.MACHINE_IN_USE.replace(RedisConstant.PLACEHOLDER, machineNo);

                    // 判断当前机器是否已有用户使用
                    if (redisUtil.setnx(key, userNo) == 0) {

                        setOtherMsg();

                        // 若当前机器已被占用
                        got(BaseConstant.MACHINE_ALREADY_IN_UES);

                        return;
                    }

                } catch (Exception e) {

                    logger.error("userPlayMachine redis error:" + e, e);

                    setOtherMsg();

                    got(BaseConstant.MACHINE_ALREADY_IN_UES);

                    return;
                }

                // 获得玩具编号和玩具图片地址
                CommonResult<UserMachine> userMachineCommonResult =
                        machineService.getToyNoAndToyImgByMachineNo(machineNo);

                if(!userMachineCommonResult.success()) {

                    setOtherMsg();

                    got(BaseConstant.DEDUCTION_COIN_FAIL);

                    return;
                }

                UserMachine userMachine = userMachineCommonResult.getValue();

                // 添加用户消费记录
                UserSpendRecord userSpendRecord = new UserSpendRecord();

                Date date = new Date();

                // 用户编号
                userSpendRecord.setUserNo(userNo);

                // 消费游戏币数
                userSpendRecord.setCoin(needCoin);

                // 消费类型
                userSpendRecord.setTradeType(TradeType.SPEND.getType());

                // 消费日期
                userSpendRecord.setTradeDate(DateUtil.getDateByTime(date));

                // 消费时间
                userSpendRecord.setTradeTime(date);

                // 订单号
                String spendOrderNo = BaseConstant.TOILER_CAT + date.getTime() + userNo;

                userSpendRecord.setOrderNo(spendOrderNo);

                try {
                    // 扣除用户游戏币
                    userDao.updateUserCoinByUserNo(-needCoin, userNo);

                    userSpendRecord.setTradeStatus(TradeStatus.SUCCESS.getStatus());

                    userSpendRecordService.addUserSpendRecord(userSpendRecord);

                    // 添加娃娃抓取记录
                    CatchRecord catchRecord = new CatchRecord();

                    // 抓取id
                    String catchId = UUIDUtil.generateUUID();

                    catchRecord.setCatchId(catchId);

                    // 用户编号
                    catchRecord.setUserNo(userNo);

                    // 玩具编号
                    catchRecord.setToyNo(userMachine.getToyNo());

                    // 玩具名称
                    catchRecord.setToyName(userMachine.getToyName());

                    // 玩具图片
                    catchRecord.setToyImg(userMachine.getToyImg());

                    // 抓取状态 默认为等待
                    catchRecord.setCatchResult(CatchResult.CATCH_WAIT.getStatus());

                    // 抓取状态
                    catchRecord.setCatchStatus(CatchStatus.NORMAL.getStatus());

                    // 抓取时间
                    catchRecord.setCatchTime(new Date());

                    catchRecordDao.addCatchRecord(catchRecord);

                    // 构建返回字符串
                    JSONObject returnJson = new JSONObject();

                    returnJson.put("catchId", catchId);

                    returnJson.put("result", BaseConstant.SUCCESS);

                    logger.info("userPlayMachine 成功扣除用户:{} 游戏币数:{} 结果{}", userNo, needCoin,
                            returnJson.toJSONString());

                    got(returnJson.toJSONString());

                    return;

                } catch (Exception e) {

                    logger.error("userPlayMachine 扣除用户游戏币失败" + e, e);

                    userSpendRecord.setTradeStatus(TradeStatus.FAIL.getStatus());

                    userSpendRecordService.addUserSpendRecord(userSpendRecord);

                    setOtherMsg();

                    got(BaseConstant.DEDUCTION_COIN_FAIL);
                }
            }
        }, true, "userPlayMachine", json);
    }

    /**
     * 根据用户编号,游戏房间编号判断用户是否可以进行游戏若可以则直接扣除相应游戏币数
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    @Override
    @Transactional
    public CommonResult<String> userPlayGame(final String userNo, final String gameRoomNo) {

        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("gameRoomNo", gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                // 获得用户当前游戏币数
                Integer userCoin = userDao.getUserCoinByUserNo(userNo);

                // 获得机器信息
                CommonResult<Integer> needCoinResult = gameRoomService.getCoinByGameRoomNo(gameRoomNo);

                Integer needCoin;

                if (needCoinResult.success()) {

                    needCoin = needCoinResult.getValue();

                } else {

                    logger.error("userPlayGame getCoinByGameRoomNo error");

                    setOtherMsg();

                    got(BaseConstant.DEDUCTION_COIN_FAIL);

                    return;
                }

                // 判断用户游戏币是否足够
                if (userCoin < needCoin) {

                    logger.info("userPlayGame 用户游戏币不足,用户游戏币数:{},所需游戏币数:{}", userCoin, needCoin);

                    setOtherMsg();

                    got(BaseConstant.NOT_ENOUGH_COIN);

                    return;
                }

                // 获得玩具编号和玩具图片地址
                CommonResult<UserSeeGameRoom> userSeeGameRoomCommonResult =
                        gameRoomService.getUserSeeGameRoomByGameRoomNo(gameRoomNo);

                if(!userSeeGameRoomCommonResult.success()) {

                    setOtherMsg();

                    got(BaseConstant.SYSTEM_ERROR);

                    return;
                }

                UserSeeGameRoom userSeeGameRoom = userSeeGameRoomCommonResult.getValue();

                // 添加用户消费记录
                UserSpendRecord userSpendRecord = new UserSpendRecord();

                Date date = new Date();

                // 用户编号
                userSpendRecord.setUserNo(userNo);

                // 消费游戏币数
                userSpendRecord.setCoin(needCoin);

                // 消费类型
                userSpendRecord.setTradeType(TradeType.SPEND.getType());

                // 消费日期
                userSpendRecord.setTradeDate(DateUtil.getDateByTime(date));

                // 消费时间
                userSpendRecord.setTradeTime(date);

                // 订单号
                String spendOrderNo = BaseConstant.TOILER_CAT + date.getTime() + userNo;

                userSpendRecord.setOrderNo(spendOrderNo);

                try {

                    // 扣除用户游戏币
                    userDao.updateUserCoinByUserNo(-needCoin, userNo);

                    userSpendRecord.setTradeStatus(TradeStatus.SUCCESS.getStatus());

                    userSpendRecordService.addUserSpendRecord(userSpendRecord);

                    // 添加娃娃抓取记录
                    CatchRecord catchRecord = new CatchRecord();

                    // 抓取id
                    String catchId = UUIDUtil.generateUUID();

                    catchRecord.setCatchId(catchId);

                    // 用户编号
                    catchRecord.setUserNo(userNo);

                    // 玩具编号
                    catchRecord.setToyNo(userSeeGameRoom.getToyNo());

                    // 玩具名称
                    catchRecord.setToyName(userSeeGameRoom.getToyName());

                    // 玩具图片
                    catchRecord.setToyImg(userSeeGameRoom.getToyImg());

                    // 抓取结果 默认为等待
                    catchRecord.setCatchResult(CatchResult.CATCH_WAIT.getStatus());

                    // 抓取时间
                    catchRecord.setCatchTime(new Date());

                    // 抓取状态
                    catchRecord.setCatchStatus(CatchStatus.NORMAL.getStatus());

                    catchRecordDao.addCatchRecord(catchRecord);

                    // 构建返回字符串
                    JSONObject returnJson = new JSONObject();

                    returnJson.put("catchId", catchId);

                    returnJson.put("result", BaseConstant.SUCCESS);

                    logger.info("userPlayGame 成功扣除用户:{} 游戏币数:{} 结果{}", BaseConstant.LOG_MSG, userNo, needCoin,
                            returnJson.toJSONString());

                    got(returnJson.toJSONString());

                    return;

                } catch (Exception e) {

                    logger.error("userPlayGame 扣除用户游戏币失败" + e, e);

                    userSpendRecord.setTradeStatus(TradeStatus.FAIL.getStatus());

                    userSpendRecordService.addUserSpendRecord(userSpendRecord);

                    setOtherMsg();

                    got(BaseConstant.DEDUCTION_COIN_FAIL);
                }
            }
        }, true, "userPlayGame", json);
    }

    /**
     * 发送短信验证码方法
     *
     * @param mobileNo 手机号
     * @return
     */
    @Override
    public CommonResult<String> sendMobileVerificationCode(final String mobileNo) {

        JSONObject json = new JSONObject();
        json.put("mobileNo", mobileNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                // 通过配置文件获取参数
                PropertiesUtil systemProperties = PropertiesUtil.getInstance("system");

                // 获取短信超时参数
                String redisTimeout = systemProperties.getProperty("sms_redis_time_out");

                // 获取短信超时参数
                String textTimeout = systemProperties.getProperty("sms_text_time_out");

                // 获取redis链接
                try (RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

                    String random = RandomIntUtil.getRandom();

                    if (SDKTestSendTemplateSMS.sendMobileVerificationCode(mobileNo, random, textTimeout)) {

                        redisUtil.set(Integer.valueOf(redisTimeout),
                                RedisConstant.SMS_MOBILE_NO.replace(RedisConstant.PLACEHOLDER, mobileNo), random);

                        got(BaseConstant.SUCCESS);

                        return;
                    }

                } catch (Exception e) {

                    logger.error("sendMobileVerificationCode redis error:" + e, e);
                }

                got(BaseConstant.FAIL);

            }
        }, true, "userRegisterOrLogin", json);
    }

    /**
     * 根据用户编号获得用户信息
     *
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<User> getUserByUserNo(final String userNo) {

        JSONObject json = new JSONObject();
        json.put("userNo", userNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                // 判断当前用户编号是否正确
                if(userDao.countUserByUserNo(userNo) == 0) {

                    logger.warn("getUserByUserNo user not exists userNo:" + userNo);

                    return;
                }

                // 处理非终态订单
                rechargeService.getInitRechargeResultByOrderInfo(userNo);

                got(userDao.getUserByUserNo(userNo));

            }
        }, true, "getUserByUserNo", json);
    }

    /**
     * 根据微信openId获得用户信息
     * @param openId 微信openId
     * @return
     */
    @Override
    public CommonResult<User> getUserByOpenId(final String openId) {

        JSONObject json = new JSONObject();
        json.put("openId", openId);

        return exec(new Callback() {
            @Override
            public void exec() {

                // 判断当前openId是否存在
                if(userDao.countUserByOpenId(openId) == 0) {

                    logger.info("getUserByOpenId user not exists openId:" + openId);

                    return;
                }

                got(userDao.getUserByOpenId(openId));

            }
        }, true, "getUserByOpenId", json);
    }

    /**
     * 根据用户编号修改用户名和用户头像
     * @param userNo 用户编号
     * @param userName 用户名
     * @param userImg 用户头像
     */
    @Override
    public CommonResult updateUserInfoByIdAndUserNo(final String userNo, final String userName, final String userImg) {

        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("userName", userName);
        json.put("userImg", userImg);

        return exec(new Callback() {
            @Override
            public void exec() {
                userDao.updateUserInfoByIdAndUserNo(userNo, userName, userImg);

            }
        }, true, "updateUserInfoByIdAndUserNo", json);
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return
     */
    @Override
    public CommonResult updateUserInfo(final User user) {

        JSONObject json = new JSONObject();
        json.put("user", user);

        return exec(new Callback() {
            @Override
            public void exec() {
                userDao.updateUserInfo(user);

            }
        }, false, "updateUserInfo", json);
    }

    /**
     * 根据用户编号和游戏房间号获得游戏抓取结果
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间号
     * @param catchId 抓取id
     * @param status 状态
     * @return
     */
    @Override
    public CommonResult<String> getGameCatchResultByUserNoAndGameRoomNo(final String userNo, final String gameRoomNo,
                                                                        final String catchId, final Integer status) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("gameRoomNo", gameRoomNo);
        json.put("catchId", catchId);
        json.put("status", status);

        return exec(new Callback() {
            @Override
            public void exec() {

                Integer userLucKyNum = RandomIntUtil.getRandomNum(5, Integer.valueOf(
                        toiletCatConfigService.getConfigByKey(ToiletCatConfigConstant.USER_LUCKY_NUM_BEFORE_THRESHOLD)));

                if(status > 0) {

                    // 判断用户之前是否有成功抓取记录
                    Integer userCatchRecordnum = catchRecordDao.countSuccessCatchRecordByUserNo(userNo);

                    if(userCatchRecordnum == 0) {
                        // 若是没有成功记录则直接成功
                        // 重置房间和用户房间幸运值
                        got(resetLuckyNum(catchId, userNo, gameRoomNo, userLucKyNum));
                        return;
                    }
                }

                // 获得房间当前幸运值,房间幸运值,房间累加幸运值
                CommonResult<GameRoom> gameRoomLuckyNum = gameRoomService.getLuckyNumByGameRoomNo(gameRoomNo);

                GameRoom gameRoom = gameRoomLuckyNum.getValue();

                Integer roomAddLuckyNum = gameRoom.getAddLuckyNum();

                // 判断当前用户是否在此房间有幸运值
                CommonResult<Integer> userGameRoomCount = userGameRoomService
                        .countUserGameRoomByUserNo(userNo, gameRoomNo);

                Boolean userGameRoomExist = true;

                Integer userNowLuckyNum = null;

                if(!userGameRoomCount.success()) {

                    got(getCatchFailReturn(catchId, userLucKyNum));

                    // 累加幸运值
                    addLuckyNum(userLucKyNum, userNowLuckyNum, userNo, gameRoomNo, roomAddLuckyNum, gameRoom);
                    return;
                }

                // 若用户第一次来此房间
                if(userGameRoomCount.getValue() == 0) {

                    UserGameRoom userGameRoom = new UserGameRoom();

                    // 游戏房间编号
                    userGameRoom.setGameRoomNo(gameRoomNo);
                    // 用户编号
                    userGameRoom.setUserNo(userNo);
                    // 用户幸运值
                    userGameRoom.setUserRoomLuckyNum(userLucKyNum);

                    // 添加用户游戏房间记录
                    userGameRoomService.addUserGameRoom(userGameRoom);

                    userNowLuckyNum = 0;
                }

                if(userNowLuckyNum == null) {
                    // 获得用户游戏房间当前幸运值
                    CommonResult<Integer> userGameLucyNumResult = userGameRoomService.
                            getUserGameRoomLuckyNumByUserNo(userNo, gameRoomNo);

                    if(!userGameLucyNumResult.success()) {

                        got(getCatchFailReturn(catchId, userLucKyNum));

                        // 累加幸运值
                        addLuckyNum(userLucKyNum, userNowLuckyNum, userNo, gameRoomNo, roomAddLuckyNum, gameRoom);
                        return;
                    }

                    userNowLuckyNum = userGameLucyNumResult.getValue();
                }

                // 幸运值大于阈值后
                if(userNowLuckyNum >= Integer.valueOf(
                        toiletCatConfigService.getConfigByKey(ToiletCatConfigConstant.USER_LUCKY_NUM_THRESHOLD))) {

                    // 按幸运值大于阈值后幸运值最大随机数上限进行累加
                    userLucKyNum = RandomIntUtil.getRandomNum(3, Integer.valueOf(
                            toiletCatConfigService.getConfigByKey(ToiletCatConfigConstant.USER_LUCKY_NUM_AFTER_THRESHOLD)));

                }

                // 若当前用户是特殊用户
                if(toiletCatConfigService.getConfigByKey(ToiletCatConfigConstant.VIP_USER_NO_LIST).contains(userNo)) {

                    // 用户游戏房间幸运累加值则为特殊值
                    userLucKyNum = Integer.valueOf(toiletCatConfigService.getConfigByKey(
                            ToiletCatConfigConstant.VIP_USER_GAME_ROOM_ADD_NUM));

                    // 日志记录
                    logger.info("getGameCatchResultByUserNoAndGameRoomNo user is vip userNo:" + userNo +
                            ", userLucKyNum:" + userLucKyNum);
                }

                logger.info("getGameCatchResultByUserNoAndGameRoomNo userLucKyNum:" + userLucKyNum +
                        ",userNowLuckyNum:" + userNowLuckyNum + ",status:" + status);

                // 用户房间幸运值大于等于最大用户房间幸运值
                if(userLucKyNum + userNowLuckyNum >= BaseConstant.MAX_USER_ROOM_LUCKY_NUM && status > 0) {

                    logger.info("getGameCatchResultByUserNoAndGameRoomNo 用户幸运值封顶");

                    // 重置房间和用户房间幸运值
                    got(resetLuckyNum(catchId, userNo, gameRoomNo, userLucKyNum));
                    return;
                }

                logger.info("getGameCatchResultByUserNoAndGameRoomNo roomNowLuckyNum:" + gameRoom.getRoomNowLuckyNum() +
                        ",roomAddLuckyNum:" + roomAddLuckyNum + ",status:" + status);

                // 若房间幸运值大于等于最大房间幸运值
                if(gameRoom.getRoomNowLuckyNum() + roomAddLuckyNum >= gameRoom.getRoomLuckyNum() && status > 0) {

                    logger.info("getGameCatchResultByUserNoAndGameRoomNo 房间幸运值封顶");

                    // 重置房间和用户房间幸运值
                    got(resetLuckyNum(catchId, userNo, gameRoomNo, userLucKyNum));
                    return;
                }

                // 累加幸运值
                addLuckyNum(userLucKyNum, userNowLuckyNum, userNo, gameRoomNo, roomAddLuckyNum, gameRoom);

                got(getCatchFailReturn(catchId, userLucKyNum));
                return;

            }
        }, true, "getGameCatchResultByUserNoAndGameRoomNo", json);
    }

    /**
     * 累加幸运值
     * @param userLucKyNum 添加用户房间幸运值
     * @param userNowLuckyNum 当前用户房间幸运值
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @param roomAddLuckyNum 房间每次累加幸运值
     * @param gameRoom 游戏房间
     */
    private void addLuckyNum(Integer userLucKyNum, Integer userNowLuckyNum, String userNo, String gameRoomNo,
                             Integer roomAddLuckyNum, GameRoom gameRoom) {


        if(userLucKyNum + userNowLuckyNum < BaseConstant.MAX_USER_ROOM_LUCKY_NUM && userNowLuckyNum != null) {

            logger.info("getGameCatchResultByUserNoAndGameRoomNo addLuckyNum addUserRoomLuckyNumByUserNoAndGameRoomNo");

            // 累加用户房间幸运值
            userGameRoomService.addUserRoomLuckyNumByUserNoAndGameRoomNo(userNo, gameRoomNo, userLucKyNum);
        }

        if(gameRoom.getRoomNowLuckyNum() + roomAddLuckyNum < gameRoom.getRoomLuckyNum()) {

            logger.info("getGameCatchResultByUserNoAndGameRoomNo addLuckyNum addRoomLuckyNumByGameRoomNo");

            // 累加游戏房间幸运值
            gameRoomService.addRoomLuckyNumByGameRoomNo(gameRoomNo);
        }
    }

    /**
     * 用户邀请码
     * @param userNo 用户编号
     * @param inviteCode 邀请码
     * @return
     */
    @Override
    public CommonResult<String> userInvite(final String userNo, final String inviteCode) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("inviteCode", inviteCode);

        return exec(new Callback() {
            @Override
            public void exec() {

                // 判断用户编号是否正确
                Integer count = userDao.countUserByUserNo(userNo);

                if(count == 0) {

                    logger.warn("userInvite " + userNo + " is wrong");

                    setOtherMsg();

                    got("userNo is wrong");

                    return;
                }

                String invitationUserNo = userDao.getInvitationUserNoByUserNo(userNo);

                // 判断当前用户是否已被邀请过
                if(!BaseConstant.DEFAULT_INVITATION_USER_NO.equals(invitationUserNo)) {

                    setOtherMsg();

                    got("只能填写一次邀请码");

                    return;
                }

                // 根据邀请码获得邀请用户用户编号
                String inviteUserNo = userDao.getUserNoByInvitationCode(inviteCode);

                // 判断该邀请码是否正确
                if(StringUtils.isBlank(inviteUserNo)) {

                    setOtherMsg();

                    got("请填写正确的邀请码");

                    return;
                }

                // 判断邀请用户和被邀请用户是否是同一个用户
                if(inviteUserNo.equals(userNo)) {

                    setOtherMsg();

                    got("不能填写自己的邀请码");

                    return;
                }

                PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

                Integer coin = Integer.valueOf(toiletCatConfigService.getConfigByKey(ToiletCatConfigConstant.USER_INVITE_COIN));

                Date tradeTime = new Date();

                Integer tradeDate = DateUtil.getDate();

                // 给被邀请用户添加游戏币
                userDao.updateUserCoinByUserNo(coin, userNo);

                // 更新用户邀请用户编号
                userDao.updateInvitationUserNoByUserNo(userNo, inviteUserNo);

                UserSpendRecord userSpendRecord = new UserSpendRecord();

                userSpendRecord.setUserNo(userNo);

                // 充值游戏币数
                userSpendRecord.setCoin(coin);

                // 交易时间
                userSpendRecord.setTradeTime(tradeTime);

                // 交易日期
                userSpendRecord.setTradeDate(tradeDate);

                // 被邀请用户订单号
                String beInviteOrderNo = BaseConstant.TOILER_CAT + tradeTime.getTime() + userNo;

                // 设置被邀请用户订单号
                userSpendRecord.setOrderNo(beInviteOrderNo);

                // 交易类型(邀请码)
                userSpendRecord.setTradeType(TradeType.INVITE.getType());

                // 交易状态(成功)
                userSpendRecord.setTradeStatus(TradeStatus.SUCCESS.getStatus());

                // 添加被邀请用户消费记录
                userSpendRecordService.addUserSpendRecord(userSpendRecord);

                // 获得邀请用户当前已邀请用户数量
                Integer invitationCount = userDao.countByInvitationUserNo(invitationUserNo);

                // 若数量小于最大邀请数量则给邀请用户添加游戏币
                if(invitationCount < BaseConstant.INVITE_MAX_NUM) {

                    // 给邀请用户添加游戏币
                    userDao.updateUserCoinByUserNo(coin, inviteUserNo);

                    // 设置邀请用户用户编号
                    userSpendRecord.setUserNo(inviteUserNo);

                    String inviteOrderNo = BaseConstant.TOILER_CAT + tradeTime.getTime() + inviteUserNo;

                    // 设置邀请用户订单编号
                    userSpendRecord.setOrderNo(inviteOrderNo);

                    // 添加邀请用户消费记录
                    userSpendRecordService.addUserSpendRecord(userSpendRecord);
                }

                // 获得当前用户游戏币并返回前端
                Integer userCoin = userDao.getUserCoinByUserNo(userNo);

                JSONObject json = new JSONObject();

                json.put("userCoin", userCoin);

                got(json.toJSONString());

            }
        }, true, "userInvite", json);
    }

    /**
     * 获得抓取失败返回值
     * @param catchId 抓取id
     * @param userLucKyNum 用户房间累加幸运值
     * @return
     */
    private String getCatchFailReturn(String catchId, Integer userLucKyNum) {

        catchRecordDao.updateCatchResultByCatchId(CatchResult.CATCH_FAIL.getStatus(), catchId);

        JSONObject json = new JSONObject();
        // 抓取失败
        json.put("catch_result", BaseConstant.CATCH_FAIL);
        //
        json.put("addNum", userLucKyNum);

        return json.toJSONString();
    }

    /**
     * 重置用户及游戏房间幸运值
     * @param catchId 抓取id
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @param userLucKyNum 用户房间累加幸运值
     * @return
     */
    private String resetLuckyNum(String catchId, String userNo, String gameRoomNo, Integer userLucKyNum) {

        // 重置用户房间幸运值
        CommonResult resetUserRoom = userGameRoomService.resetUserRoomLuckyNum(userNo, gameRoomNo);
        // 重置当前房间幸运值
        CommonResult resetGameRoom = gameRoomService.resetRoomLuckyNumByGameRoomNo(gameRoomNo);

        if(resetUserRoom.success() && resetGameRoom.success()) {

            catchRecordDao.updateCatchResultByCatchId(CatchResult.CATCH_SUCCESS.getStatus(), catchId);

            // 添加用户战利品记录
            UserToy userToy = new UserToy();

            userToy.setUserNo(userNo);

            String toyNo = gameRoomService.getUserSeeGameRoomByGameRoomNo(gameRoomNo).getValue().getToyNo();

            userToy.setToyNo(toyNo);

            userToy.setChoiceType(ChoiceType.INIT.getStatus());

            userToyService.addUserToy(userToy);

            JSONObject json = new JSONObject();

            json.put("catch_result", BaseConstant.CATCH_SUCCESS);

            // 获得用户名
            String userName = userDao.getUserNameByUserNo(userNo);

            // 获得玩具名
            String toyName = gameRoomService.getToyNameByGameRoomNo(gameRoomNo).getValue();

            JSONObject msg = new JSONObject();

            msg.put("userNo", userNo);

            msg.put("userName", userName);

            msg.put("gameRoomNo", gameRoomNo);

            msg.put("toyName", toyName);

            // 发送抓取成功的通知
            SocketUtil.sendMsg(msg.toJSONString());

            return json.toJSONString();

        }

        return getCatchFailReturn(catchId, userLucKyNum);

    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

}
