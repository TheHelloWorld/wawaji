package com.lzg.wawaji.utils;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PropertiesUtil {

    private static Map instance = Collections.synchronizedMap(new HashMap());

    private static Object lock = new Object();

    private String sourceUrl;

    private ResourceBundle resourceBundle;

    private static Map<String, String> convert = Collections.synchronizedMap(new HashMap<String, String>());

    public PropertiesUtil(String sourceUrl) {
        this.sourceUrl = sourceUrl;
        load();
    }

    public static PropertiesUtil getInstance(String sourceUrl) {
        synchronized (lock) {
            PropertiesUtil manager = (PropertiesUtil) instance.get(sourceUrl);
            if (manager == null) {
                manager = new PropertiesUtil(sourceUrl);
                instance.put(sourceUrl, manager);
            }
            return manager;
        }
    }

    private synchronized void load() {
        try {
            resourceBundle = ResourceBundle.getBundle(sourceUrl);
        } catch (Exception e) {
            throw new RuntimeException("sourceUrl = " + sourceUrl + " file load error!", e);
        }
    }

    public synchronized String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    public Map<String, String> readyConvert() {
        Enumeration enu = resourceBundle.getKeys();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            String value = resourceBundle.getString(key);
            convert.put(value, key);
        }
        return convert;
    }

    public Map<String, String> readyConvert(ResourceBundle resourcebundle) {
        Enumeration enu = resourcebundle.getKeys();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            String value = resourcebundle.getString(key);
            convert.put(value, key);
        }
        return convert;
    }

    public static void main(String[] args) {
        PropertiesUtil hh = PropertiesUtil.getInstance("redis");
        String df = hh.getProperty("redis.host");
        System.out.println(df.split(",").length);
    }

}
