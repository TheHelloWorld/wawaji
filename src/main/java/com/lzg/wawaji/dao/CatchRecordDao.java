package com.lzg.wawaji.dao;

import com.lzg.wawaji.bean.UserCatchRecord;
import com.lzg.wawaji.entity.CatchRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("catchRecordDao")
public interface CatchRecordDao {

    /**
     * 添加用户抓取记录
     * @param catchRecord 抓取记录
     */
    void addCatchRecord(CatchRecord catchRecord);

    /**
     * 根据玩具编号获得最近成功的抓取记录(10条)
     * @param toyNo 玩具编号
     * @return
     */
    List<UserCatchRecord> getLatelyCatchSuccessRecordByToyNo(String toyNo);

    /**
     * 根据用户编号获得用户抓取记录数量
     * @param userNo 用户编号
     * @return
     */
    Integer countCatchRecordByUserNo(String userNo);

    /**
     * 根据用户编号分页获得用户抓取记录集合
     * @param userNo 用户编号
     * @param startPage 开始页
     * @param pageSize 每页数据数
     * @return
     */
    List<CatchRecord> getCatchRecordListByUserNo(@Param("userNo") String userNo, @Param("startPage") int startPage,
                                     @Param("pageSize") int pageSize);

    /**
     * 根据用户编号,id获得用户抓取记录
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    CatchRecord getCatchRecordByUserNo(@Param("id") Long id, @Param("userNo") String userNo);

    /**
     * 根据id,用户编号修改抓取记录状态
     * @param catchStatus 抓去记录状态
     * @param id id
     * @param userNo 用户编号
     */
    void updateCatchStatusByIdAndUserNo(@Param("catchStatus") Integer catchStatus,
                                        @Param("id") Long id,
                                        @Param("userNo") String userNo);

}
