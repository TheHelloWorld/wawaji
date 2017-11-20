package com.lzg.wawaji.controller;

import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.entity.UserAddress;
import com.lzg.wawaji.service.UserAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/wawaji/userAddress")
@Controller
public class UserAddressController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
        JSONObject json = new JSONObject();
        json.put("list", userAddressService.getUserAddressByUserNo(userNo));

        return json.toJSONString();
    }

    /**
     * 添加用户地址
     * @param userAddress 用户地址
     * @return
     */
    @RequestMapping(value = "/addUserAddress", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addUserAddress(UserAddress userAddress) {
        return userAddressService.addUserAddressService(userAddress);
    }

    /**
     * 判断当前用户是否还能继续添加地址
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "/judgeUserAddressIsMaxNum", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String judgeUserAddressIsMaxNum(String userNo) {
        if(BaseConstant.MAX_USER_ADDRESS.equals(userAddressService.countUserAddressByUserNo(userNo))) {
            return BaseConstant.FAIL;
        }
        return BaseConstant.SUCCESS;
    }


}
