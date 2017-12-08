package com.toiletCat.controller;

import com.alibaba.fastjson.JSON;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.entity.UserAddress;
import com.toiletCat.service.UserAddressService;
import com.toiletCat.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/toiletCat/userAddress")
@Controller
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    /**
     * 根据用户编号获得用户地址list
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "/getUserAddressListByUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserAddressListByUserNo(String userNo) {

        CommonResult<List<UserAddress>> result = userAddressService.getUserAddressListByUserNo(userNo);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 根据id,用户编号获得用户地址
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "/getUserAddressByIdAndUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserAddressByIdAndUserNo(Long id, String userNo) {

        CommonResult<UserAddress> result = userAddressService.getUserAddressByIdAndUserNo(id, userNo);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 添加用户地址
     * @param paramStr 用户地址
     * @return
     */
    @RequestMapping(value = "/addUserAddress", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addUserAddress(String paramStr) {

        CommonResult result = userAddressService.addUserAddressService(JSON.parseObject(paramStr, UserAddress.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
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

                return JSONUtil.getSuccessReturnJSON(BaseConstant.SUCCESS);
            } else {
                return JSONUtil.getSuccessReturnJSON(BaseConstant.FAIL);
            }
        }
        return JSONUtil.getErrorJson();
    }

    /**
     * 根据id和用户编号删除用户地址记录
     * @param id id
     * @param userNo 机器编号
     * @return
     */
    @RequestMapping(value = "/deleteUserAddressByIdAndUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteUserAddressByIdAndUserNo(Long id, String userNo) {

        CommonResult result = userAddressService.deleteUserAddressByIdAndUserNo(id, userNo);

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 根据id和用户编号修改用户地址
     * @param paramStr 用户地址
     * @return
     */
    @RequestMapping(value = "/updateUserAddressByIdAndUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateUserAddressByIdAndUserNo(String paramStr) {

        CommonResult result =  userAddressService.updateUserAddressByIdAndUserNo(JSON.parseObject(paramStr, UserAddress.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

}
