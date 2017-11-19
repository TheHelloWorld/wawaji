package com.lzg.wawaji.dao;

import com.lzg.wawaji.entity.Deliver;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("deliverDao")
public interface DeliverDao {

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
     * 获得所有用户发货记录
     * @return
     */
    Integer countAllDeliver();

    /**
     * 根据用户编号分页获得用户发货记录集合
     * @param userNo 用户编号
     * @param startPage 开始页
     * @param pageSize 每页数据数
     * @return
     */
    List<Deliver> getDeliverByUserNo(@Param("userNo") String userNo, @Param("startPage") int startPage,
                                     @Param("pageSize") int pageSize);

    /**
     * 分页获得所有用户发货记录
     * @param startPage 开始也
     * @param pageSize 每页数据数
     * @return
     */
    List<Deliver> getAllDeliverByPage(@Param("startPage") int startPage,
                                     @Param("pageSize") int pageSize);


    /**
     * 根据id,用户编号获得用户发货记录
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    Deliver getDeliverByIdAndUserNo(@Param("id") Long id, @Param("userNo") String userNo);

    /**
     * 根据id,用户编号修改货物详情及状态
     * @param deliver 货物详情
     */
    void updateDeliverMsgByIdAndUserNo(Deliver deliver);

}
