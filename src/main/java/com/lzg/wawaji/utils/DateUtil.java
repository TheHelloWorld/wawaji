package com.lzg.wawaji.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private DateUtil() {

    }

    private static final String dateFormat = "yyyyMMdd";

    private static final String fullFormat = "yyyy-MM-dd HH:mm:ss";

    private static final String secondFormat = "yyyyMMddHHmmss";

    private static final SimpleDateFormat daySdf = new SimpleDateFormat("yyyyMMdd");

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

    /**
     * 获得上一日日期
     * @param day 日期数字
     * @return
     */
    public static Integer getBeforeDate(Integer day) {

        if(day == null) {
            return null;
        }



        String dayTime = String.valueOf(day);

        try {
            // 得到日历
            Calendar calendar = Calendar.getInstance();
            Date date = daySdf.parse(dayTime);
            // 把当前时间赋给日历
            calendar.setTime(date);
            // 设置为前一天
            calendar.add(Calendar.DAY_OF_MONTH, -1);

            // 得到前一天的时间并返回
            return Integer.valueOf(daySdf.format(calendar.getTime()));
        } catch(ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
