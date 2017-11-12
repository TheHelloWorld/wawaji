package com.lzg.wawaji.service;

import com.lzg.wawaji.entity.Machine;

import java.util.List;

public interface MachineService {

    /**
     * 添加机器记录
     * @param machine 机器Bean
     */
    void addMachine(Machine machine);

    /**
     * 获取所有机器记录数量
     * @return
     */
    Integer countAllMachine();

    /**
     * 分页获得所有机器记录
     * @param startPage 开始页
     * @return
     */
    List<Machine> getAllMachineByPage(int startPage);

    /**
     * 根据id获得机器信息
     * @param id id
     * @param machineNo 机器编号
     * @return
     */
    Machine getMachineByIdAndMachineNo(Long id, String machineNo);

    /**
     * 根据id,机器编号修改机器记录
     * @param machine 机器Bean
     */
    void updateMachineByIdAndMachineNo(Machine machine);

    /**
     * 根据id,机器编号删除机器记录
     * @param id id
     * @param machineNo 机器编号
     */
    void deleteMachineByIdAndMachineNo(Long id, String machineNo);

    /**
     * 根据机器编号获得机器信息
     * @param machineNo 机器编号
     * @return
     */
    Machine getMachineByMachineNo(String machineNo);

    /**
     * 根据机器编号获得所需游戏币数
     * @param machineNo 机器编号
     * @return
     */
    Integer getCoinByMachineNo(String machineNo);
}
