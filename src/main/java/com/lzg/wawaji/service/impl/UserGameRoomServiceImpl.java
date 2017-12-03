package com.lzg.wawaji.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.bean.Callback;
import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.UserGameRoomDao;
import com.lzg.wawaji.entity.UserGameRoom;
import com.lzg.wawaji.service.UserGameRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service("userGameRoomService")
public class UserGameRoomServiceImpl extends BaseServiceImpl implements UserGameRoomService {

    private static final Logger logger = LoggerFactory.getLogger(UserGameRoomServiceImpl.class);

    @Autowired
    private UserGameRoomDao userGameRoomDao;

    /**
     * 添加用户游戏房间记录
     * @param userGameRoom 用户游戏房间
     */
    @Override
    public CommonResult addUserGameRoom(final UserGameRoom userGameRoom) {
        return exec(new Callback() {
            @Override
            public void exec() {
                userGameRoomDao.addUserGameRoom(userGameRoom);
            }
        }, "addUserGameRoom", JSON.toJSONString(userGameRoom));
    }

    /**
     * 根据用户编号获得用户用户游戏房间数量
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    @Override
    public CommonResult<Integer> countUserGameRoomByUserNo(final String userNo, final String gameRoomNo) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);
        json.put("gameRoomNo",gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userGameRoomDao.countUserGameRoomByUserNo(userNo, gameRoomNo));
            }
        }, "countUserGameRoomByUserNo", json.toJSONString());
    }

    /**
     * 根据用户编号获得用户游戏房间
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    @Override
    public CommonResult<UserGameRoom> getUserGameRoomByUserNoAndGameRoomNo(final String userNo, final String gameRoomNo) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);
        json.put("gameRoomNo",gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userGameRoomDao.getUserGameRoomByUserNoAndGameRoomNo(userNo, gameRoomNo));
            }
        }, "countUserGameRoomByUserNo", json.toJSONString());
    }

    /**
     * 根据用户编号和游戏房间编号累加用户房间幸运值
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @param userRoomLuckyNum 用户房间幸运值
     */
    @Override
    public CommonResult addUserRoomLuckyNumByUserNoAndGameRoomNo(String userNo, String gameRoomNo, Integer userRoomLuckyNum) {
        return null;
    }

    /**
     * 重置用户游戏房间幸运值
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     */
    @Override
    public CommonResult resetUserRoomLuckyNum(final String userNo, final String gameRoomNo) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);
        json.put("gameRoomNo",gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                userGameRoomDao.resetUserRoomLuckyNum(userNo, gameRoomNo);
            }
        }, "resetUserRoomLuckyNum", json.toJSONString());
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
