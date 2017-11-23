package com.lzg.wawaji.dao;

import com.lzg.wawaji.entity.UserAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userAddressDao")
public interface UserAddressDao {

    /**
     * 添加用户地址记录
     * @param userAddress 用户地址
     */
    void addUserAddress(UserAddress userAddress);

    /**
     * 根据用户编号获得用户地址数量
     * @param userNo 用户编号
     * @return
     */
    Integer countUserAddressByUserNo(String userNo);

    /**
     * 根据用户编号获得用户地址集合
     * @param userNo 用户编号
     * @return
     */
    List<UserAddress> getUserAddressByUserNo(String userNo);

    /**
     * 根据id,用户编号获得用户地址
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    UserAddress getUserAddressByIdAndUserNo(@Param("id") Long id, @Param("userNo") String userNo);

    /**
     * 根据id修改地址
     * @param userAddress 用户地址Bean
     */
    void updateUserAddressByIdAndUserNo(UserAddress userAddress);

    /**
     * 根据用户编号,id删除用户地址
     * @param id id
     * @param userNo 用户编号
     */
    void deleteUserAddressByIdAndUserNo(@Param("id") Long id, @Param("userNo") String userNo);

}
