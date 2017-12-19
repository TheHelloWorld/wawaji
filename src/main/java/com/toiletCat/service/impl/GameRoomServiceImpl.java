package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.UserSeeGameRoom;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.dao.GameRoomDao;
import com.toiletCat.dao.UserGameRoomDao;
import com.toiletCat.entity.GameRoom;
import com.toiletCat.enums.HandleType;
import com.toiletCat.service.GameRoomService;
import com.toiletCat.utils.RandomIntUtil;
import com.toiletCat.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
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

    @Autowired
    private UserGameRoomDao userGameRoomDao;

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

                List<UserSeeGameRoom> userSeeGameRoomList =
                        gameRoomDao.getUserSeeGameRoomListByPage(startPage, BaseConstant.DEFAULT_PAGE_SIZE);

                if(userSeeGameRoomList != null && userSeeGameRoomList.size() >0) {

                    try(RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {

                        for(UserSeeGameRoom userSeeGameRoom : userSeeGameRoomList) {

                            String gameRoomkey = BaseConstant.GAME_ROOM_VIEWER.
                                    replace("#{}", userSeeGameRoom.getGameRoomNo());

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

                String str = JSON.toJSONString(gameRoomDao.getUserSeeGameRoomByGameRoomNo(gameRoomNo));

                JSONObject returnJSON = JSONObject.parseObject(str);

                if(userGameRoomDao.countUserGameRoomByUserNo(userNo, gameRoomNo) == 0) {
                    returnJSON.put("userGameRoomLuckyNumB", "0");
                } else {
                    returnJSON.put("userGameRoomLuckyNumB", userGameRoomDao.getUserGameRoomLuckyNumByUserNo(
                            userNo, gameRoomNo));
                }

                got(returnJSON.toJSONString());
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
        }, "getLuckyNumByGameRoomNo", json.toJSONString());
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
        }, "getToyNameByGameRoomNo", json.toJSONString());
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
                try(RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {

                    // 当前游戏房间围观人数key
                    String gameRoomkey = BaseConstant.GAME_ROOM_VIEWER.replace("#{}", gameRoomNo);

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
        }, "handleGameRoomViewer", json.toJSONString());
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
