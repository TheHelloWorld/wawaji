package com.toiletCat.entity;

import java.io.Serializable;

/**
 * 配置表
 */
public class ToiletCatConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4954366162493302680L;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置key(程序中用)
     */
    private String configKey;

    /**
     * 配置value
     */
    private String configValue;

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
