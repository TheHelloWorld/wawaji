package com.toiletCat.service.impl;

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
import com.toiletCat.service.ToiletCatConfigService;
import com.toiletCat.service.UserToyService;
import com.toiletCat.utils.DateUtil;
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

    @Autowired
    private ToiletCatConfigService toiletCatConfigService;

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
        }, "addUserToy", userToy);
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
        },"countUserToyByUserNo", json);
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
        },"getUserToyByUserNo", json);
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
                // 邮寄娃娃所需游戏币数
                Integer deliverCoin = Integer.valueOf(toiletCatConfigService.getConfigByKey(
                        BaseConstant.USER_DELIVER_COIN));
                // 几个娃娃免费包邮
                Integer freeDeliverNum = Integer.valueOf(toiletCatConfigService.getConfigByKey(
                        BaseConstant.FREE_DELIVER_NUM));
                UserToy userToy = userToyDao.getUserToyByUserNoAndId(userNo, id);
                // 设置邮寄所需游戏币数
                userToy.setDeliverCoin(deliverCoin);
                // 设置几个娃娃免费包邮
                userToy.setFreeDeliverNum(freeDeliverNum);
                got(userToy);
            }
        },"getUserToyByUserNoAndId", json);
    }

    /**
     * 根据id,用户编号修改选择方式
     * @param userToy 用户玩具
     * @param userAddress 用户地址
     * @param toyNameArray 玩具名集合
     * @param userToyIdList 用户战利品id集合
     */
    @Override
    public CommonResult<String> updateChoiceTypeByIdAndUserNo(final UserToy userToy, final UserAddress userAddress,
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

                    // 几个娃娃免费包邮
                    Integer freeDeliverNum = Integer.valueOf(toiletCatConfigService.getConfigByKey(
                            BaseConstant.FREE_DELIVER_NUM));

                    // 若寄送娃娃少于包邮个数则扣除相应游戏币作为邮寄费
                    if(userToyIdList.size() < freeDeliverNum) {
                        // 获取邮寄费
                        Integer deliverCoin = Integer.valueOf(toiletCatConfigService.getConfigByKey(
                                BaseConstant.USER_DELIVER_COIN));

                        Integer userCoin = userDao.getUserCoinByUserNo(userNo);

                        if(userCoin < deliverCoin) {
                            setOtherMsg();
                            got(BaseConstant.NOT_ENOUGH_COIN);
                            return;
                        }

                        userDao.updateUserCoinByUserNo(-deliverCoin, userNo);

                        logger.info("updateChoiceTypeByIdAndUserNo 扣除用户邮寄费游戏币成功");
                        UserSpendRecord userSpendRecord = new UserSpendRecord();

                        Date tradeTime = new Date();

                        userSpendRecord.setTradeStatus(TradeStatus.SUCCESS.getStatus());

                        userSpendRecord.setTradeType(TradeType.DELIVER_COIN.getType());

                        userSpendRecord.setTradeTime(tradeTime);

                        userSpendRecord.setTradeDate(DateUtil.getDate());

                        userSpendRecord.setUserNo(userNo);

                        // 邮寄游戏币数
                        userSpendRecord.setCoin(deliverCoin);

                        // 订单号
                        String deliverOrderNo = BaseConstant.TOILER_CAT + tradeTime.getTime() + userNo;

                        // 设置订单号
                        userSpendRecord.setOrderNo(deliverOrderNo);

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

                    Date tradeTime = new Date();

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
                    userSpendRecord.setTradeTime(tradeTime);
                    // 交易状态 成功
                    userSpendRecord.setTradeStatus(TradeStatus.SUCCESS.getStatus());
                    // 交易类型 玩具兑换成游戏币
                    userSpendRecord.setTradeType(TradeType.TOY_FOR_COIN.getType());

                    // 订单号
                    String toyForCoinOrderNo = BaseConstant.TOILER_CAT + tradeTime.getTime() + userNo;

                    // 设置订单号
                    userSpendRecord.setOrderNo(toyForCoinOrderNo);


                    userSpendRecordDao.addUserSpendRecord(userSpendRecord);

                    userToyDao.updateChoiceTypeByIdAndUserNo(userToy);
                }

                Integer userCoin = userDao.getUserCoinByUserNo(userNo);

                JSONObject json = new JSONObject();

                json.put("userCoin",userCoin);

                got(json.toJSONString());

            }
        }, "updateChoiceTypeByIdAndUserNo", userToy);
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
        }, "getAllUnHandleUserToyByUserNo", json);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
