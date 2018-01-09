package com.toiletCat.controller;

import com.alibaba.fastjson.JSON;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.entity.ToiletCatConfig;
import com.toiletCat.service.ToiletCatConfigService;
import com.toiletCat.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/toiletCat/api/toiletCatConfig")
@Controller
public class ToiletCatConfigController {

    @Autowired
    private ToiletCatConfigService toiletCatConfigService;

    /**
     * 获得所有配置项数量
     * @return
     */
    @RequestMapping(value = "/getAllConfig", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllConfig() {

        CommonResult result = toiletCatConfigService.getAllConfig();

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 添加配置项
     * @param paramStr 配置项
     */
    @RequestMapping(value = "/addToiletCatConfig", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addToiletCatConfig(String paramStr) {

        CommonResult result = toiletCatConfigService.addToiletCatConfig(JSON.parseObject(paramStr,
                ToiletCatConfig.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 根据id获得配置项
     * @param id 主键id
     * @return
     */
    @RequestMapping(value = "/getToiletCatConfigById", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getToiletCatConfigById(Long id) {

        CommonResult result = toiletCatConfigService.getToiletCatConfigById(id);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 修改配置项
     * @param paramStr 配置项
     */
    @RequestMapping(value = "/updateToiletCatConfig", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateToiletCatConfig(String paramStr) {

        CommonResult result = toiletCatConfigService.updateToiletCatConfig(JSON.parseObject(paramStr,
                ToiletCatConfig.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 删除配置项
     * @param dataStr 配置项
     */
    @RequestMapping(value = "/deleteToiletCatConfig", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteToiletCatConfig(String dataStr) {

        CommonResult result = toiletCatConfigService.deleteToiletCatConfig(
                JSON.parseObject(dataStr, ToiletCatConfig.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }
}
