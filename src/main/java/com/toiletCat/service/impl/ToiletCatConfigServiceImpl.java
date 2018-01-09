package com.toiletCat.service.impl;

import com.toiletCat.bean.CommonResult;
import com.toiletCat.entity.ToiletCatConfig;
import com.toiletCat.service.ToiletCatConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("all")
@Service("toiletCatConfigService")
public class ToiletCatConfigServiceImpl extends BaseServiceImpl implements ToiletCatConfigService {

    private static final Logger logger = LoggerFactory.getLogger(ToiletCatConfigServiceImpl.class);

    /**
     * 添加配置项
     * @param toiletCatConfig 配置项
     */
    @Override
    public CommonResult addToiletCatConfig(ToiletCatConfig toiletCatConfig) {
        return null;
    }

    /**
     * 获得所有配置项数量
     * @return
     */
    @Override
    public CommonResult<Integer> countAllConfig() {
        return null;
    }

    /**
     * 获得所有配置
     * @return
     */
    @Override
    public CommonResult<List<ToiletCatConfig>> getAllConfig() {
        return null;
    }

    /**
     * 修改配置项
     * @param toiletCatConfig 配置项
     */
    @Override
    public CommonResult updateToiletCatConfig(ToiletCatConfig toiletCatConfig) {
        return null;
    }

    /**
     * 删除配置项
     * @param toiletCatConfig 配置项
     */
    @Override
    public CommonResult deleteToiletCatConfig(ToiletCatConfig toiletCatConfig) {
        return null;
    }

    /**
     * 根据key获得配置项值
     * @param key 配置项key
     * @return
     */
    @Override
    public String getConfigByKey(String key) {
        return null;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
