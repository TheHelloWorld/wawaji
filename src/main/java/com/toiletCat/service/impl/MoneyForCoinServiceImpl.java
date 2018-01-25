package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.dao.BannerImgDao;
import com.toiletCat.dao.MoneyForCoinDao;
import com.toiletCat.entity.BannerImg;
import com.toiletCat.entity.MoneyForCoin;
import com.toiletCat.enums.BannerType;
import com.toiletCat.enums.CurrentState;
import com.toiletCat.service.BannerImgService;
import com.toiletCat.service.MoneyForCoinService;
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
                BaseConstant.moneyForCoinMap.put(moneyForCoin.getMoney(), moneyForCoin);
            }
        }, "addMoneyForCoin", JSON.toJSONString(moneyForCoin));
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
        }, "getAllMoneyForCoin", new JSONObject().toJSONString());
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
        }, "getMoneyForCoinById", json.toJSONString());
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

                // 若为不可用则去除key
                if(moneyForCoin.getCurrentState() == CurrentState.UNAVAILABLE.getStatus()) {

                    BaseConstant.moneyForCoinMap.remove(moneyForCoin.getMoney());

                } else {

                    BaseConstant.moneyForCoinMap.put(moneyForCoin.getMoney(), moneyForCoin);
                }

            }
        }, "updateMoneyForCoin", JSON.toJSONString(moneyForCoin));
    }

    /**
     * 获得所有可用的对应关系
     * @return
     */
    @Override
    public CommonResult<List<MoneyForCoin>> getAllCanSeeMoneyForCoin() {
        return exec(new Callback() {
            @Override
            public void exec() {
                // 初始化Map
                initMap();

                List<MoneyForCoin> list = new ArrayList<>();

                for(Map.Entry<String, MoneyForCoin> entry : BaseConstant.moneyForCoinMap.entrySet()) {
                    list.add(entry.getValue());
                }


                got(list);
            }
        }, "getAllCanSeeMoneyForCoin", new JSONObject().toJSONString());
    }

    /**
     * 根据钱数获得对应的对应关系
     * @param money 钱数
     * @return
     */
    @Override
    public MoneyForCoin getMoneyForCoinByMoney(String money) {

        if(BaseConstant.moneyForCoinMap.get(money) == null) {

            refreshMap();
        }

        return BaseConstant.moneyForCoinMap.get(money);
    }

    /**
     * 初始化Map
     */
    private void initMap() {

        if(BaseConstant.moneyForCoinMap.isEmpty()) {

            refreshMap();
        }
    }

    /**
     * 刷新对应关系Map
     */
    private void refreshMap() {

        // 获得所有可用的对应关系
        List<MoneyForCoin> list = moneyForCoinDao.getAllCanSeeMoneyForCoin();

        for(MoneyForCoin moneyForCoin : list) {

            BaseConstant.moneyForCoinMap.put(moneyForCoin.getMoney(), moneyForCoin);
        }
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
