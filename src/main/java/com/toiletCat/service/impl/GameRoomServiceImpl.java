package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.UserSeeGameRoom;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.constants.RedisConstant;
import com.toiletCat.dao.GameRoomDao;
import com.toiletCat.dao.UserGameRoomDao;
import com.toiletCat.entity.GameRoom;
import com.toiletCat.enums.HandleType;
import com.toiletCat.service.GameRoomService;
import com.toiletCat.service.ToiletCatConfigService;
import com.toiletCat.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
@Service("gameRoomService")
public class GameRoomServiceImpl extends BaseServiceImpl implements GameRoomService {

    private static final Logger logger = LoggerFactory.getLogger(GameRoomServiceImpl.class);

    @Autowired
    private GameRoomDao gameRoomDao;

    @Autowired
    private UserGameRoomDao userGameRoomDao;

    @Autowired
    private ToiletCatConfigService toiletCatConfigService;

    /**
     * 添加游戏房间
     * @param gameRoom 游戏房间`
     */
    @Override
    public CommonResult addGameRoom(final GameRoom gameRoom) {

        return exec(new Callback() {
            @Override
            public void exec() {
                gameRoomDao.addGameRoom(gameRoom);
            }
        }, false, "addGameRoom", gameRoom);
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
        }, false, "countAllGameRoom", new JSONObject());
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

                List<GameRoom> list = new ArrayList<>();

                list = gameRoomDao.getGameRoomListByPage(startPage, BaseConstant.DEFAULT_PAGE_SIZE);

                if(list == null || list.size() == 0) {

                    got(list);
                    return;
                }
                try (RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {
                    for(GameRoom gameRoom : list) {

                        String key = RedisConstant.REAL_GAME_ROOM_VIEWER.replace(RedisConstant.PLACEHOLDER,
                                gameRoom.getGameRoomNo());

                            String realNum = redisUtil.get(key);

                            if(realNum == null) {
                                realNum = "0";
                            }

                        gameRoom.setRealPlayNum(realNum);

                    }
                } catch (Exception e) {
                    logger.error("getGameRoomListByPage redis error:" + e, e);
                }

                got(list);
            }
        }, true, "getGameRoomListByPage", json);
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
        }, false, "countAllUserSeeGamRoom", new JSONObject());
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

                List<UserSeeGameRoom> userSeeGameRoomList =
                        gameRoomDao.getUserSeeGameRoomListByPage(startPage, BaseConstant.DEFAULT_PAGE_SIZE);

                if(userSeeGameRoomList != null && userSeeGameRoomList.size() >0) {

                    try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

                        for(UserSeeGameRoom userSeeGameRoom : userSeeGameRoomList) {

                            String gameRoomkey = RedisConstant.GAME_ROOM_VIEWER.
                                    replace(RedisConstant.PLACEHOLDER, userSeeGameRoom.getGameRoomNo());

                            String viewerNum = redisUtil.get(gameRoomkey);

                            if(StringUtils.isBlank(viewerNum)) {
                                viewerNum = "0";
                            }

                            // 当前游戏房间观看人数
                            userSeeGameRoom.setViewer(Integer.valueOf(viewerNum));

                        }
                    } catch (Exception e) {
                        logger.error("{} getUserSeeGameRoomListByPage redis error " + e, BaseConstant.LOG_ERR_MSG);
                    }
                }

                got(userSeeGameRoomList);
            }
        }, true, "getUserSeeGameRoomListByPage", json);
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
        }, true, "getUserSeeGameRoomByGameRoomNo", json);
    }

    /**
     * 根据游戏房间号码用户号码获得房间信息
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间号码
     * @return
     */
    @Override
    public CommonResult<String> getUserGameRoomInfoByGameRoomNo(final String userNo, final String gameRoomNo) {
        JSONObject json = new JSONObject();
        json.put("userNo",userNo);
        json.put("gameRoomNo",gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                UserSeeGameRoom userSeeGameRoom = gameRoomDao.getUserSeeGameRoomByGameRoomNo(gameRoomNo);

                // 设置游戏房间当前幸运值为空
                userSeeGameRoom.setRoomNowLuckyNum(null);

                // 试着游戏房间幸运值为空
                userSeeGameRoom.setRoomLuckyNum(null);

                String str = JSON.toJSONString(userSeeGameRoom);

                JSONObject returnJSON = JSONObject.parseObject(str);

                if(userGameRoomDao.countUserGameRoomByUserNo(userNo, gameRoomNo) == 0) {
                    returnJSON.put("userGameRoomLuckyNum", "0");
                } else {
                    returnJSON.put("userGameRoomLuckyNum", userGameRoomDao.getUserGameRoomLuckyNumByUserNo(
                            userNo, gameRoomNo));
                }

                got(returnJSON.toJSONString());
            }
        }, true, "getUserSeeGameRoomByGameRoomNo", json);
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
        }, true, "getCoinByGameRoomNo", json);
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
        }, true, "getGameRoomByGameRoomNoAndId", json);
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
        }, true, "updateGameRoomByGameRoomNoAndId", gameRoom);
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
        }, true, "addRoomLuckyNumByGameRoomNo", json);
    }

    /**
     * 重置游戏房间幸运值
     * @param gameRoomNo 游戏房间编号
     */
    @Override
    public CommonResult resetRoomLuckyNumByGameRoomNo(final String gameRoomNo) {
        JSONObject json = new JSONObject();
        json.put("gameRoomNo",gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                gameRoomDao.resetRoomLuckyNumByGameRoomNo(gameRoomNo);
            }
        }, true, "resetRoomLuckyNumByGameRoomNo", json);
    }

    /**
     * 根据游戏房间编号获得房间幸运值及当前幸运值
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    @Override
    public CommonResult<GameRoom> getLuckyNumByGameRoomNo(final String gameRoomNo) {
        JSONObject json = new JSONObject();
        json.put("gameRoomNo",gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(gameRoomDao.getLuckyNumByGameRoomNo(gameRoomNo));
            }
        }, true, "getLuckyNumByGameRoomNo", json);
    }

    /**
     * 根据游戏房间编号获得玩具名称
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    @Override
    public CommonResult<String> getToyNameByGameRoomNo(final String gameRoomNo) {
        JSONObject json = new JSONObject();
        json.put("gameRoomNo",gameRoomNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(gameRoomDao.getToyNameByGameRoomNo(gameRoomNo));
            }
        }, true, "getToyNameByGameRoomNo", json);
    }

    /**
     * 操作游戏房间围观人数
     * @param gameRoomNo 游戏房间编号
     * @param handleType 操作类型
     * @return
     */
    @Override
    public CommonResult<Long> handleGameRoomViewer(final String gameRoomNo, final HandleType handleType) {
        JSONObject json = new JSONObject();
        json.put("gameRoomNo",gameRoomNo);
        json.put("handleType",handleType.name());

        return exec(new Callback() {
            @Override
            public void exec() {
                try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

                    // 当前游戏房间围观人数key
                    String gameRoomkey = RedisConstant.GAME_ROOM_VIEWER.replace(RedisConstant.PLACEHOLDER, gameRoomNo);

                    if(HandleType.CONNECT == handleType) {
                        // 围观人数+1
                        got(redisUtil.incr(gameRoomkey));
                        return;

                    } else {
                        // 围观人数-1
                        got(redisUtil.decr(gameRoomkey));
                        return;
                    }

                } catch (Exception e) {
                    logger.error("{} handleGameRoomViewer redis error:" + e, BaseConstant.LOG_ERR_MSG, e);
                    got(1000L);
                    return;
                }
            }
        }, true, "handleGameRoomViewer", json);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
