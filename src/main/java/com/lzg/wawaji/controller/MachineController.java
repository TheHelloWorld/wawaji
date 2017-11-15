package com.lzg.wawaji.controller;

import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.entity.Machine;
import com.lzg.wawaji.service.MachineService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/wawaji/machine")
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

        JSONObject json = new JSONObject();

        json.put("pageSize", BaseConstant.DEFAULT_PAGE_SIZE);
        json.put("list", JSONArray.fromObject(machineService.getAllMachineByPage(startPage)));
        json.put("totalCount", machineService.countAllMachine());

        return json.toString();
    }

    /**
     * 添加机器记录
     * @param machine 机器记录
     * @return
     */
    @RequestMapping(value = "/addMachine", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addMachine(Machine machine) {
        machineService.addMachine(machine);
        return BaseConstant.SUCCESS;
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
        JSONObject json = new JSONObject();

        json.put("machine", machineService.getMachineByIdAndMachineNo(id, machineNo));

        return json.toString();
    }

    /**
     * 根据id和机器编号获得机器记录
     * @param machine 机器记录
     * @return
     */
    @RequestMapping(value = "/updateMachineByIdAndMachineNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateMachineByIdAndMachineNo(Machine machine) {
        machineService.updateMachineByIdAndMachineNo(machine);
        return BaseConstant.SUCCESS;
    }
}
