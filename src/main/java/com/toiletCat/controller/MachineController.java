package com.toiletCat.controller;

import com.alibaba.fastjson.JSON;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.UserMachine;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.entity.Machine;
import com.toiletCat.service.MachineService;

import com.toiletCat.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/toiletCat/api/machine")
@Controller
public class MachineController {

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

        CommonResult<List<Machine>> result = machineService.getAllMachineByPage(startPage);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 分页获得所有用户机器记录
     * @param startPage 开始页
     * @return
     */
    @RequestMapping(value = "/getUserAllMachineByPage", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserAllMachineByPage(int startPage) {

        CommonResult<List<UserMachine>> result = machineService.getUserAllMachineByPage(startPage);

        return JSONUtil.getReturnBeanString(result);
    }


    /**
     * 获得总记录数和每页数据数
     * @return
     */
    @RequestMapping(value = "/getMachineTotalCountAndPageSize", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMachineTotalCountAndPageSize() {

        CommonResult<Integer> result = machineService.countAllMachine();

        return JSONUtil.getTotalCountAndPageSize(result, BaseConstant.DEFAULT_PAGE_SIZE);
    }

    /**
     * 添加机器记录
     * @param paramStr 机器记录
     * @return
     */
    @RequestMapping(value = "/addMachine", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addMachine(String paramStr) {

        CommonResult result = machineService.addMachine(JSON.parseObject(paramStr, Machine.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 根据id和机器编号获得机器记录
     * @param id id
     * @param dataNo 机器编号
     * @return
     */
    @RequestMapping(value = "/getMachineByIdAndMachineNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMachineByIdAndMachineNo(Long id, String dataNo) {

        CommonResult<Machine> result = machineService.getMachineByIdAndMachineNo(id, dataNo);

        return JSONUtil.getReturnStrString(result, String.valueOf(result.getValue()));
    }

    /**
     * 根据id和机器编号修改机器记录
     * @param paramStr 机器记录
     * @return
     */
    @RequestMapping(value = "/updateMachineByIdAndMachineNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateMachineByIdAndMachineNo(String paramStr) {

        CommonResult result =  machineService.updateMachineByIdAndMachineNo(JSON.parseObject(paramStr, Machine.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
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

        CommonResult result =  machineService.deleteMachineByIdAndMachineNo(id, machineNo);

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 根据机器编号判断当前机器是否可用
     * @param machineNo 机器编号
     * @return
     */
    @RequestMapping(value = "/getMachineInUse", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMachineInUse(String machineNo) {

        CommonResult<String> result = machineService.getMachineInUse(machineNo);

        return JSONUtil.getReturnBeanString(result);
    }
}
