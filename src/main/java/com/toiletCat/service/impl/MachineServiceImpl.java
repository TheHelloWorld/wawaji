package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.UserMachine;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.constants.RedisConstant;
import com.toiletCat.dao.MachineDao;
import com.toiletCat.entity.Machine;
import com.toiletCat.enums.HandleType;
import com.toiletCat.service.MachineService;
import com.toiletCat.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("all")
@Service("machineService")
public class MachineServiceImpl extends BaseServiceImpl implements MachineService {

    private static final Logger logger = LoggerFactory.getLogger(MachineServiceImpl.class);

    @Autowired
    private MachineDao machineDao;

    /**
     * 添加机器记录
     * @param machine 机器Bean
     */
    @Override
    public CommonResult addMachine(final Machine machine) {

        return exec(new Callback() {
            @Override
            public void exec() {
                machineDao.addMachine(machine);
            }
        }, "addMachine", machine);

    }

    /**
     * 获得所有机器记录数量
     * @return
     */
    @Override
    public CommonResult<Integer> countAllMachine() {

        return exec(new Callback() {
            @Override
            public void exec() {
                got(machineDao.countAllMachine());
            }
        }, "countAllMachine", new JSONObject());
    }

    /**
     * 分页获得所有机器记录
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<Machine>> getAllMachineByPage(final int startPage) {

        JSONObject json = new JSONObject();
        json.put("startPage",startPage);
        json.put("pageSize",BaseConstant.DEFAULT_PAGE_SIZE);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(machineDao.getAllMachineByPage(startPage, BaseConstant.DEFAULT_PAGE_SIZE));
            }
        }, "getAllMachineByPage", json);
    }

    /**
     * 分页获得所有用户使用娃娃机
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<UserMachine>> getUserAllMachineByPage(final int startPage) {

        JSONObject json = new JSONObject();
        json.put("startPage",startPage);
        json.put("pageSize",BaseConstant.DEFAULT_PAGE_SIZE);

        return exec(new Callback() {
            @Override
            public void exec() {

                List<UserMachine> userMachineList = machineDao.getUserAllMachineByPage(startPage,
                        BaseConstant.DEFAULT_PAGE_SIZE);

                if(userMachineList != null && userMachineList.size() > 0) {

                    try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

                        for(UserMachine userMachine : userMachineList) {

                            // 获得当前机器围观人数
                            String machineKey = RedisConstant.MACHINE_ROOM_VIEWER.
                                    replace(RedisConstant.PLACEHOLDER, userMachine.getMachineNo());

                            String viewerNum = redisUtil.get(machineKey);

                            if(StringUtils.isBlank(viewerNum)) {
                                viewerNum = "0";
                            }

                            userMachine.setViewer(Integer.valueOf(viewerNum));

                            // 当前机器被占用的锁
                            String machineLockKey = RedisConstant.MACHINE_IN_USE.replace(RedisConstant.PLACEHOLDER,
                                    userMachine.getMachineNo());

                            String isUse = redisUtil.get(machineLockKey);

                            boolean available = false;

                            if(StringUtils.isBlank(isUse)) {

                                available = true;
                            }

                            // 设置当前机器是否可用
                            userMachine.setAvailable(available);
                        }
                    } catch (Exception e) {
                        logger.error("{} getUserAllMachineByPage redis error " + e, BaseConstant.LOG_ERR_MSG);
                    }
                }

                got(userMachineList);
            }
        }, "getUserAllMachineByPage", json);
    }
    /**
     * 根据id,机器编号获得机器信息
     * @param id id
     * @param machineNo 机器编号
     * @return
     */
    @Override
    public CommonResult<Machine> getMachineByIdAndMachineNo(final Long id, final String machineNo) {

        JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("machineNo",machineNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(machineDao.getMachineByIdAndMachineNo(id, machineNo));
            }
        }, "getMachineByIdAndMachineNo", json);
    }

    /**
     * 根据id,机器编号修改机器记录
     * @param machine 机器Bean
     */
    @Override
    public CommonResult updateMachineByIdAndMachineNo(final Machine machine) {

        return exec(new Callback() {
            @Override
            public void exec() {
                machineDao.updateMachineByIdAndMachineNo(machine);;
            }
        }, "updateMachineByIdAndMachineNo", machine);

    }

    /**
     * 根据id,机器编号删除机器记录
     * @param id id
     * @param machineNo 机器编号
     */
    @Override
    public CommonResult deleteMachineByIdAndMachineNo(final Long id, final String machineNo) {

        JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("machineNo",machineNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                machineDao.deleteMachineByIdAndToyNo(id, machineNo);
            }
        }, "deleteMachineByIdAndMachineNo", json);

    }

    /**
     * 根据机器编号获得所需游戏币数
     * @param machineNo 机器编号
     * @return
     */
    @Override
    public CommonResult<Integer> getCoinByMachineNo(final String machineNo) {

        JSONObject json = new JSONObject();
        json.put("machineNo",machineNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(machineDao.getCoinByMachineNo(machineNo));
            }
        }, "getCoinByMachineNo", json);
    }

    /**
     * 根据机器编号获得当前机器是否可用
     * @param machineNo 机器编号
     * @return
     */
    @Override
    public CommonResult<String> getMachineInUse(final String machineNo) {

        JSONObject json = new JSONObject();
        json.put("machineNo",machineNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

                    String key = RedisConstant.MACHINE_IN_USE.replace(RedisConstant.PLACEHOLDER, machineNo);

                    // 获得当前机器锁
                    String value = redisUtil.get(key);
                    if(value == null) {
                        got("true");
                        return;
                    }
                    got("false");
                    return;
                } catch (Exception e) {
                    logger.error("{} getMachineInUse redis error:" + e, BaseConstant.LOG_ERR_MSG, e);
                    got("false");
                    return;
                }
            }
        }, "getMachineInUse", json);
    }

    /**
     * 根据机器编号获得玩具编号和玩具图片地址
     * @param machineNo 机器编号
     * @return
     */
    @Override
    public CommonResult<UserMachine> getToyNoAndToyImgByMachineNo(final String machineNo) {
        JSONObject json = new JSONObject();
        json.put("machineNo",machineNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                UserMachine userMachine = machineDao.getToyNoAndToyImgByMachineNo(machineNo);
                got(userMachine);
            }
        }, "getToyNoAndToyImgByMachineNo", json);
    }

    /**
     * 操作娃娃机围观人数
     * @param machineNo 娃娃机编号
     * @param handleType 操作类型
     * @return
     */
    @Override
    public CommonResult<Long> handleMachineViewer(final String machineNo, final HandleType handleType) {
        JSONObject json = new JSONObject();
        json.put("machineNo",machineNo);
        json.put("handleType",handleType.name());

        return exec(new Callback() {
            @Override
            public void exec() {
                try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

                    // 当前娃娃机房间围观人数key
                    String machineRoomKey = RedisConstant.MACHINE_ROOM_VIEWER.replace(RedisConstant.PLACEHOLDER, machineNo);

                    if(HandleType.CONNECT == handleType) {
                        // 围观人数+1
                        got(redisUtil.incr(machineRoomKey));
                        return;

                    } else {
                        // 围观人数-1
                        got(redisUtil.decr(machineRoomKey));
                        return;
                    }

                } catch (Exception e) {
                    logger.error("{} handleMachineViewer redis error:" + e, BaseConstant.LOG_ERR_MSG, e);
                    got(1000L);
                    return;
                }
            }
        }, "handleMachineViewer", json);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
