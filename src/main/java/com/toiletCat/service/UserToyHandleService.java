package com.toiletCat.service;

import com.toiletCat.bean.CommonResult;
import com.toiletCat.entity.UserToyHandle;

import java.util.List;

public interface UserToyHandleService {

    /**
     * 添加用户战利品处理记录
     * @param userToyHandle 用户战利品处理Bean
     */
    CommonResult addUserToyHandle(UserToyHandle userToyHandle);

    /**
     * 根据用户编号获得用户玩具战利品处理数
     * @param userNo 用户编号
     * @return
     */
    CommonResult<Integer> countUserToyHandleByUserNo(String userNo);

    /**
     * 根据用户编号分页获得所有用户战利品处理记录集合
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    CommonResult<List<UserToyHandle>> getUserToyHandleListByUserNo(String userNo, int startPage);

    /**
     * 根据用户编号和id获得用户战利品处理记录
     * @param userNo 用户编号
     * @param id id
     * @return
     */
    CommonResult<UserToyHandle> getUserToyHandleByUserNoAndId(String userNo, Long id);
}
