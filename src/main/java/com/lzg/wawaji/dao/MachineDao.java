package com.lzg.wawaji.dao;

import com.lzg.wawaji.entity.Machine;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("machineDao")
public interface MachineDao {

    /**
     * 添加机器记录
     * @param machine 机器Bean
     */
    void addMachine(Machine machine);

    /**
     * 获得所有机器记录数
     * @return
     */
    Integer countAllMachine();

    /**
     * 分页获得所有机器记录
     * @param startPage 开始页数
     * @param pageSize 每页数据数
     * @return
     */
    List<Machine> getAllMachineByPage(@Param("startPage") int startPage, @Param("pageSize") int pageSize);

    /**
     * 根据id和机器编号获得机器记录
     * @param id id
     * @param machineNo 机器编号
     * @return
     */
    Machine getMachineByIdAndMachineNo(@Param("id") Long id, @Param("machineNo") String machineNo);

    /**
     * 根据id,机器编号修改机器记录
     * @param machine 机器Bean
     */
    void updateMachineByIdAndMachineNo(Machine machine);

    /**
     * 根据机器编号获得机器记录
     * @param machineNo 机器编号
     * @return
     */
    Machine getMachineByMachineNo(String machineNo);

    /**
     * 根据机器编号获得机器所需游戏币数
     * @param machineNo 机器编号
     * @return
     */
    Integer getCoinByMachineNo(String machineNo);

    /**
     * 根据id和机器编号删除机器记录
     * @param id id
     * @param machineNo 机器编号
     */
    void deleteMachineByIdAndToyNo(@Param("id") Long id, @Param("machineNo") String machineNo);

}
