package com.lzg.wawaji.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {

    private HttpUtil() {

    }

    /**
     * 获取Ip
     *
     * @param request 请求
     * @return
     * @throws Exception
     */
    public static String getUserIp(HttpServletRequest request) throws Exception {
        String ip = "";

        if (request.getHeader("x-forwarded-for") != null && (!request.getHeader("x-forwarded-for").trim().equals("")
        )) {
            ip = request.getHeader("x-forwarded-for").split(",")[0];
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
