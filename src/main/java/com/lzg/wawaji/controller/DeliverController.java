package com.lzg.wawaji.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.entity.Deliver;
import com.lzg.wawaji.service.DeliverService;
import com.lzg.wawaji.utils.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/wawaji/deliver")
@Controller
public class DeliverController {

    private static final Logger logger = LoggerFactory.getLogger(DeliverController.class);

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

        JSONObject json = new JSONObject();
        json.put("list", deliverService.getAllDeliverByPage(startPage));

        return json.toString();
    }


    /**
     * 获得总记录数和每页数据数
     * @return
     */
    @RequestMapping(value = "/getDeliverTotalCountAndPageSize", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getDeliverTotalCountAndPageSize() {

        return JSONUtil.getTotalCountAndPageSize(deliverService.countAllDeliver(), BaseConstant.DEFAULT_PAGE_SIZE);
    }

    /**
     * 添加发货记录
     * @param paramStr 机器记录
     * @return
     */
    @RequestMapping(value = "/addDeliver", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addDeliver(String paramStr) {
        try{
            deliverService.addDeliver(JSON.parseObject(paramStr, Deliver.class));
            return BaseConstant.SUCCESS;
        } catch(Exception e) {
            logger.error("{} addDeliver param:{} error "+e, BaseConstant.LOG_ERR_MSG, paramStr, e);
            return BaseConstant.FAIL;
        }
    }

    /**
     * 根据id和用户编号获得发货记录
     * @param id id
     * @param dataNo 用户编号
     * @return
     */
    @RequestMapping(value = "/getDeliverByIdAndUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getDeliverByIdAndUserNo(Long id, String dataNo) {
        return String.valueOf(deliverService.getDeliverByIdAndUserNo(id, dataNo));
    }

    /**
     * 根据id和用户编号修改发货记录
     * @param paramStr 发货记录
     * @return
     */
    @RequestMapping(value = "/updateDeliverMsgByIdAndUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateDeliverMsgByIdAndUserNo(String paramStr) {
        try{
            deliverService.updateDeliverMsgByIdAndUserNo(JSON.parseObject(paramStr, Deliver.class));
            return BaseConstant.SUCCESS;
        } catch(Exception e) {
            logger.error("{} updateDeliverMsgByIdAndUserNo param:{} error "+e, BaseConstant.LOG_ERR_MSG, paramStr, e);
            return BaseConstant.FAIL;
        }
    }

}
