package com.lzg.wawaji.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.entity.Machine;
import com.lzg.wawaji.service.MachineService;

import com.lzg.wawaji.utils.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/wawaji/machine")
@Controller
public class MachineController {

    private static final Logger logger = LoggerFactory.getLogger(MachineController.class);

    @Autowired
    private MachineService machineService;

    /**
     * 分页获得所有机器记录
     * @param startPage 开始页
     * @return
     */
    @RequestMapping(value = "/getAllMachineByPage", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllMachineByPage(int startPage) {

        JSONObject json = new JSONObject();
        json.put("list", machineService.getAllMachineByPage(startPage));

        return json.toString();
    }


    /**
     * 获得总记录数和每页数据数
     * @return
     */
    @RequestMapping(value = "/getMachineTotalCountAndPageSize", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMachineTotalCountAndPageSize() {

        return JSONUtil.getTotalCountAndPageSize(machineService.countAllMachine(), BaseConstant.DEFAULT_PAGE_SIZE);

    }
    /**
     * 添加机器记录
     * @param machineStr 机器记录
     * @return
     */
    @RequestMapping(value = "/addMachine", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addMachine(String machineStr) {
        try{
            machineService.addMachine(JSON.parseObject(machineStr, Machine.class));
            return BaseConstant.SUCCESS;
        } catch(Exception e) {
            logger.error("{} addMachine param:{} error "+e, BaseConstant.LOG_ERR_MSG, machineStr, e);
            return BaseConstant.FAIL;
        }


    }

    /**
     * 根据id和机器编号获得机器记录
     * @param id id
     * @param machineNo 机器编号
     * @return
     */
    @RequestMapping(value = "/getMachineByIdAndMachineNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMachineByIdAndMachineNo(Long id, String machineNo) {
        return String.valueOf(machineService.getMachineByIdAndMachineNo(id, machineNo));
    }

    /**
     * 根据id和机器编号获得机器记录
     * @param machineStr 机器记录
     * @return
     */
    @RequestMapping(value = "/updateMachineByIdAndMachineNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateMachineByIdAndMachineNo(String machineStr) {
        try{
            machineService.updateMachineByIdAndMachineNo(JSON.parseObject(machineStr, Machine.class));
            return BaseConstant.SUCCESS;
        } catch(Exception e) {
            logger.error("{} updateMachineByIdAndMachineNo param:{} error "+e, BaseConstant.LOG_ERR_MSG, machineStr, e);
            return BaseConstant.FAIL;
        }
    }

    /**
     * 根据id和机器编号删除机器记录
     * @param id id
     * @param machineNo 机器编号
     * @return
     */
    @RequestMapping(value = "/deleteMachineByIdAndToyNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteMachineByIdAndToyNo(Long id, String machineNo) {
        try{
            machineService.deleteMachineByIdAndMachineNo(id, machineNo);
            return BaseConstant.SUCCESS;
        } catch(Exception e) {
            JSONObject json = new JSONObject();
            json.put("id",id);
            json.put("machineNo",machineNo);
            logger.error("{} deleteMachineByIdAndToyNo param:{} error "+e, BaseConstant.LOG_ERR_MSG, json, e);
            return BaseConstant.FAIL;
        }
    }
}
