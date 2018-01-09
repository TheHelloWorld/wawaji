package com.toiletCat.dao;

import com.toiletCat.entity.ToiletCatConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("toiletCatConfigDao")
public interface ToiletCatConfigDao {

    /**
     * 添加配置项
     * @param toiletCatConfig 配置项
     */
    void addToiletCatConfig(ToiletCatConfig toiletCatConfig);

    /**
     * 获得所有配置项数量
     * @return
     */
    Integer countAllConfig();

    /**
     * 获得所有配置
     * @return
     */
    List<ToiletCatConfig> getAllConfig();

    /**
     * 根据id获得配置项
     * @param id 主键id
     * @return
     */
    ToiletCatConfig getToiletCatConfigById(Long id);

    /**
     * 修改配置项
     * @param toiletCatConfig 配置项
     */
    void updateToiletCatConfig(ToiletCatConfig toiletCatConfig);

    /**
     * 删除配置项
     * @param toiletCatConfig 配置项
     */
    void deleteToiletCatConfig(ToiletCatConfig toiletCatConfig);

}
