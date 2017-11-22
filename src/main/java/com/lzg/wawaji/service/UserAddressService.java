package com.lzg.wawaji.service;

import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.entity.UserAddress;

import java.util.List;

public interface UserAddressService {

    /**
     * 添加用户地址记录
     * @param userAddress 用户地址Bean
     */
    CommonResult<String> addUserAddressService(UserAddress userAddress);

    /**
     * 根据用户编号获得用户地址
     * @param userNo 用户编号
     * @return
     */
    CommonResult<List<UserAddress>> getUserAddressByUserNo(String userNo);

    /**
     * 根据用户编号获得用户地址数量
     * @param userNo 用户编号
     * @return
     */
    CommonResult<Integer> countUserAddressByUserNo(String userNo);

    /**
     * 根据id,用户编号获得用户地址
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    CommonResult<UserAddress> getUserAddressByIdAndUserNo(Long id, String userNo);

    /**
     * 根据用户编号,id修改用户地址
     * @param userAddress 用户地址bean
     */
    CommonResult updateUserAddressByIdAndUserNo(UserAddress userAddress);
}
