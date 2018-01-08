package com.toiletCat.utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class RandomIntUtil {

    private RandomIntUtil() {

    }

    /**
     * 获得随机数
     * @return
     */
    public static String getRandom() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((int) Math.floor(Math.random() * 10));
        }

        return sb.toString();
    }

    /**
     * 获得下边界到最大值之间的随机数
     * @param bound 下边界
     * @return
     */
    public static Integer getRandomNum(int bound) {
        Random random = new Random();

        return random.nextInt(10) + 1 + bound;
    }

    /**
     * 获得上边界到最大值之间的随机数
     * @param highBound 上边界
     * @return
     */
    public static Integer getRandomNumByHighBound(int highBound) {
        Random random = new Random();

        return random.nextInt(highBound) + 1;
    }


    /**
     * 获得随机名称
     * @param str 字符串
     * @return
     */
    public static String getRandomName(String str) {
        String ss = str.substring(str.lastIndexOf("."), str.length());
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = sdf.format(date);
        return s + getRandom() + ss;
    }

    /**
     * 获得指定长度的随机字符串
     * @param length 长度
     * @return
     */
    public static String getRandomString(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

}
