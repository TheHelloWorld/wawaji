package com.lzg.wawaji.utils;

import java.util.UUID;

public class UUIDUtil {

    private UUIDUtil() {

    }

    /**
     * 生成随机且唯一的UUID
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
