package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.dao.DeliverDao;
import com.toiletCat.dao.UserDao;
import com.toiletCat.dao.UserSpendRecordDao;
import com.toiletCat.dao.UserToyDao;
import com.toiletCat.entity.Deliver;
import com.toiletCat.entity.UserAddress;
import com.toiletCat.entity.UserSpendRecord;
import com.toiletCat.entity.UserToy;
import com.toiletCat.enums.*;
import com.toiletCat.service.UserService;
import com.toiletCat.service.UserSpendRecordService;
import com.toiletCat.service.UserToyService;
import com.toiletCat.utils.DateUtil;
import com.toiletCat.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@SuppressWarnings("all")
@Service("userToyService")
public class UserToyServiceImpl extends BaseServiceImpl implements UserToyService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserToyServiceImpl.class);

    @Autowired
    private UserToyDao userToyDao;

    @Autowired
    private DeliverDao deliverDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserSpendRecordDao userSpendRecordDao;

    /**
     * 添加用户娃娃记录
     * @param userToy 用户娃娃Bean
     */
    @Override
    public CommonResult addUserToy(final UserToy userToy) {

        return exec(new Callback() {
            @Override
            public void exec() {
                userToy.setDeliverId(0L);
                userToyDao.addUserToy(userToy);
            }
        }, "addUserToy", JSON.toJSONString(userToy));
    }

    /**
     * 根据用户编号获得用户玩具记录数
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<Integer> countUserToyByUserNo(final String userNo) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userToyDao.countUserToyByUserNo(userNo));
            }
        },"countUserToyByUserNo", json.toJSONString());
    }

    /**
     * 根据用户编号分页获得所有用户娃娃记录
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<UserToy>> getUserToyListByUserNo(final String userNo, final int startPage) {

        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("startPage", startPage);
        json.put("pageSize", BaseConstant.DEFAULT_PAGE_SIZE);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userToyDao.getUserToyListByUserNo(userNo, startPage, BaseConstant.DEFAULT_PAGE_SIZE));
            }
        },"getUserToyByUserNo", json.toJSONString());
    }

    /**
     * 根据用户编号和id获得用户娃娃记录
     * @param userNo 用户编号
     * @param id id
     * @return
     */
    @Override
    public CommonResult<UserToy> getUserToyByUserNoAndId(final String userNo, final Long id) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("id", id);

        return exec(new Callback() {
            @Override
            public void exec() {
                PropertiesUtil systemProperties = PropertiesUtil.getInstance("system");
                Integer deliverCoin = Integer.valueOf(systemProperties.getProperty("user_deliver_coin"));
                UserToy userToy = userToyDao.getUserToyByUserNoAndId(userNo, id);
                // 设置邮寄所需游戏币数
                userToy.setDeliverCoin(deliverCoin);
                got(userToy);
            }
        },"getUserToyByUserNoAndId", json.toJSONString());
    }

    /**
     * 根据id,用户编号修改选择方式
     * @param userToy 用户玩具
     * @param userAddress 用户地址
     * @param toyNameArray 玩具名集合
     * @param userToyIdList 用户战利品id集合
     */
    @Override
    public CommonResult updateChoiceTypeByIdAndUserNo(final UserToy userToy, final UserAddress userAddress,
                                                      final String toyNameArray, final List<Long> userToyIdList) {

        return exec(new Callback() {
            @Override
            public void exec() {

                // 选择类型
                Integer choiceType = userToy.getChoiceType();
                // 用户编号
                String userNo = userToy.getUserNo();


                if(ChoiceType.FOR_DELIVER.getStatus() == choiceType) {

                    Deliver deliver = new Deliver();
                    deliver.setToyNameArray(toyNameArray);
                    deliver.setUserNo(userNo);
                    deliver.setAddress(userAddress.getAddress());
                    deliver.setMobileNo(userAddress.getMobileNo());
                    deliver.setUserName(userAddress.getUserName());
                    deliver.setDeliverStatus(DeliverStatus.INIT.getStatus());

                    deliverDao.addDeliver(deliver);

                    // 若寄送娃娃少于3个则扣除相应游戏币作为邮寄费
                    if(userToyIdList.size() < 3) {
                        // 获取邮寄费
                        PropertiesUtil systemProperties = PropertiesUtil.getInstance("system");
                        Integer deliverCoin = Integer.valueOf(systemProperties.getProperty("user_deliver_coin"));
                        userDao.updateUserCoinByUserNo(-deliverCoin, userNo);

                        logger.info("updateChoiceTypeByIdAndUserNo 扣除用户邮寄费游戏币成功");
                        UserSpendRecord userSpendRecord = new UserSpendRecord();

                        userSpendRecord.setTradeStatus(TradeStatus.SUCCESS.getStatus());
                        userSpendRecord.setTradeType(TradeType.DELIVER_COIN.getType());
                        userSpendRecord.setTradeTime(new Date());
                        userSpendRecord.setTradeDate(DateUtil.getDate());
                        userSpendRecord.setUserNo(userNo);
                        userSpendRecord.setCoin(deliverCoin);

                        userSpendRecordDao.addUserSpendRecord(userSpendRecord);

                        logger.info("updateChoiceTypeByIdAndUserNo 添加用户消费记录成功");
                    }

                    for(Long id : userToyIdList) {

                        UserToy nowUserToy = new UserToy();
                        nowUserToy.setDeliverId(deliver.getId());
                        nowUserToy.setId(id);
                        nowUserToy.setUserNo(userNo);
                        nowUserToy.setChoiceType(choiceType);
                        userToyDao.updateChoiceTypeByIdAndUserNo(nowUserToy);
                    }

                } else if(ChoiceType.FOR_COIN.getStatus() == choiceType) {
                    // 添加相应的游戏币给用户
                    userDao.updateUserCoinByUserNo((userToy.getToyForCoin()), userNo);

                    UserSpendRecord userSpendRecord = new UserSpendRecord();
                    // 用户编号
                    userSpendRecord.setUserNo(userNo);
                    // 玩具兑换游戏币数
                    userSpendRecord.setCoin(userToy.getToyForCoin());
                    // 交易日期
                    userSpendRecord.setTradeDate(DateUtil.getDate());
                    // 交易时间
                    userSpendRecord.setTradeTime(new Date());
                    // 交易状态 成功
                    userSpendRecord.setTradeStatus(TradeStatus.SUCCESS.getStatus());
                    // 交易类型 玩具兑换成游戏币
                    userSpendRecord.setTradeType(TradeType.TOY_FOR_COIN.getType());

                    userSpendRecordDao.addUserSpendRecord(userSpendRecord);

                    userToyDao.updateChoiceTypeByIdAndUserNo(userToy);
                }



            }
        }, "updateChoiceTypeByIdAndUserNo", JSON.toJSONString(userToy));
    }

    /**
     * 根据用户编号获得用户所有未处理战利品
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<List<UserToy>> getAllUnHandleUserToyByUserNo(final String userNo) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userToyDao.getAllUnHandleUserToyByUserNo(userNo));
            }
        }, "getAllUnHandleUserToyByUserNo", json.toJSONString());
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
