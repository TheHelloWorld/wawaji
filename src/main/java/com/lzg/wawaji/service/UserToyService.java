package com.lzg.wawaji.service;

import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.entity.UserToy;

import java.util.List;

public interface UserToyService {

    /**
     * 添加用户娃娃记录
     * @param userToy 用户娃娃Bean
     */
    CommonResult addUserToy(UserToy userToy);

    /**
     * 根据用户编号获得用户玩具记录数
     * @param userNo 用户编号
     * @return
     */
    CommonResult<Integer> countUserToyByUserNo(String userNo);

    /**
     * 根据用户编号分页获得所有用户娃娃记录
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    CommonResult<List<UserToy>> getUserToyListByUserNo(String userNo, int startPage);

    /**
     * 根据用户编号和id获得用户娃娃记录
     * @param userNo 用户编号
     * @param id id
     * @return
     */
    CommonResult<UserToy> getUserToyByUserNoAndId(String userNo, Long id);

    /**
     * 根据id,用户编号修改选择方式
     * @param choiceType 用户选择方式
     * @param id id
     * @param userNo 用户编号
     */
    CommonResult updateChoiceTypeByIdAndUserNo(Integer choiceType, Long id, String userNo);

    /**
     * 根据用id,用户编号修改处理状态
     * @param handleStatus 处理状态
     * @param id id
     * @param userNo 用户编号
     */
    CommonResult updateHandleStatusByIdAndUserNo(Integer handleStatus, Long id, String userNo);
}
