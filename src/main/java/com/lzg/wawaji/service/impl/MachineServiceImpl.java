package com.lzg.wawaji.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.bean.Callback;
import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.bean.UserMachine;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.MachineDao;
import com.lzg.wawaji.entity.Machine;
import com.lzg.wawaji.service.MachineService;
import com.lzg.wawaji.utils.RedisUtil;
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
        }, "addMachine", JSON.toJSONString(machine));

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
        }, "countAllMachine", new JSONObject().toJSONString());
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
        }, "getAllMachineByPage", json.toJSONString());
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

//                if(userMachineList != null && userMachineList.size() >0) {
//                    try(RedisUtil redisUtil = new RedisUtil("redis")) {
//                        for(UserMachine userMachine : userMachineList) {
//                            String viewerNum = redisUtil.hget(userMachine.getMachineNo(), "viewer");
//
//                            if(StringUtils.isBlank(viewerNum)) {
//                                viewerNum = "0";
//                            }
//                            userMachine.setViewer(Integer.valueOf(viewerNum));
//
//                            String isUse = redisUtil.get(userMachine.getMachineNo());
//
//                            boolean available = false;
//                            if(StringUtils.isBlank(isUse)) {
//                                available = true;
//                            }
//                            userMachine.setAvailable(available);
//                        }
//                    } catch (Exception e) {
//                        logger.error("{} redis error " + e, BaseConstant.LOG_ERR_MSG);
//                    }
//                }

                got(userMachineList);
            }
        }, "getUserAllMachineByPage", json.toJSONString());
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
        }, "getMachineByIdAndMachineNo", json.toJSONString());
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
        }, "updateMachineByIdAndMachineNo", JSON.toJSONString(machine));

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
        }, "deleteMachineByIdAndMachineNo", json.toJSONString());

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
        }, "getCoinByMachineNo", json.toJSONString());
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
                try(RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {
                    String key = BaseConstant.MACHINE_IN_USE.replace("#{}", machineNo);
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
        }, "getMachineInUse", json.toJSONString());
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
        }, "getToyNoAndToyImgByMachineNo", json.toJSONString());
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
