package com.lzg.wawaji.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.bean.Callback;
import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.bean.UserSeeGameRoom;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.GameRoomDao;
import com.lzg.wawaji.entity.GameRoom;
import com.lzg.wawaji.service.GameRoomService;
import com.lzg.wawaji.utils.RandomIntUtil;
import com.lzg.wawaji.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("all")
@Service("gameRoomService")
public class GameRoomServiceImpl extends BaseServiceImpl implements GameRoomService {

    private static final Logger logger = LoggerFactory.getLogger(GameRoomServiceImpl.class);

    @Autowired
    private GameRoomDao gameRoomDao;

    /**
     * 添加游戏房间
     * @param gameRoom 游戏房间
     */
    @Override
    public CommonResult addGameRoom(final GameRoom gameRoom) {

        return exec(new Callback() {
            @Override
            public void exec() {
                gameRoomDao.addGameRoom(gameRoom);
            }
        }, "addGameRoom", JSON.toJSONString(gameRoom));
    }

    /**
     * 获得所有游戏房间的数量
     * @return
     */
    @Override
    public CommonResult<Integer> countAllGameRoom() {

        return exec(new Callback() {
            @Override
            public void exec() {
                got(gameRoomDao.countAllGameRoom());
            }
        }, "countAllGameRoom", new JSONObject().toJSONString());
    }

    /**
     * 分页获得所有游戏房间
     * @param startPage 开始页
     * @param pageSize 每页数据数
     * @return
     */
    @Override
    public CommonResult<List<GameRoom>> getGameRoomListByPage(final int startPage) {
        JSONObject json = new JSONObject();
        json.put("startPage",startPage);
        json.put("pageSize",BaseConstant.DEFAULT_PAGE_SIZE);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(gameRoomDao.getGameRoomListByPage(startPage, BaseConstant.DEFAULT_PAGE_SIZE));
            }
        }, "getGameRoomListByPage", json.toJSONString());
    }

    /**
     * 获得所有用户可见游戏房间数量
     * @return
     */
    @Override
    public CommonResult<Integer> countAllUserSeeGamRoom() {

        return exec(new Callback() {
            @Override
            public void exec() {
                got(gameRoomDao.countAllUserSeeGamRoom());
            }
        }, "countAllUserSeeGamRoom", new JSONObject().toJSONString());
    }

    /**
     * 分页获得所有用户可见的游戏房间
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<UserSeeGameRoom>> getUserSeeGameRoomListByPage(final int startPage) {
        JSONObject json = new JSONObject();
        json.put("startPage",startPage);
        json.put("pageSize",BaseConstant.DEFAULT_PAGE_SIZE);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(gameRoomDao.getUserSeeGameRoomListByPage(startPage, BaseConstant.DEFAULT_PAGE_SIZE));
            }
        }, "getUserSeeGameRoomListByPage", json.toJSONString());
    }

    /**
     * 根据游戏房间号码获得用户可见游戏房间
     * @param gameRoomNo 游戏房间号码
     * @return
     */
    @Override
    public CommonResult<UserSeeGameRoom> getUserSeeGameRoomByGameRoomNo(final String gameRoomNo) {
        JSONObject json = new JSONObject();
        json.put("gameRoomNo",gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(gameRoomDao.getUserSeeGameRoomByGameRoomNo(gameRoomNo));
            }
        }, "getUserSeeGameRoomByGameRoomNo", json.toJSONString());
    }

    /**
     * 根据游戏房间编号获得所需游戏币数
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    @Override
    public CommonResult<Integer> getCoinByGameRoomNo(final String gameRoomNo) {
        JSONObject json = new JSONObject();
        json.put("gameRoomNo",gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(gameRoomDao.getCoinByGameRoomNo(gameRoomNo));
            }
        }, "getCoinByGameRoomNo", json.toJSONString());
    }

    /**
     * 根据游戏房间编号和id获得游戏房间数据
     * @param gameRoomNo 游戏房间编号
     * @param id id
     * @return
     */
    @Override
    public CommonResult<GameRoom> getGameRoomByGameRoomNoAndId(final String gameRoomNo, final Long id) {
        JSONObject json = new JSONObject();
        json.put("gameRoomNo",gameRoomNo);
        json.put("id",id);

        return exec(new Callback() {
            @Override
            public void exec() {

                got(gameRoomDao.getGameRoomByGameRoomNoAndId(gameRoomNo, id));
            }
        }, "getGameRoomByGameRoomNoAndId", json.toJSONString());
    }

    /**
     * 根据游戏房间编号和id修改游戏房间
     * @param gameRoom 游戏房间
     */
    @Override
    public CommonResult updateGameRoomByGameRoomNoAndId(final GameRoom gameRoom) {

        return exec(new Callback() {
            @Override
            public void exec() {
                gameRoomDao.updateGameRoomByGameRoomNoAndId(gameRoom);
            }
        }, "updateGameRoomByGameRoomNoAndId", JSON.toJSONString(gameRoom));
    }

    /**
     * 累加游戏房间幸运值
     * @param gameRoomNo 游戏房间编号
     */
    @Override
    public CommonResult addRoomLuckyNumByGameRoomNo(final String gameRoomNo) {
        JSONObject json = new JSONObject();
        json.put("gameRoomNo",gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                gameRoomDao.addRoomLuckyNumByGameRoomNo(gameRoomNo);
            }
        }, "addRoomLuckyNumByGameRoomNo", json.toJSONString());
    }

    /**
     * 重置游戏房间幸运值
     * @param gameRoomNo 游戏房间编号
     */
    @Override
    public CommonResult resetRoomLuckyNumByGameRoomNo(final String gameRoomNo) {
        JSONObject json = new JSONObject();
        json.put("gameRoomNo",gameRoomNo);

        final Integer roomLuckyNum = RandomIntUtil.getRandomNum(40);

        json.put("roomLuckyNum",roomLuckyNum);

        return exec(new Callback() {
            @Override
            public void exec() {
                gameRoomDao.resetRoomLuckyNumByGameRoomNo(gameRoomNo, roomLuckyNum);
            }
        }, "resetRoomLuckyNumByGameRoomNo", json.toJSONString());
    }

    @Override
    public CommonResult<GameRoom> getLuckyNumByGameRoomNo(final String gameRoomNo) {
        JSONObject json = new JSONObject();
        json.put("gameRoomNo",gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(gameRoomDao.getLuckyNumByGameRoomNo(gameRoomNo));
            }
        }, "getLuckyNumByGameRoomNo", json.toJSONString());
    }

    /**
     * 根据游戏房间编号获得当前游戏房间是否可用
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    @Override
    public CommonResult<String> getGameRoomInUse(final String gameRoomNo) {

        JSONObject json = new JSONObject();
        json.put("gameRoomNo",gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                try(RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {
                    String key = BaseConstant.GAME_ROOM_IN_USE.replace("#{}", gameRoomNo);
                    // 获得当前机器锁
                    String value = redisUtil.get(key);
                    if(value == null) {
                        got("true");
                        return;
                    }
                    got("false");
                    return;
                } catch (Exception e) {
                    logger.error("{} getGameRoomInUse redis error:" + e, BaseConstant.LOG_ERR_MSG, e);
                    got("false");
                    return;
                }
            }
        }, "getGameRoomInUse", json.toJSONString());
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
