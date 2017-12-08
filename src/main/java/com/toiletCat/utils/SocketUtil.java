package com.toiletCat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.Socket;

public class SocketUtil {

    private static final Logger logger = LoggerFactory.getLogger(SocketUtil.class);

    // ip地址
    private final static String IP = "45.76.51.107";

    // 端口号
    private final static int PORT = 20001;

    private SocketUtil() {

    }

    private static Socket socket = null;

    private static OutputStream output = null;

    /**
     * 静态代码初始化socket
     */
    static {

        if(socket == null) {
            try {
                socket = new Socket(IP, PORT);
                logger.info("socket start success");
            } catch (Exception e) {
                logger.error("socket start error:"+ e.getMessage(), e);
            }
        }
    }

    public static void sendMsg(String msg) {

        try {

            if(output == null) {
                // 向客户端发送信息
                output = socket.getOutputStream();
            }

            output.write(msg.getBytes("utf-8"));
            // 发送数据
            output.flush();

        } catch (Exception e) {
            logger.error("socket sendMsg error:"+ e.getMessage(), e);
        }
    }
}
