package com.lzg.wawaji.service.impl;

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
    public String addUserAddressService(UserAddress userAddress) {
        try {
            // 判断当前用户的地址数量是否超过最大用户地址数量
            if(userAddressDao.countUserAddressByUserNo(userAddress.getUserNo()) < BaseConstant.MAX_USER_ADDRESS) {
                userAddressDao.addUserAddress(userAddress);

                return BaseConstant.SUCCESS;
            }
            logger.info("{}userNO:{}当前已有{}个地址",BaseConstant.LOG_MSG, BaseConstant.MAX_USER_ADDRESS,
                    userAddress.getUserNo());
            return BaseConstant.OVER_MAX_USER_ADDRESS;
        }catch (Exception e) {
            logger.error("{} addUserAddressService param:{} error "+ e,BaseConstant.LOG_ERR_MSG, userAddress, e);
            return BaseConstant.SYSTEM_ERROR;
        }


    }

    /**
     * 根据用户编号获得用户地址list
     * @param userNo 用户编号
     * @return
     */
    @Override
    public List<UserAddress> getUserAddressByUserNo(String userNo) {
        try{
            return userAddressDao.getUserAddressByUserNo(userNo);
        }catch (Exception e) {
            logger.error("{} getUserAddressByUserNo userNo:{} error "+ e, BaseConstant.LOG_ERR_MSG, userNo, e);
        }
        return null;
    }

    /**
     * 根据用户编号获得用户地址数量
     * @param userNo 用户编号
     * @return
     */
    @Override
    public Integer countUserAddressByUserNo(String userNo) {
        try{
            return userAddressDao.countUserAddressByUserNo(userNo);
        }catch (Exception e) {
            logger.error("{} countUserAddressByUserNo userNo:{} error "+ e, BaseConstant.LOG_ERR_MSG, userNo, e);
        }
        return null;
    }

    /**
     * 根据id,用户编号获得用户地址
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    @Override
    public UserAddress getUserAddressByIdAndUserNo(Long id, String userNo) {
        try{
            return userAddressDao.getUserAddressByIdAndUserNo(id, userNo);
        }catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("id",id);
            json.put("userNo",userNo);
            logger.error("{} getUserAddressByIdAndUserNo param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
        }
        return null;

    }

    /**
     * 根据用户编号,id修改用户地址
     * @param userAddress 用户地址bean
     */
    @Override
    public void updateUserAddressByIdAndUserNo(UserAddress userAddress) {
        try{
            userAddressDao.updateUserAddressByIdAndUserNo(userAddress);
        }catch (Exception e) {
            logger.error("{} updateUserAddressByIdAndUserNo param:{} error "+ e, BaseConstant.LOG_ERR_MSG, userAddress, e);
        }

    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
