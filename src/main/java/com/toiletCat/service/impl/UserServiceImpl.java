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
import com.toiletCat.enums.CatchStatus;
import com.toiletCat.enums.TradeStatus;
import com.toiletCat.enums.TradeType;
import com.toiletCat.service.*;
import com.toiletCat.utils.*;
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
    private UserGameRoomService userGameRoomService;

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

                    PropertiesUtil systemProperties = new PropertiesUtil("system");
                    // 用户手机号
                    user.setMobileNo(mobileNo);
                    // 用户游戏币数
                    user.setUserCoin(0);
                    // 用户头像
                    user.setUserImg(systemProperties.getProperty("user_default_img"));
                    String userNo = UUIDUtil.generateUUID();
                    // 用户编号
                    user.setUserNo(userNo);
                    // 用户姓名
                    user.setUserName(RandomIntUtil.getRandomString(18));
                    // 用户邀请码
                    user.setInvitationCode(RandomIntUtil.getRandomString(8));
                    userDao.addUser(user);

                } else {
                    user = userDao.getUserByMobileNo(mobileNo);
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

                try (RedisUtil redisUtil = new RedisUtil("redis")) {

                    String verifyCode = redisUtil.get("sms-" + mobileNo);

                    // 若验证码不对
                    if (!ticket.equals(verifyCode)) {
                        got(BaseConstant.VCODE_ERR_MSG);
                        return;
                    }

                    got(BaseConstant.SUCCESS);
                    return;

                } catch (Exception e) {
                    logger.error("{} registerOrLoginUser redis error:" + e, BaseConstant.LOG_ERR_MSG, e);
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
                    got(BaseConstant.DEDUCTION_COIN_FAIL);
                    return;
                }

                // 判断用户游戏币是否足够
                if (userCoin < needCoin) {
                    logger.info("{} 用户游戏币不足,用户游戏币数:{},所需游戏币数:{}", BaseConstant.LOG_MSG, userCoin, needCoin);
                    got(BaseConstant.NOT_ENOUGH_COIN);
                    return;
                }

                try (RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {
                    String key = BaseConstant.MACHINE_IN_USE.replace("#{}", machineNo);
                    // 判断当前机器是否已有用户使用
                    if (redisUtil.setnx(key, userNo) == 0) {
                        // 若当前机器已被占用
                        got(BaseConstant.MACHINE_ALREADY_IN_UES);
                        return;
                    }
                } catch (Exception e) {
                    logger.error("{} userPlayMachine redis error:" + e, BaseConstant.LOG_ERR_MSG, e);
                    got(BaseConstant.MACHINE_ALREADY_IN_UES);
                    return;
                }

                // 获得玩具编号和玩具图片地址
                CommonResult<UserMachine> userMachineCommonResult =
                        machineService.getToyNoAndToyImgByMachineNo(machineNo);

                if(!userMachineCommonResult.success()) {
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
                    catchRecord.setCatchStatus(CatchStatus.CATCH_WAIT.getStatus());

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
                    got(BaseConstant.DEDUCTION_COIN_FAIL);
                    return;
                }

                // 判断用户游戏币是否足够
                if (userCoin < needCoin) {
                    logger.info("{} 用户游戏币不足,用户游戏币数:{},所需游戏币数:{}", BaseConstant.LOG_MSG, userCoin, needCoin);
                    got(BaseConstant.NOT_ENOUGH_COIN);
                    return;
                }

                // 获得玩具编号和玩具图片地址
                CommonResult<UserSeeGameRoom> userSeeGameRoomCommonResult =
                        gameRoomService.getUserSeeGameRoomByGameRoomNo(gameRoomNo);

                if(!userSeeGameRoomCommonResult.success()) {
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
                    // 抓取状态 默认为等待
                    catchRecord.setCatchStatus(CatchStatus.CATCH_WAIT.getStatus());

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
                PropertiesUtil systemProperties = new PropertiesUtil("system");

                // 获取短信超时参数
                String timeout = systemProperties.getProperty("sms_time_out");

                // 获取redis链接
                try (RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {

                    String random = RandomIntUtil.getRandom();

                    if (SDKTestSendTemplateSMS.sendMobileVerificationCode(mobileNo, random, timeout)) {

                        redisUtil.set(Integer.valueOf(timeout), "sms-" + mobileNo, random);

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
     * @return
     */
    @Override
    public CommonResult<String> getGameCatchResultByUserNoAndGameRoomNo(final String userNo, final String gameRoomNo) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("gameRoomNo", gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {
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
                    got(getCatchFailReturn(roomAddLuckyNum));
                    return;
                }

                Integer userLucKyNum = RandomIntUtil.getRandomNum(0);

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

                        got(getCatchFailReturn(roomAddLuckyNum));
                        return;
                    }

                    userNowLuckyNum = userGameLucyNumResult.getValue();
                }

                // 用户房间幸运值大于等于最大用户房间幸运值
                if(userLucKyNum + userNowLuckyNum >= BaseConstant.MAX_USER_ROOM_LUCKY_NUM) {
                    // 重置房间和用户房间幸运值
                    got(resetLuckyNum(userNo, gameRoomNo, roomAddLuckyNum));
                    return;
                }

                // 若房间幸运值大于等于最大房间幸运值
                if(gameRoom.getRoomNowLuckyNum() + roomAddLuckyNum >= gameRoom.getRoomLuckyNum()) {
                    // 重置房间和用户房间幸运值
                    got(resetLuckyNum(userNo, gameRoomNo, roomAddLuckyNum));
                    return;
                }

                // 累加用户房间幸运值
                userGameRoomService.addUserRoomLuckyNumByUserNoAndGameRoomNo(userNo, gameRoomNo, userLucKyNum);

                // 累加游戏房间幸运值
                gameRoomService.addRoomLuckyNumByGameRoomNo(gameRoomNo);

                got(getCatchFailReturn(roomAddLuckyNum));
                return;

            }
        }, "getGameCatchResultByUserNoAndGameRoomNo", json.toJSONString());
    }

    /**
     * 获得抓取失败返回值
     * @param roomAddLuckyNum 房间累加幸运值
     * @return
     */
    private String getCatchFailReturn(Integer roomAddLuckyNum) {

        JSONObject json = new JSONObject();
        // 抓取失败
        json.put("catch_result", BaseConstant.CATCH_FAIL);
        //
        json.put("addNum", roomAddLuckyNum);

        return json.toJSONString();
    }

    /**
     * 重置用户及游戏房间幸运值
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @param roomAddLuckyNum 房间累加幸运值
     * @return
     */
    private String resetLuckyNum(String userNo, String gameRoomNo, Integer roomAddLuckyNum) {

        // 重置用户房间幸运值
        CommonResult resetUserRoom = userGameRoomService.resetUserRoomLuckyNum(userNo, gameRoomNo);
        // 重置当前房间幸运值
        CommonResult resetGameRoom = gameRoomService.resetRoomLuckyNumByGameRoomNo(gameRoomNo);

        if(resetUserRoom.success() && resetGameRoom.success()) {

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

        } else {

            return getCatchFailReturn(roomAddLuckyNum);
        }
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
