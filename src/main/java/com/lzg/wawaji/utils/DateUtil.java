package com.lzg.wawaji.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private DateUtil() {

    }

    private static final String dateFormat = "yyyyMMdd";

    private static final String fullFormat = "yyyy-MM-dd HH:mm:ss";

    private static final String secondFormat = "yyyyMMddHHmmss";

    /**
     * 获取日期
     * @return
     */
    public static Integer getDate() {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return Integer.valueOf(sdf.format(new Date()));
    }

    /**
     * 根据传入时间获得日期
     * @param date 时间
     * @return
     */
    public static Integer getDateByTime(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return Integer.valueOf(sdf.format(date));
    }

    /**
     * 获取完成时间
     * @return
     */
    public static String getFullDate() {

        SimpleDateFormat sdf = new SimpleDateFormat(fullFormat);
        return sdf.format(new Date());
    }

    /**
     * 获取秒时间字符串
     * @return
     */
    public static String getSecondDate() {

        SimpleDateFormat sdf = new SimpleDateFormat(secondFormat);
        return sdf.format(new Date());
    }

    /**
     * 根据字符串格式化时间
     * @param formatStr 格式化代码
     * @return
     */
    public static String getFormatTiem(String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(new Date());
    }
}
