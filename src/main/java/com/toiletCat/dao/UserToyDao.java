package com.toiletCat.dao;

import com.toiletCat.entity.UserToy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userToyDao")
public interface UserToyDao {

    /**
     * 添加用户娃娃记录
     * @param userToy 用户娃娃Bean
     */
    void addUserToy(UserToy userToy);

    /**
     * 根据用户编号获得用户玩具记录数
     * @param userNo 用户编号
     * @return
     */
    Integer countUserToyByUserNo(String userNo);

    /**
     * 根据用户编号分页获得所有用户娃娃记录集合
     * @param userNo 用户编号
     * @param startPage 开始页
     * @param pageSize 每页数据数
     * @return
     */
    List<UserToy> getUserToyListByUserNo(@Param("userNo") String userNo, @Param("startPage") int startPage,
                                     @Param("pageSize") int pageSize);

    /**
     * 根据用户编号和id获得用户娃娃记录
     * @param userNo 用户编号
     * @param id id
     * @return
     */
    UserToy getUserToyByUserNoAndId(@Param("userNo") String userNo, @Param("id") Long id);

    /**
     * 根据id,用户编号修改选择方式及其送货编号
     * @param userToy 用户娃娃
     */
    void updateChoiceTypeByIdAndUserNo(UserToy userToy);

    /**
     * 根据用户编号获得用户所有未处理战利品
     * @param userNo 用户编号
     * @return
     */
    List<UserToy> getAllUnHandleUserToyByUserNo(String userNo);

    List<UserToy> getLimitUserToyListByUserNoandToyNo(@Param("userNo") String userNo, @Param("toyNo") String toyNo);
}
