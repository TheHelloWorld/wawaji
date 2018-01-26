package com.toiletCat.controller;

import com.alibaba.fastjson.JSON;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.entity.MoneyForCoin;
import com.toiletCat.service.MoneyForCoinService;
import com.toiletCat.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/toiletCat/api/moneyForCoin")
@Controller
public class MoneyForCoinController {

    @Autowired
    private MoneyForCoinService moneyForCoinService;

    /**
     * 获得所有对应关系
     * @return
     */
    @RequestMapping(value = "/getAllMoneyForCoin", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllMoneyForCoin() {

        CommonResult<List<MoneyForCoin>> result = moneyForCoinService.getAllMoneyForCoin();

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 添加对应关系
     * @param paramStr bean
     */
    @RequestMapping(value = "/addMoneyForCoin", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addMoneyForCoin(String paramStr) {

        CommonResult result = moneyForCoinService.addMoneyForCoin(JSON.parseObject(paramStr, MoneyForCoin.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 根据id获得对应关系
     * @param id 主键id
     * @return
     */
    @RequestMapping(value = "/getMoneyForCoinById", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMoneyForCoinById(Long id) {

        CommonResult<MoneyForCoin> result = moneyForCoinService.getMoneyForCoinById(id);

        return JSONUtil.getReturnStrString(result, String.valueOf(result.getValue()));
    }

    /**
     * 修改对应关系
     * @param paramStr bean
     */
    @RequestMapping(value = "/updateMoneyForCoin", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateMoneyForCoin(String paramStr) {

        CommonResult result = moneyForCoinService.updateMoneyForCoin(JSON.parseObject(paramStr, MoneyForCoin.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 获得所有可用的对应关系
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "/getAllCanSeeMoneyForCoin", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllCanSeeMoneyForCoin(String userNo) {

        CommonResult<List<MoneyForCoin>> result = moneyForCoinService.getAllCanSeeMoneyForCoin(userNo);

        return JSONUtil.getReturnBeanString(result);
    }

}
