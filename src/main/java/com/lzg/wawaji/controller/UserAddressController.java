package com.lzg.wawaji.controller;

import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.entity.UserAddress;
import com.lzg.wawaji.service.UserAddressService;
import com.lzg.wawaji.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/wawaji/userAddress")
@Controller
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    /**
     * 根据用户编号获得用户地址
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "/getUserAddressByUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserAddressByUserNo(String userNo) {

        CommonResult<List<UserAddress>> result = userAddressService.getUserAddressByUserNo(userNo);

        if(result.success()) {
            JSONObject json = new JSONObject();
            json.put("result", BaseConstant.SUCCESS);
            json.put("list", result.getValue());
            return json.toJSONString();
        }

        return JSONUtil.getErrorJson();
    }

    /**
     * 添加用户地址
     * @param userAddress 用户地址
     * @return
     */
    @RequestMapping(value = "/addUserAddress", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addUserAddress(UserAddress userAddress) {

        CommonResult result = userAddressService.addUserAddressService(userAddress);
        if(result.success()) {
            return BaseConstant.SUCCESS;
        }

        return BaseConstant.FAIL;
    }

    /**
     * 判断当前用户是否还能继续添加地址
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "/judgeUserAddressIsMaxNum", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String judgeUserAddressIsMaxNum(String userNo) {

        CommonResult<Integer> result = userAddressService.countUserAddressByUserNo(userNo);

        if(result.success()) {
            if(BaseConstant.MAX_USER_ADDRESS > result.getValue()) {
                return BaseConstant.SUCCESS;
            }
        }
        return BaseConstant.FAIL;
    }

}
