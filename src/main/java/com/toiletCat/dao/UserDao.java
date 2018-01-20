package com.toiletCat.dao;

import com.toiletCat.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public interface UserDao {

    /**
     * 添加用户记录
     * @param user 用户Bean
     */
    void addUser(User user);

    /**
     * 获得所有用户记录数
     * @return
     */
    Integer countAllUser();

    /**
     * 分页获得所有用户记录
     * @param startPage 开始数据
     * @param pageSize 每页数据数
     * @return
     */
    List<User> getAllUserByPage(@Param("startPage") int startPage, @Param("pageSize") int pageSize);

    /**
     * 根据用户编号获得用户记录
     * @param userNo 用户编号
     * @return
     */
    User getUserByUserNo(String userNo);

    /**
     * 根据用户编号获得用户数量
     * @param userNo 用户编号
     * @return
     */
    Integer countUserByUserNo(String userNo);

    /**
     * 根据用户编号获得用户名称
     * @param userNo 用户编号
     * @return
     */
    String getUserNameByUserNo(String userNo);

    /**
     * 根据手机号获得用户记录
     * @param mobileNo 手机号
     * @return
     */
    User getUserByMobileNo(String mobileNo);

    /**
     * 根据用户编号获得用户游戏币数
     * @param userNo 用户编号
     * @return
     */
    Integer getUserCoinByUserNo(String userNo);

    /**
     * 根据手机号获得用户数量
     * @param mobileNo 手机号
     * @return
     */
    Integer countUserByMobileNo(String mobileNo);

    /**
     * 根据用户编号修改用户游戏币数(用户操作 充值coin为正数,消费coin为负数)
     * @param coin 游戏币数
     * @param userNo 用户编号
     */
    void updateUserCoinByUserNo(@Param("coin") Integer coin, @Param("userNo") String userNo);

    /**
     * 根据id,用户编号修改用户游戏币数(后台操作)
     * @param coin 游戏币数
     * @param id id
     * @param userNo 用户编号
     */
    void updateUserCoinByIdAndUserNo(@Param("coin") Integer coin, @Param("id") Long id, @Param("userNo") String userNo);

    /**
     * 根据用户编号修改用户名和用户头像
     * @param userNo 用户编号
     * @param userName 用户名
     * @param userImg 用户头像
     */
    void updateUserInfoByIdAndUserNo(@Param("userNo") String userNo, @Param("userName") String userName,
                                     @Param("userImg") String userImg);

    /**
     * 根据用户邀请码获得用户编号
     * @param invitationCode 邀请码
     */
    String getUserNoByInvitationCode(String invitationCode);

    /**
     * 根据用户编号修改用户邀请用户编码
     * @param userNo 用户编号
     * @param invitationUserNo 用户邀请码用户编号
     */
    void updateInvitationUserNoByUserNo(@Param("userNo") String userNo,
                                        @Param("invitationUserNo") String invitationUserNo);

    /**
     * 根据用户编号获得用户邀请状态
     * @param userNo 用户编号
     * @return
     */
    String getInvitationUserNoByUserNo(String userNo);

    /**
     * 获得用户邀请数量
     * @param invitationUserNo 邀请用户编码
     * @return
     */
    Integer countByInvitationUserNo(String invitationUserNo);
}
