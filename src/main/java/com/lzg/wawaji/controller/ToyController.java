package com.lzg.wawaji.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.entity.Toy;
import com.lzg.wawaji.service.ToyService;
import com.lzg.wawaji.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/wawaji/toy")
@Controller
public class ToyController {

    @Autowired
    private ToyService toyService;

    /**
     * 分页获得所有玩具记录
     * @param startPage 开始页
     * @return
     */
    @RequestMapping(value = "/getAllToyByPage", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getAllToyByPage(int startPage) {

        CommonResult<List<Toy>> result = toyService.getAllToyByPage(startPage);

        if(result.success()) {
            return JSONUtil.getSuccessReturnJSON(result.getValue());
        }
        return JSONUtil.getErrorJson();
    }

    /**
     * 获得总记录数和每页数据数
     * @return
     */
    @RequestMapping(value = "/getTotalCountAndPageSize", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getTotalCountAndPageSize() {

        CommonResult<Integer> result = toyService.countAllToy();

        if(result.success()) {
            return JSONUtil.getTotalCountAndPageSize(result.getValue(), BaseConstant.DEFAULT_PAGE_SIZE);
        }
        return JSONUtil.getErrorJson();
    }

    /**
     * 添加玩具记录
     * @param paramStr 玩具记录
     * @return
     */
    @RequestMapping(value = "/addToy", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addToy(String paramStr) {

        CommonResult result = toyService.addToy(JSON.parseObject(paramStr, Toy.class));

        if(result.success()) {
            return JSONUtil.getSuccessReturnJSON(BaseConstant.SUCCESS);
        }
        return JSONUtil.getErrorJson();
    }

    /**
     * 根据id和玩具编号获得玩具信息
     * @param id id
     * @param dataNo 玩具编号
     * @return
     */
    @RequestMapping(value = "/getToyByIdAndToyNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getToyByIdAndToyNo(Long id, String dataNo) {

        CommonResult<Toy> result = toyService.getToyByIdAndToyNo(id, dataNo);

        if(result.success()) {
            return JSONUtil.getSuccessReturnJSON(String.valueOf(result.getValue()));
        }
        return JSONUtil.getErrorJson();
    }

    /**
     * 根据id和玩具编号修改玩具记录
     * @param paramStr 玩具记录
     * @return
     */
    @RequestMapping(value = "/updateToyByIdAndToyNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateToyByIdAndToyNo(String paramStr) {

        CommonResult result = toyService.updateToyByIdAndToyNo(JSON.parseObject(paramStr, Toy.class));

        if(result.success()) {
            return JSONUtil.getSuccessReturnJSON(BaseConstant.SUCCESS);
        }
        return JSONUtil.getErrorJson();
    }

    /**
     * 根据id和玩具编号删除记录
     * @param id id
     * @param toyNo 玩具编号
     * @return
     */
    @RequestMapping(value = "/deleteToyByIdAndToyNo", method = RequestMethod.POST)
    @ResponseBody
    public String deleteToyByIdAndToyNo(Long id, String toyNo) {

        CommonResult result = toyService.deleteToyByIdAndToyNo(id, toyNo);

        if(result.success()) {
            return JSONUtil.getSuccessReturnJSON(BaseConstant.SUCCESS);
        }
        return JSONUtil.getErrorJson();
    }
}
