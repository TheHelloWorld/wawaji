package com.toiletCat.service;

import com.toiletCat.bean.CommonResult;
import com.toiletCat.entity.Deliver;

import java.util.List;

public interface DeliverService {

    /**
     * 添加发货记录
     * @param deliver 发货记录
     */
    CommonResult addDeliver(Deliver deliver);

    /**
     * 根据用户编号获得用户发货记录数量
     * @param userNo 用户编号
     * @return
     */
    CommonResult<Integer> countDeliverByUserNo(String userNo);

    /**
     * 获得所有用户发货记录数量
     * @return
     */
    CommonResult<Integer> countAllDeliver();


    /**
     * 根据用户编号分页获得用户发货记录集合
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    CommonResult<List<Deliver>> getDeliverByUserNo(String userNo, int startPage);

    /**
     * 分页获得所有用户发货记录
     * @param startPage 开始页
     * @return
     */
    CommonResult<List<Deliver>> getAllDeliverByPage(int startPage);

    /**
     * 根据id,用户编号获得用户发货记录
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    CommonResult<Deliver> getDeliverByIdAndUserNo(Long id, String userNo);

    /**
     * 根据id,用户编号修改货物详情及状态
     * @param deliver 货物详情
     */
    CommonResult updateDeliverMsgByIdAndUserNo(Deliver deliver);

}
