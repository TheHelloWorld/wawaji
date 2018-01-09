package com.toiletCat.service;

import com.toiletCat.bean.CommonResult;
import com.toiletCat.entity.ToiletCatConfig;

import java.util.List;

public interface ToiletCatConfigService {

    /**
     * 添加配置项
     * @param toiletCatConfig 配置项
     */
    CommonResult addToiletCatConfig(ToiletCatConfig toiletCatConfig);

    /**
     * 获得所有配置项数量
     * @return
     */
    CommonResult<Integer> countAllConfig();

    /**
     * 获得所有配置
     * @return
     */
    CommonResult<List<ToiletCatConfig>> getAllConfig();

    /**
     * 修改配置项
     * @param toiletCatConfig 配置项
     */
    CommonResult updateToiletCatConfig(ToiletCatConfig toiletCatConfig);

    /**
     * 删除配置项
     * @param toiletCatConfig 配置项
     */
    CommonResult deleteToiletCatConfig(ToiletCatConfig toiletCatConfig);

    /**
     * 根据key获得配置项值
     * @param key 配置项key
     * @return
     */
    String getConfigByKey(String key);
}
