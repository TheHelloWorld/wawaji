package com.toiletCat.service;

import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.UserMachine;
import com.toiletCat.entity.Machine;
import com.toiletCat.enums.HandleType;

import java.util.List;

public interface MachineService {

    /**
     * 添加机器记录
     * @param machine 机器Bean
     */
    CommonResult addMachine(Machine machine);

    /**
     * 获取所有机器记录数量
     * @return
     */
    CommonResult<Integer> countAllMachine();

    /**
     * 分页获得所有机器记录
     * @param startPage 开始页
     * @return
     */
    CommonResult<List<Machine>> getAllMachineByPage(int startPage);

    /**
     * 分页获得所有用户使用娃娃机
     * @param startPage 开始页
     * @return
     */
    CommonResult<List<UserMachine>> getUserAllMachineByPage(int startPage);

    /**
     * 根据id获得机器信息
     * @param id id
     * @param machineNo 机器编号
     * @return
     */
    CommonResult<Machine> getMachineByIdAndMachineNo(Long id, String machineNo);

    /**
     * 根据id,机器编号修改机器记录
     * @param machine 机器Bean
     */
    CommonResult updateMachineByIdAndMachineNo(Machine machine);

    /**
     * 根据id,机器编号删除机器记录
     * @param id id
     * @param machineNo 机器编号
     */
    CommonResult deleteMachineByIdAndMachineNo(Long id, String machineNo);

    /**
     * 根据机器编号获得所需游戏币数
     * @param machineNo 机器编号
     * @return
     */
    CommonResult<Integer> getCoinByMachineNo(String machineNo);

    /**
     * 根据机器编号获得当前机器是否可用
     * @param machineNo 机器编号
     * @return
     */
    CommonResult<String> getMachineInUse(String machineNo);

    /**
     * 根据机器编号获得玩具编号和玩具图片地址
     * @param machineNo 机器编号
     * @return
     */
    CommonResult<UserMachine> getToyNoAndToyImgByMachineNo(String machineNo);

    /**
     * 操作娃娃机围观人数
     * @param machineNo 娃娃机编号
     * @param handleType 操作类型
     * @return
     */
    CommonResult<Long> handleMachineViewer(String machineNo, HandleType handleType);

}
