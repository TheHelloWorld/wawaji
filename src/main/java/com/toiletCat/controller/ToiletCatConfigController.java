package com.toiletCat.controller;

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
     * @param toiletCatConfig 配置项
     */
    @RequestMapping(value = "/addToiletCatConfig", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addToiletCatConfig(ToiletCatConfig toiletCatConfig) {

        CommonResult result = toiletCatConfigService.addToiletCatConfig(toiletCatConfig);

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 修改配置项
     * @param toiletCatConfig 配置项
     */
    @RequestMapping(value = "/updateToiletCatConfig", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateToiletCatConfig(ToiletCatConfig toiletCatConfig) {

        CommonResult result = toiletCatConfigService.updateToiletCatConfig(toiletCatConfig);

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 删除配置项
     * @param toiletCatConfig 配置项
     */
    @RequestMapping(value = "/deleteToiletCatConfig", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteToiletCatConfig(ToiletCatConfig toiletCatConfig) {

        CommonResult result = toiletCatConfigService.deleteToiletCatConfig(toiletCatConfig);

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }
}
