package com.toiletCat.controller;

import com.alibaba.fastjson.JSON;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.entity.Deliver;
import com.toiletCat.service.DeliverService;
import com.toiletCat.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/toiletCat/deliver")
@Controller
public class DeliverController {

    @Autowired
    private DeliverService deliverService;

    /**
     * 分页获得所有用户发货记录
     * @param startPage 开始页
     * @return
     */
    @RequestMapping(value = "/getAllDeliverByPage", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllDeliverByPage(int startPage) {

        CommonResult<List<Deliver>> result = deliverService.getAllDeliverByPage(startPage);

        return JSONUtil.getReturnBeanString(result);
    }


    /**
     * 获得总记录数和每页数据数
     * @return
     */
    @RequestMapping(value = "/getDeliverTotalCountAndPageSize", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getDeliverTotalCountAndPageSize() {

        CommonResult<Integer> result = deliverService.countAllDeliver();

        return JSONUtil.getTotalCountAndPageSize(result, BaseConstant.DEFAULT_PAGE_SIZE);
    }

    /**
     * 添加发货记录
     * @param paramStr 机器记录
     * @return
     */
    @RequestMapping(value = "/addDeliver", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addDeliver(String paramStr) {

        CommonResult result = deliverService.addDeliver(JSON.parseObject(paramStr, Deliver.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 根据id和用户编号获得发货记录
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "/getDeliverByIdAndUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getDeliverByIdAndUserNo(Long id, String userNo) {

        CommonResult<Deliver> result = deliverService.getDeliverByIdAndUserNo(id, userNo);

        return JSONUtil.getReturnStrString(result, String.valueOf(result.getValue()));
    }

    /**
     * 根据id和用户编号修改发货记录
     * @param paramStr 发货记录
     * @return
     */
    @RequestMapping(value = "/updateDeliverMsgByIdAndUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateDeliverMsgByIdAndUserNo(String paramStr) {

        CommonResult result = deliverService.updateDeliverMsgByIdAndUserNo(JSON.parseObject(paramStr, Deliver.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

}
