package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.UserMachine;
import com.toiletCat.bean.UserSeeGameRoom;
import com.toiletCat.constants.BaseConstant;
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
import java.math.BigDecimal;
import java.util.Calendar;
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

    /**
     * 用户注册或登录
     *
     * @param mobileNo 手机号
     */
    @Override
    public CommonResult<User> registerOrLoginUser(final String mobileNo) {

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
                            toiletCatConfigService.getConfigByKey(BaseConstant.USER_DEFAULT_COIN));

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

                    String invitationCode = "";

                    try(RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {

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
                        logger.error("{} registerOrLoginUser redis error:" + e, BaseConstant.LOG_ERR_MSG, e);
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
                return;
            }
        }, "registerOrLoginUser", json.toJSONString());
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

                try (RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {

                    String verifyCode = redisUtil.get(BaseConstant.SMS_MOBILE_NO.replace("#{}", mobileNo));

                    // 若验证码不对
                    if (!ticket.equals(verifyCode)) {
                        setOtherMsg();
                        got(BaseConstant.VCODE_ERR_MSG);
                        return;
                    }

                    got(BaseConstant.SUCCESS);
                    return;

                } catch (Exception e) {
                    logger.error("{} verifyCode redis error:" + e, BaseConstant.LOG_ERR_MSG, e);
                    setOtherMsg();
                    got(BaseConstant.VCODE_ERR_MSG);
                    return;
                }
            }
        }, "verifyCode", json.toJSONString());
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
                    logger.error("{} getCoinByMachineNo error:", BaseConstant.LOG_ERR_MSG);
                    setOtherMsg();
                    got(BaseConstant.DEDUCTION_COIN_FAIL);
                    return;
                }

                // 判断用户游戏币是否足够
                if (userCoin < needCoin) {
                    logger.info("{} 用户游戏币不足,用户游戏币数:{},所需游戏币数:{}", BaseConstant.LOG_MSG, userCoin, needCoin);
                    setOtherMsg();
                    got(BaseConstant.NOT_ENOUGH_COIN);
                    return;
                }

                try (RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {
                    String key = BaseConstant.MACHINE_IN_USE.replace("#{}", machineNo);
                    // 判断当前机器是否已有用户使用
                    if (redisUtil.setnx(key, userNo) == 0) {
                        setOtherMsg();
                        // 若当前机器已被占用
                        got(BaseConstant.MACHINE_ALREADY_IN_UES);
                        return;
                    }
                } catch (Exception e) {
                    logger.error("{} userPlayMachine redis error:" + e, BaseConstant.LOG_ERR_MSG, e);
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

                    logger.info("{} 成功扣除用户:{} 游戏币数:{} 结果{}", BaseConstant.LOG_MSG, userNo, needCoin,
                            returnJson.toJSONString());

                    got(returnJson.toJSONString());
                    return;
                } catch (Exception e) {
                    logger.error("{} 扣除用户游戏币失败" + e, BaseConstant.LOG_ERR_MSG, e);
                    userSpendRecord.setTradeStatus(TradeStatus.FAIL.getStatus());
                    userSpendRecordService.addUserSpendRecord(userSpendRecord);
                    setOtherMsg();
                    got(BaseConstant.DEDUCTION_COIN_FAIL);
                    return;
                }
            }
        }, "userPlayMachine", json.toJSONString());
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
                    logger.error("{} getCoinByGameRoomNo error:", BaseConstant.LOG_ERR_MSG);
                    setOtherMsg();
                    got(BaseConstant.DEDUCTION_COIN_FAIL);
                    return;
                }

                // 判断用户游戏币是否足够
                if (userCoin < needCoin) {
                    logger.info("{} 用户游戏币不足,用户游戏币数:{},所需游戏币数:{}", BaseConstant.LOG_MSG, userCoin, needCoin);
                    setOtherMsg();
                    got(BaseConstant.NOT_ENOUGH_COIN);
                    return;
                }

                // 获得玩具编号和玩具图片地址
                CommonResult<UserSeeGameRoom> userSeeGameRoomCommonResult =
                        gameRoomService.getUserSeeGameRoomByGameRoomNo(gameRoomNo);

                if(!userSeeGameRoomCommonResult.success()) {
                    setOtherMsg();
                    got(BaseConstant.DEDUCTION_COIN_FAIL);
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

                    logger.info("{} 成功扣除用户:{} 游戏币数:{} 结果{}", BaseConstant.LOG_MSG, userNo, needCoin,
                            returnJson.toJSONString());

                    got(returnJson.toJSONString());
                    return;
                } catch (Exception e) {
                    logger.error("{} 扣除用户游戏币失败" + e, BaseConstant.LOG_ERR_MSG, e);
                    userSpendRecord.setTradeStatus(TradeStatus.FAIL.getStatus());
                    userSpendRecordService.addUserSpendRecord(userSpendRecord);
                    setOtherMsg();
                    got(BaseConstant.DEDUCTION_COIN_FAIL);
                    return;
                }
            }
        }, "userPlayGame", json.toJSONString());
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
                try (RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {

                    String random = RandomIntUtil.getRandom();

                    if (SDKTestSendTemplateSMS.sendMobileVerificationCode(mobileNo, random, textTimeout)) {

                        redisUtil.set(Integer.valueOf(redisTimeout),
                                BaseConstant.SMS_MOBILE_NO.replace("#{}", mobileNo), random);

                        got(BaseConstant.SUCCESS);

                        return;
                    }

                } catch (Exception e) {
                    logger.error("{} sendMobileVerificationCode redis error:" + e, BaseConstant.LOG_ERR_MSG, e);
                }

                got(BaseConstant.FAIL);

                return;
            }
        }, "userRegisterOrLogin", json.toJSONString());
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
        }, "getUserByUserNo", json.toJSONString());
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
        }, "updateUserInfoByIdAndUserNo", json.toJSONString());
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
                // 获得房间当前幸运值,房间幸运值,房间累加幸运值
                CommonResult<GameRoom> gameRoomLuckyNum = gameRoomService.getLuckyNumByGameRoomNo(gameRoomNo);

                GameRoom gameRoom = gameRoomLuckyNum.getValue();

                Integer roomAddLuckyNum = gameRoom.getAddLuckyNum();

                Integer userLucKyNum = RandomIntUtil.getRandomNumByHighBound(Integer.valueOf(
                        toiletCatConfigService.getConfigByKey(BaseConstant.USER_LUCKY_NUM_BEFORE_THRESHOLD)));

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
                        toiletCatConfigService.getConfigByKey(BaseConstant.USER_LUCKY_NUM_THRESHOLD))) {

                    // 按幸运值大于阈值后幸运值最大随机数上限进行累加
                    userLucKyNum = RandomIntUtil.getRandomNumByHighBound(Integer.valueOf(
                            toiletCatConfigService.getConfigByKey(BaseConstant.USER_LUCKY_NUM_AFTER_THRESHOLD)));

                }

                // 若当前用户是特殊用户
                if(toiletCatConfigService.getConfigByKey(BaseConstant.VIP_USER_NO_LIST).contains(userNo)) {

                    // 用户游戏房间幸运累加值则为特殊值
                    userLucKyNum = Integer.valueOf(toiletCatConfigService.getConfigByKey(
                            BaseConstant.VIP_USER_GAME_ROOM_ADD_NUM));

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
        }, "getGameCatchResultByUserNoAndGameRoomNo", json.toJSONString());
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
     * 用户充值
     * @param userNo 用户编号
     * @param amount 金额
     * @param rechargeType 充值类型
     * @return
     */
    @Override
    public CommonResult<String> userRecharge(final String userNo, final String amount, final String rechargeType) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("amount", amount);

        return exec(new Callback() {
            @Override
            public void exec() {

                logger.info("userRecharge userNo:" + userNo + ", amount:" + amount);

                Integer coin = MoneyForCoin.getValueMapByKey(amount);

                if(coin == null) {
                    setOtherMsg();
                    got("请重新选择金额");
                    return;
                }

                logger.info("userRecharge userNo:" + userNo + ", amount:" + amount + ", coin:" + coin);

                // 如果充的是2元
                if(amount.equals(MoneyForCoin.EXCHANGE_2.getMoney())) {
                    try(RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {

                        String key = BaseConstant.RECHARGE_LIMIT_NUM_BY_USER.replace("#{}", userNo);

                        // 上限
                        String limit = toiletCatConfigService.getConfigByKey(BaseConstant.RECHARGE_LIMIT_NUM);

                        // 如果达到上限
                        if(limit.equals(redisUtil.get(key))) {
                            setOtherMsg();
                            got("当前选项每天只能充" + limit + "次哦");
                            return;
                        }

                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DAY_OF_YEAR, 1);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.MILLISECOND, 0);

                        Integer second = (int)(cal.getTimeInMillis() - System.currentTimeMillis())/1000;

                        // 设置超时时间为当前时间到第二天0点的时间并累加数量
                        redisUtil.incr(second, key);
                    } catch (Exception e) {
                        logger.error("userRecharge redis error:" + e, e);
                    }
                }

                Integer tradeDate = DateUtil.getDate();

                Date tradeTime = new Date();

                // 添加用户充值记录
                // 用户充值记录
                UserRechargeRecord userRechargeRecord = new UserRechargeRecord();

                // 金额
                userRechargeRecord.setAmount(BigDecimal.valueOf(Double.valueOf(amount)));

                // 充值类型(支付宝/微信)
                userRechargeRecord.setRechargeType(rechargeType);

                // 用户编号
                userRechargeRecord.setUserNo(userNo);

                // 交易日期
                userRechargeRecord.setTradeDate(tradeDate);

                // 交易时间
                userRechargeRecord.setTradeTime(tradeTime);

                // 交易状态
                userRechargeRecord.setTradeStatus(TradeStatus.INIT.getStatus());

                String orderNo = BaseConstant.TOILER_CAT + tradeTime.getTime() + userNo;

                // 订单号
                userRechargeRecord.setOrderNo(orderNo);

                CommonResult addRechargeResult = userRechargeRecordService.addUserRechargeRecord(userRechargeRecord);

                if(!addRechargeResult.success()) {
                    respondSysError();
                    return;
                }

                // 添加用户消费记录
                // 用户消费记录
                UserSpendRecord userSpendRecord = new UserSpendRecord();

                //  消费日期
                userSpendRecord.setTradeDate(tradeDate);

                // 订单号
                userSpendRecord.setOrderNo(orderNo);

                // 消费时间
                userSpendRecord.setTradeTime(tradeTime);

                // 消费类型(充值)
                userSpendRecord.setTradeType(TradeType.RECHARGE.getType());

                // 消费游戏币
                userSpendRecord.setCoin(coin);

                // 用户编号
                userSpendRecord.setUserNo(userNo);

                // 消费状态
                userSpendRecord.setTradeStatus(TradeStatus.INIT.getStatus());

                CommonResult addSpendResult = userSpendRecordService.addUserSpendRecord(userSpendRecord);

                if(!addSpendResult.success()) {
                    respondSysError();
                    return;
                }

                // 获得请求url并返回
                got(RechargeUtil.getRequestUrl(orderNo, String.valueOf(amount)));

            }
        }, "userRecharge", json.toJSONString());
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

                Integer coin = Integer.valueOf(toiletCatConfigService.getConfigByKey(BaseConstant.USER_INVITE_COIN));

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
        }, "userInvite", json.toJSONString());
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

            userToy.setDeliverId(0L);

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
