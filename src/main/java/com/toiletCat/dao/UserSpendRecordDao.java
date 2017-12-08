package com.toiletCat.dao;

import com.toiletCat.entity.UserSpendRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userSpendRecordDao")
public interface UserSpendRecordDao {

    /**
     * 添加用户消费记录
     * @param userSpendRecord 用户消费记录Bean
     */
    void addUserSpendRecord(UserSpendRecord userSpendRecord);

    /**
     * 根据用户编号获得所有用户消费记录
     * @param userNo 用户编号
     * @return
     */
    Integer countUserSpendRecordByUserNo(String userNo);

    /**
     * 根据用户编号分页获得用户消费记录
     * @param userNo 用户编号
     * @param startPage 开始页
     * @param pageSize 每页数量
     * @return
     */
    List<UserSpendRecord> getUserSpendRecordByUserNo(@Param("userNo") String userNo,
                                                           @Param("startPage") int startPage,
                                                           @Param("pageSize") int pageSize);
}
