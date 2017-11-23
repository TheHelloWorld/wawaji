package com.lzg.wawaji.service.impl;

import com.alibaba.fastjson.JSON;
import com.lzg.wawaji.bean.Callback;
import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.UserAddressDao;
import com.lzg.wawaji.entity.UserAddress;
import com.lzg.wawaji.service.UserAddressService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("all")
@Service("userAddressService")
public class UserAddressServiceImpl extends BaseServiceImpl implements UserAddressService {

    private static final Logger logger = LoggerFactory.getLogger(UserAddressServiceImpl.class);

    @Autowired
    private UserAddressDao userAddressDao;

    /**
     * 添加用户地址,若当前用户地址数量小于最大数量则添加否则不添加
     * @param userAddress 用户地址Bean
     * @return
     */
    @Override
    public CommonResult<String> addUserAddressService(final UserAddress userAddress) {

        return exec(new Callback() {
            @Override
            public void exec() {

                // 判断当前用户的地址数量是否超过最大用户地址数量
                if(userAddressDao.countUserAddressByUserNo(userAddress.getUserNo()) < BaseConstant.MAX_USER_ADDRESS) {
                    userAddressDao.addUserAddress(userAddress);

                    got(BaseConstant.SUCCESS);
                    return;
                }
                logger.info("{} userNO:{} 当前已有 {} 个地址",BaseConstant.LOG_MSG, BaseConstant.MAX_USER_ADDRESS,
                        userAddress.getUserNo());

                got(BaseConstant.OVER_MAX_USER_ADDRESS);
                return;
            }
        }, "addUserAddressService", JSON.toJSONString(userAddress));

    }

    /**
     * 根据用户编号获得用户地址list
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<List<UserAddress>> getUserAddressListByUserNo(final String userNo) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userAddressDao.getUserAddressByUserNo(userNo));
            }
        }, "getUserAddressByUserNo", json.toJSONString());
    }

    /**
     * 根据用户编号获得用户地址数量
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<Integer> countUserAddressByUserNo(final String userNo) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userAddressDao.countUserAddressByUserNo(userNo));
            }
        }, "countUserAddressByUserNo", json.toJSONString());
    }

    /**
     * 根据id,用户编号获得用户地址
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<UserAddress> getUserAddressByIdAndUserNo(final Long id, final String userNo) {

        JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("userNo",userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userAddressDao.getUserAddressByIdAndUserNo(id, userNo));
            }
        }, "getUserAddressByIdAndUserNo", json.toJSONString());

    }

    /**
     * 根据用户编号,id修改用户地址
     * @param userAddress 用户地址bean
     */
    @Override
    public CommonResult updateUserAddressByIdAndUserNo(final UserAddress userAddress) {

        return exec(new Callback() {
            @Override
            public void exec() {
                userAddressDao.updateUserAddressByIdAndUserNo(userAddress);
            }
        }, "updateUserAddressByIdAndUserNo", JSON.toJSONString(userAddress));

    }

    /**
     * 根据用户编号,id删除用户地址
     * @param id id
     * @param userNo 用户编号
     */
    @Override
    public CommonResult deleteUserAddressByIdAndUserNo(final Long id, final String userNo) {

        JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("userNo",userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                userAddressDao.deleteUserAddressByIdAndUserNo(id, userNo);
            }
        }, "deleteUserAddressByIdAndUserNo", json.toJSONString());
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
