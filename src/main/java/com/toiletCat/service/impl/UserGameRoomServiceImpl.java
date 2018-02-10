package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.dao.UserGameRoomDao;
import com.toiletCat.entity.UserGameRoom;
import com.toiletCat.service.UserGameRoomService;
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
        }, false, "addUserGameRoom", userGameRoom);
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
        }, true, "countUserGameRoomByUserNo", json);
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
        }, true, "countUserGameRoomByUserNo", json);
    }

    /**
     * 根据用户编号和游戏房间编号累加用户房间幸运值
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @param userRoomLuckyNum 用户房间幸运值
     */
    @Override
    public CommonResult addUserRoomLuckyNumByUserNoAndGameRoomNo(final String userNo, final String gameRoomNo,
                                                                 final Integer userRoomLuckyNum) {
        JSONObject json = new JSONObject();
        json.put("userNo",userNo);
        json.put("gameRoomNo",gameRoomNo);
        json.put("userRoomLuckyNum",userRoomLuckyNum);

        return exec(new Callback() {
            @Override
            public void exec() {
                userGameRoomDao.addUserRoomLuckyNumByUserNoAndGameRoomNo(userNo, gameRoomNo, userRoomLuckyNum);
            }
        }, true, "addUserRoomLuckyNumByUserNoAndGameRoomNo", json);
    }

    /**
     * 根据用户编号和房间编号获得用户游戏房间幸运值
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    @Override
    public CommonResult<Integer> getUserGameRoomLuckyNumByUserNo(final String userNo, final String gameRoomNo) {
        JSONObject json = new JSONObject();
        json.put("userNo",userNo);
        json.put("gameRoomNo",gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userGameRoomDao.getUserGameRoomLuckyNumByUserNo(userNo, gameRoomNo));
            }
        }, true, "getUserGameRoomLuckyNumByUserNo", json);
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
        }, true, "resetUserRoomLuckyNum", json);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
