package com.lzg.wawaji.service;

import com.lzg.wawaji.entity.Deliver;

import java.util.List;

public interface DeliverService {

    /**
     * 添加发货记录
     * @param deliver 发货记录
     */
    void addDeliver(Deliver deliver);

    /**
     * 根据用户编号获得用户发货记录数量
     * @param userNo 用户编号
     * @return
     */
    Integer countDeliverByUserNo(String userNo);

    /**
     * 根据用户编号分页获得用户发货记录集合
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    List<Deliver> getDeliverByUserNo(String userNo, int startPage);

    /**
     * 根据id,用户编号获得用户发货记录
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    Deliver getDeliverByIdAndUserNo(Long id, String userNo);

    /**
     * 根据id,用户编号修改货物详情及状态
     * @param deliver 货物详情
     */
    void updateDeliverMsgByIdAndUserNo(Deliver deliver);

}
