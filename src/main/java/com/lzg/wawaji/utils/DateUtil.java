package com.lzg.wawaji.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private DateUtil() {

    }

    private static final String DateFormat = "yyyyMMdd";

    private static final String FullFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取日期
     * @return
     */
    public static Integer getDate() {

        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
        return Integer.valueOf(sdf.format(new Date()));
    }

    /**
     * 根据传入时间获得日期
     * @param date 时间
     * @return
     */
    public static Integer getDateByTime(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
        return Integer.valueOf(sdf.format(date));
    }

    /**
     * 获取完成时间
     * @return
     */
    public static String getFullDate() {

        SimpleDateFormat sdf = new SimpleDateFormat(FullFormat);
        return sdf.format(new Date());
    }
}
