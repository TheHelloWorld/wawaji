package com.toiletCat.dao;

import com.toiletCat.entity.Toy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("toyDao")
public interface ToyDao {

    /**
     * 添加娃娃记录
     * @param toy 娃娃Bean
     */
    void addToy(Toy toy);

    /**
     * 获得所有娃娃数
     * @return
     */
    Integer countAllToy();

    /**
     * 分页获得所有娃娃记录
     * @param startPage 开始数据
     * @param pageSize 每页数据数
     * @return
     */
    List<Toy> getAllToyByPage(@Param("startPage") int startPage, @Param("pageSize") int pageSize);

    /**
     * 根据id,娃娃编号获得娃娃及信息
     * @param id id
     * @param toyNo 玩具编号
     * @return
     */
    Toy getToyByIdAndToyNo(@Param("id") Long id, @Param("toyNo") String toyNo);

    /**
     * 根据id,娃娃机编号修改娃娃机记录
     * @param toy 娃娃Bean
     */
    void updateToyByIdAndToyNo(Toy toy);

    /**
     * 根据id和玩具编号删除记录
     * @param id id
     * @param toyNo 玩具编号
     */
    void deleteToyByIdAndToyNo(@Param("id") Long id, @Param("toyNo") String toyNo);

    /**
     * 获得所有可用玩具信息
     * @return
     */
    List<Toy> getAllAvailableToy();

    /**
     * 根据玩具编号获得玩具信息
     * @param toyNo 玩具编号
     * @return
     */
    Toy getToyInfoByToyNo(String toyNo);
}
