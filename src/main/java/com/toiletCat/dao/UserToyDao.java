package com.toiletCat.dao;

import com.toiletCat.entity.UserToy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userToyDao")
public interface UserToyDao {

    /**
     * 添加用户战利品记录
     * @param userToy 用户战利品Bean
     */
    void addUserToy(UserToy userToy);

    /**
     * 根据用户编号获得用户战利品记录数
     * @param userNo 用户编号
     * @return
     */
    Integer countUserToyByUserNo(String userNo);

    /**
     * 根据用户编号分页获得所有用户战利品记录集合
     * @param userNo 用户编号
     * @param startPage 开始页
     * @param pageSize 每页数据数
     * @return
     */
    List<UserToy> getUserToyListByUserNo(@Param("userNo") String userNo, @Param("startPage") int startPage,
                                     @Param("pageSize") int pageSize);

    /**
     * 根据用户编号和玩具编号获得用户战利品记录
     * @param userNo 用户编号
     * @param toyNo 玩具编号
     * @return
     */
    UserToy getUserToyByUserNoAndToyNo(@Param("userNo") String userNo, @Param("toyNo") String toyNo);

    /**
     * 根据id,用户编号修改选择方式及其送货编号
     * @param userToy 用户战利品
     */
    void updateChoiceTypeByIdAndUserNo(UserToy userToy);

    /**
     * 根据用户编号获得用户所有未处理战利品
     * @param userNo 用户编号
     * @return
     */
    List<UserToy> getAllUnHandleUserToyByUserNo(String userNo);

    /**
     * 根据用户编号玩具编号及数量获得用户战利品id集合
     * @param userNo 用户编号
     * @param toyNo 玩具编号
     * @param limit 数量
     * @return
     */
    List<Long> getLimitUserToyIdListByUserNoAndToyNo(@Param("userNo") String userNo, @Param("toyNo") String toyNo,
                                                      @Param("limit") Integer limit);

    /**
     * 根据用户编号玩具编号获得待处理用户战利品数量
     * @param userNo 用户编号
     * @param toyNo 玩具编号
     * @return
     */
    Integer countUserToyNumByUserNoAndToyNo(@Param("userNo") String userNo, @Param("toyNo") String toyNo);
}
