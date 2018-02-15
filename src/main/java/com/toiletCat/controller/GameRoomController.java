package com.toiletCat.controller;

import com.alibaba.fastjson.JSON;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.UserSeeGameRoom;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.entity.GameRoom;
import com.toiletCat.service.GameRoomService;
import com.toiletCat.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/toiletCat/api/gameRoom")
@Controller
public class GameRoomController {

    @Autowired
    private GameRoomService gameRoomService;

    /**
     * 分页获得所有游戏房间记录
     * @param startPage 开始页
     * @return
     */
    @RequestMapping(value = "/getAllGameRoomByPage", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllGameRoomByPage(int startPage) {

        CommonResult<List<GameRoom>> result = gameRoomService.getGameRoomListByPage(startPage);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 分页获得所有用户可见游戏房间记录
     * @param startPage 开始页
     * @return
     */
    @RequestMapping(value = "/getUserSeeGameRoomListByPage", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserSeeGameRoomListByPage(int startPage) {

        CommonResult<List<UserSeeGameRoom>> result = gameRoomService.getUserSeeGameRoomListByPage(startPage);

        return JSONUtil.getReturnBeanString(result);
    }
    
    /**
     * 获得总记录数和每页数据数
     * @return
     */
    @RequestMapping(value = "/getGameRoomTotalCountAndPageSize", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getGameRoomTotalCountAndPageSize() {

        CommonResult<Integer> result = gameRoomService.countAllGameRoom();

        return JSONUtil.getTotalCountAndPageSize(result, BaseConstant.DEFAULT_PAGE_SIZE);
    }

    /**
     * 获得用户可见总记录数和每页数据数
     * @return
     */
    @RequestMapping(value = "/getUserSeeGameRoomTotalCountAndPageSize", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserSeeGameRoomTotalCountAndPageSize() {

        CommonResult<Integer> result = gameRoomService.countAllUserSeeGamRoom();

        return JSONUtil.getTotalCountAndPageSize(result, BaseConstant.DEFAULT_PAGE_SIZE);
    }

    /**
     * 添加游戏房间记录
     * @param paramStr 机器记录
     * @return
     */
    @RequestMapping(value = "/addGameRoom", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addGameRoom(String paramStr) {

        CommonResult result = gameRoomService.addGameRoom(JSON.parseObject(paramStr, GameRoom.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 根据id和游戏房间编号获得游戏房间记录
     * @param id id
     * @param dataNo 游戏房间编号
     * @return
     */
    @RequestMapping(value = "/getGameRoomByGameRoomNoAndId", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getGameRoomByGameRoomNoAndId(Long id, String dataNo) {

        CommonResult<GameRoom> result = gameRoomService.getGameRoomByGameRoomNoAndId(dataNo, id);

        return JSONUtil.getReturnStrString(result, String.valueOf(result.getValue()));
    }

    /**
     * 根据id和游戏房间编号修改游戏房间记录
     * @param paramStr 机器记录
     * @return
     */
    @RequestMapping(value = "/updateGameRoomByGameRoomNoAndId", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateGameRoomByGameRoomNoAndId(String paramStr) {

        CommonResult result =  gameRoomService.updateGameRoomByGameRoomNoAndId(JSON.parseObject(paramStr, GameRoom.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 根据游戏房间号码获得用户可见游戏房间
     * @param gameRoomNo 游戏房间号码
     * @return
     */
    @RequestMapping(value = "/getUserSeeGameRoomByGameRoomNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserSeeGameRoomByGameRoomNo(String gameRoomNo) {

        CommonResult<UserSeeGameRoom> result =  gameRoomService.getUserSeeGameRoomByGameRoomNo(gameRoomNo);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 根据游戏房间号码用户号码获得房间信息
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间号码
     * @return
     */
    @RequestMapping(value = "/getUserGameRoomInfoByGameRoomNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserGameRoomInfoByGameRoomNo(String userNo, String gameRoomNo) {

        CommonResult<String> result =  gameRoomService.getUserGameRoomInfoByGameRoomNo(userNo, gameRoomNo);

        return JSONUtil.getReturnBeanString(result);
    }
}
