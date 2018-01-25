package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.dao.ToiletCatConfigDao;
import com.toiletCat.entity.ToiletCatConfig;
import com.toiletCat.service.ToiletCatConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
@Service("toiletCatConfigService")
public class ToiletCatConfigServiceImpl extends BaseServiceImpl implements ToiletCatConfigService {

    private static final Logger logger = LoggerFactory.getLogger(ToiletCatConfigServiceImpl.class);

    @Autowired
    private ToiletCatConfigDao toiletCatConfigDao;

    /**
     * 添加配置项
     * @param toiletCatConfig 配置项
     */
    @Override
    public CommonResult addToiletCatConfig(final ToiletCatConfig toiletCatConfig) {
        return exec(new Callback() {
            @Override
            public void exec() {
                toiletCatConfigDao.addToiletCatConfig(toiletCatConfig);

                initCoinfgMap();

                // 添加对应key value
                BaseConstant.configMap.put(toiletCatConfig.getConfigKey(), toiletCatConfig.getConfigValue());
            }
        }, "addToiletCatConfig", toiletCatConfig);
    }

    /**
     * 获得所有配置项数量
     * @return
     */
    @Override
    public CommonResult<Integer> countAllConfig() {
        return exec(new Callback() {
            @Override
            public void exec() {
                got(toiletCatConfigDao.countAllConfig());
            }
        }, "countAllConfig", new JSONObject());
    }

    /**
     * 获得所有配置
     * @return
     */
    @Override
    public CommonResult<List<ToiletCatConfig>> getAllConfig() {
        return exec(new Callback() {
            @Override
            public void exec() {
                got(toiletCatConfigDao.getAllConfig());
            }
        }, "getAllConfig", new JSONObject());
    }

    /**
     * 根据id获得配置项
     * @param id 主键id
     * @return
     */
    @Override
    public CommonResult<ToiletCatConfig> getToiletCatConfigById(final Long id) {
        JSONObject json = new JSONObject();
        json.put("id", id);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(toiletCatConfigDao.getToiletCatConfigById(id));
            }
        }, "getAllConfig", json);
    }

    /**
     * 修改配置项
     * @param toiletCatConfig 配置项
     */
    @Override
    public CommonResult updateToiletCatConfig(final ToiletCatConfig toiletCatConfig) {
        return exec(new Callback() {
            @Override
            public void exec() {
                toiletCatConfigDao.updateToiletCatConfig(toiletCatConfig);

                // 初始化配置Map
                initCoinfgMap();

                // 更新对应key value
                BaseConstant.configMap.put(toiletCatConfig.getConfigKey(), toiletCatConfig.getConfigValue());
            }
        }, "updateToiletCatConfig", toiletCatConfig);
    }

    /**
     * 删除配置项
     * @param toiletCatConfig 配置项
     */
    @Override
    public CommonResult deleteToiletCatConfig(final ToiletCatConfig toiletCatConfig) {
        return exec(new Callback() {
            @Override
            public void exec() {
                toiletCatConfigDao.deleteToiletCatConfig(toiletCatConfig);

                // 初始化配置Map
                initCoinfgMap();

                // 删除对应key value
                BaseConstant.configMap.remove(toiletCatConfig.getConfigKey());
            }
        }, "deleteToiletCatConfig", toiletCatConfig);
    }

    /**
     * 根据key获得配置项值
     * @param key 配置项key
     * @return
     */
    @Override
    public String getConfigByKey(String key) {
        logger.info("getConfigByKey: "+ key);

        // 若获得的key目前为空则重新获取一遍配置Map
        if(BaseConstant.configMap.get(key) == null) {
            refreshConfigMap();
        }

        // 若获取后依旧为空则日志报警并返回0
        if(BaseConstant.configMap.get(key) == null) {
            logger.warn("getConfigByKey key is null:" + key);
            return "0";
        }

        logger.info("getConfigByKey: "+ key + ", value:" + BaseConstant.configMap.get(key));

        return BaseConstant.configMap.get(key);
    }

    /**
     * 初始化map
     */
    private void initCoinfgMap() {
        if(BaseConstant.configMap.isEmpty()) {

            refreshConfigMap();
        }
    }

    /**
     * 重新刷新Map
     */
    private void refreshConfigMap() {
        List<ToiletCatConfig> list = new ArrayList<>(toiletCatConfigDao.countAllConfig());

        list = toiletCatConfigDao.getAllConfig();

        // 将配置放入配置Map中
        for(ToiletCatConfig toiletCatConfig : list) {
            BaseConstant.configMap.put(toiletCatConfig.getConfigKey(), toiletCatConfig.getConfigValue());
        }
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
