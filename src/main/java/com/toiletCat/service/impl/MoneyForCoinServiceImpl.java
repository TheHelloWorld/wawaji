package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.dao.MoneyForCoinDao;
import com.toiletCat.entity.MoneyForCoin;
import com.toiletCat.enums.CurrentState;
import com.toiletCat.service.MoneyForCoinService;
import com.toiletCat.service.RechargeService;
import com.toiletCat.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service("moneyForCoinService")
public class MoneyForCoinServiceImpl extends BaseServiceImpl implements MoneyForCoinService {

    private static final Logger logger = LoggerFactory.getLogger(MoneyForCoinServiceImpl.class);

    @Autowired
    private MoneyForCoinDao moneyForCoinDao;

    @Autowired
    private RechargeService rechargeService;

    /**
     * 添加对应关系
     * @param moneyForCoin bean
     */
    @Override
    public CommonResult addMoneyForCoin(final MoneyForCoin moneyForCoin) {
        return exec(new Callback() {
            @Override
            public void exec() {
                moneyForCoinDao.addMoneyForCoin(moneyForCoin);
                BaseConstant.moneyForCoinMap.put(Double.valueOf(moneyForCoin.getMoney()), moneyForCoin);
            }
        }, "addMoneyForCoin", moneyForCoin);
    }

    /**
     * 获得所有对应关系
     * @return
     */
    @Override
    public CommonResult<List<MoneyForCoin>> getAllMoneyForCoin() {
        return exec(new Callback() {
            @Override
            public void exec() {
                got(moneyForCoinDao.getAllMoneyForCoin());
            }
        }, "getAllMoneyForCoin", new JSONObject());
    }

    /**
     * 根据id获得对应关系
     * @param id 主键id
     * @return
     */
    @Override
    public CommonResult<MoneyForCoin> getMoneyForCoinById(final Long id) {
        JSONObject json = new JSONObject();
        json.put("id", id);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(moneyForCoinDao.getMoneyForCoinById(id));
            }
        }, "getMoneyForCoinById", json);
    }

    /**
     * 修改对应关系
     * @param moneyForCoin bean
     */
    @Override
    public CommonResult updateMoneyForCoin(final MoneyForCoin moneyForCoin) {
        return exec(new Callback() {
            @Override
            public void exec() {

                moneyForCoinDao.updateMoneyForCoin(moneyForCoin);

                // 刷新配置
                refreshMap();

            }
        }, "updateMoneyForCoin", moneyForCoin);
    }

    /**
     * 获得所有可用的对应关系
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<List<MoneyForCoin>> getAllCanSeeMoneyForCoin(final String userNo) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                // 初始化Map
                initMap();

                List<MoneyForCoin> list = new ArrayList<>();


                for(Map.Entry<Double, MoneyForCoin> entry : BaseConstant.moneyForCoinMap.entrySet()) {

                    // 若为首充选项
                    if(entry.getValue().getFirstFlag() != 0) {
                        // 获得用户首充标志位
                        entry.getValue().setUserFirstFlag(rechargeService.getFirstFlag(userNo));
                    }

                    // 若为限充选项
                    if(entry.getValue().getRechargeLimit() != 0) {
                        // 获得用户当前限充数量
                        entry.getValue().setUserLimitFlag(rechargeService.getLimitRechargeByUserNo(userNo,
                                entry.getValue()));
                    }

                    list.add(entry.getValue());
                }



                got(list);

            }
        }, "getAllCanSeeMoneyForCoin", json);
    }

    /**
     * 根据钱数获得对应的对应关系
     * @param money 钱数
     * @return
     */
    @Override
    public MoneyForCoin getMoneyForCoinByMoney(String money) {

        logger.info("getMoneyForCoinByMoney money:" + money);

        if(BaseConstant.moneyForCoinMap.get(Double.valueOf(money)) == null) {

            refreshMap();
        }

        return BaseConstant.moneyForCoinMap.get(Double.valueOf(money));
    }

    /**
     * 初始化Map
     */
    private void initMap() {

        // 获得当前可用对应关系数量
        Integer count = moneyForCoinDao.countAvailableMoneyForCoin();

        if(BaseConstant.moneyForCoinMap.size() != count) {

            refreshMap();
        }
    }

    /**
     * 刷新对应关系Map
     */
    private void refreshMap() {

        // 获得所有可用的对应关系
        List<MoneyForCoin> list = moneyForCoinDao.getAllCanSeeMoneyForCoin();

        // 清空Map
        BaseConstant.moneyForCoinMap.clear();

        for(MoneyForCoin moneyForCoin : list) {

            BaseConstant.moneyForCoinMap.put(Double.valueOf(moneyForCoin.getMoney()), moneyForCoin);
        }
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

}
