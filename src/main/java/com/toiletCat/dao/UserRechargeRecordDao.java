package com.toiletCat.dao;

import com.toiletCat.entity.UserRechargeRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository("userRechargeRecordDao")
public interface UserRechargeRecordDao {

    /**
     * 添加用户充值记录
     * @param userRechargeRecord 用户充值记录Bean
     */
    void addUserRechargeRecord(UserRechargeRecord userRechargeRecord);

    /**
     * 根据用户编号获得用户充值记录数
     * @param userNo 用户编号
     * @return
     */
    Integer countUserRechargeRecordByUserNo(String userNo);

    /**
     * 根据用户编号和交易状态获得用户充值记录数
     * @param userNo 用户编号
     * @param tradeStatus 交易状态
     * @return
     */
    Integer countUserRechargeRecordByUserNoAndTradeStatus(@Param("userNo") String userNo,
                                                          @Param("tradeStatus") Integer tradeStatus);

    /**
     * 根据用户编号分页获得用户充值记录
     * @param userNo 用户编号
     * @param startPage 开始页
     * @param pageSize 每页数量
     * @return
     */
    List<UserRechargeRecord> getUserRechargeRecordByUserNo(@Param("userNo") String userNo,
                                                           @Param("startPage") int startPage,
                                                           @Param("pageSize") int pageSize);

    /**
     * 根据交易日期和交易状态获得充值记录数量
     * @param tradeDate 交易日期
     * @param tradeStatus 交易状态
     * @return
     */
    Integer countUserRechargeRecordByTradeDateAndTradeStatus(@Param("tradeDate") Integer tradeDate,
                                                             @Param("tradeStatus") Integer tradeStatus);

    /**
     * 根据交易日期和交易状态分页获得所有充值记录
     * @param tradeDate 交易日期
     * @param tradeStatus 交易状态
     * @param startPage 开始页
     * @param pageSize 每页数量
     * @return
     */
    List<UserRechargeRecord> getUserRechargeRecordByTradeDateAndTradeStatus(@Param("tradeDate") Integer tradeDate,
                                                                            @Param("tradeStatus") Integer tradeStatus,
                                                                            @Param("startPage") int startPage,
                                                                            @Param("pageSize") int pageSize);

    /**
     * 根据交易日期和交易状态获得汇总信息
     * @param tradeDate 交易日期
     * @param tradeStatus 交易状态
     * @return
     */
    Map<String, Object> getSumRechargeAmountAndCountByTradeDateAndTradeStatus(@Param("tradeDate") Integer tradeDate,
                                                                               @Param("tradeStatus") Integer tradeStatus);

    /**
     * 根据用户编号和订单号获得金额
     * @param userNo 用户编号
     * @param orderNo 订单编号
     * @return
     */
    BigDecimal getAmountByUserNoAndOrderNo(@Param("userNo") String userNo, @Param("orderNo") String orderNo);

    /**
     * 根据订单编号修改交易状态
     * @param orderNo 订单编号
     * @param tradeStatus 交易状态
     */
    void updateTradeStatusByOrderNo(@Param("orderNo") String orderNo, @Param("tradeStatus") Integer tradeStatus);

    /**
     * 根据用户编号和订单号获得交易结果
     * @param userNo 用户编号
     * @param orderNo 订单编号
     * @return
     */
    Integer getTradeStatusByOrderNo(@Param("userNo") String userNo, @Param("orderNo") String orderNo);

    /**
     * 根据用户编号获得所有非终态订单
     * @param userNo 用户编号
     * @return
     */
    List<UserRechargeRecord> getAllInitRecordByUserNo(String userNo);
}
