package com.lzg.wawaji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.MachineDao;
import com.lzg.wawaji.entity.Machine;
import com.lzg.wawaji.entity.UserMachine;
import com.lzg.wawaji.service.MachineService;
import com.lzg.wawaji.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("machineService")
public class MachineServiceImpl implements MachineService {

    private static final Logger logger = LoggerFactory.getLogger(MachineServiceImpl.class);

    @Autowired
    private MachineDao machineDao;

    /**
     * 添加机器记录
     * @param machine 机器Bean
     */
    @Override
    public void addMachine(Machine machine) {
        machineDao.addMachine(machine);
    }

    /**
     * 获得所有机器记录数量
     * @return
     */
    @Override
    public Integer countAllMachine() {
        try {
            return machineDao.countAllMachine();
        } catch (Exception e) {
            logger.error("{} countAllMachine error "+ e, BaseConstant.LOG_ERR_MSG, e);
        }
        return null;
    }

    /**
     * 分页获得所有机器记录
     * @param startPage 开始页
     * @return
     */
    @Override
    public List<Machine> getAllMachineByPage(int startPage) {
        try {
            return machineDao.getAllMachineByPage(startPage, BaseConstant.DEFAULT_PAGE_SIZE);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("startPage",startPage);
            json.put("pageSize",BaseConstant.DEFAULT_PAGE_SIZE);
            logger.error("{} getAllMachineByPage param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
        }
        return null;
    }

    /**
     * 分页获得所有用户使用娃娃机
     * @param startPage 开始页
     * @return
     */
    @Override
    public List<UserMachine> getUserAllMachineByPage(int startPage) {

        List<UserMachine> userMachineList = machineDao.getUserAllMachineByPage(startPage, BaseConstant.DEFAULT_PAGE_SIZE);

        if(userMachineList != null && userMachineList.size() >0) {
            try(RedisUtil redisUtil = new RedisUtil("redis")) {
                for(UserMachine userMachine : userMachineList) {
                    String viewerNum = redisUtil.hget(userMachine.getMachineNo(), "viewer");

                    if(StringUtils.isBlank(viewerNum)) {
                        viewerNum = "0";
                    }
                    userMachine.setViewer(Integer.valueOf(viewerNum));

                    String isUse = redisUtil.get(userMachine.getMachineNo());

                    boolean available = false;
                    if(StringUtils.isBlank(isUse)) {
                        available = true;
                    }
                    userMachine.setAvailable(available);
                }
            } catch (Exception e) {
                logger.error("{} redis error " + e, BaseConstant.LOG_ERR_MSG);
            }
        }

        return userMachineList;
    }
    /**
     * 根据id,机器编号获得机器信息
     * @param id id
     * @param machineNo 机器编号
     * @return
     */
    @Override
    public Machine getMachineByIdAndMachineNo(Long id, String machineNo) {
        try {
            return machineDao.getMachineByIdAndMachineNo(id, machineNo);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("id",id);
            json.put("machineNo",machineNo);
            logger.error("{} getMachineByIdAndMachineNo param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
        }
        return null;
    }

    /**
     * 根据id,机器编号修改机器记录
     * @param machine 机器Bean
     */
    @Override
    public void updateMachineByIdAndMachineNo(Machine machine) {
        machineDao.updateMachineByIdAndMachineNo(machine);
    }

    /**
     * 根据id,机器编号删除机器记录
     * @param id id
     * @param machineNo 机器编号
     */
    @Override
    public void deleteMachineByIdAndMachineNo(Long id, String machineNo) {
        machineDao.deleteMachineByIdAndToyNo(id, machineNo);
    }

    /**
     * 根据机器编号获得机器记录
     * @param machineNo 机器编号
     * @return
     */
    @Override
    public Machine getMachineByMachineNo(String machineNo) {
        try {
            return machineDao.getMachineByMachineNo(machineNo);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("machineNo",machineNo);
            logger.error("{} getMachineByMachineNo param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
        }
        return null;

    }

    /**
     * 根据机器编号获得所需游戏币数
     * @param machineNo 机器编号
     * @return
     */
    @Override
    public Integer getCoinByMachineNo(String machineNo) {
        try {
            return machineDao.getCoinByMachineNo(machineNo);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("machineNo",machineNo);
            logger.error("{} getCoinByMachineNo param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
        }
        return null;
    }
}
