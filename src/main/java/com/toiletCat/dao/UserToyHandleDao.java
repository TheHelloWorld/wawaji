package com.toiletCat.dao;

import com.toiletCat.entity.UserToyHandle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userToyHandleDao")
public interface UserToyHandleDao {

    /**
     * 添加用户战利品处理记录
     * @param userToyHandle 用户战利品处理Bean
     */
    void addUserToyHandle(UserToyHandle userToyHandle);

    /**
     * 根据用户编号获得用户玩具战利品处理数
     * @param userNo 用户编号
     * @return
     */
    Integer countUserToyHandleByUserNo(String userNo);

    /**
     * 根据用户编号分页获得所有用户战利品处理记录集合
     * @param userNo 用户编号
     * @param startPage 开始页
     * @param pageSize 每页数据数
     * @return
     */
    List<UserToyHandle> getUserToyHandleListByUserNo(@Param("userNo") String userNo, @Param("startPage") int startPage,
                                         @Param("pageSize") int pageSize);

    /**
     * 根据用户编号和id获得用户战利品处理记录
     * @param userNo 用户编号
     * @param id id
     * @return
     */
    UserToyHandle getUserToyHandleByUserNoAndId(@Param("userNo") String userNo, @Param("id") Long id);

}
