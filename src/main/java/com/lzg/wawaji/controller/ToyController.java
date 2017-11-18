package com.lzg.wawaji.controller;

import com.alibaba.fastjson.JSON;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.entity.Toy;
import com.lzg.wawaji.service.ToyService;
import com.lzg.wawaji.utils.JSONUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/wawaji/toy")
@Controller
public class ToyController {

    private static final Logger logger = LoggerFactory.getLogger(ToyController.class);

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
        JSONObject json = new JSONObject();

        json.put("list", toyService.getAllToyByPage(startPage));

        return json.toString();
    }

    /**
     * 获得总记录数和每页数据数
     * @return
     */
    @RequestMapping(value = "/getToyTotalCountAndPageSize", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getToyTotalCountAndPageSize() {

        return JSONUtil.getTotalCountAndPageSize(toyService.countAllToy(), BaseConstant.DEFAULT_PAGE_SIZE);

    }

    /**
     * 添加玩具记录
     * @param toyStr 玩具记录
     * @return
     */
    @RequestMapping(value = "/addToy", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addToy(String toyStr) {
        try {
            toyService.addToy(JSON.parseObject(toyStr, Toy.class));
            return BaseConstant.SUCCESS;
        } catch (Exception e) {
            logger.error("{} addToy param:{} error "+e, BaseConstant.LOG_ERR_MSG, toyStr, e);
            return BaseConstant.FAIL;
        }
    }

    /**
     * 根据id和玩具编号获得玩具信息
     * @param id id
     * @param toyNo 玩具编号
     * @return
     */
    @RequestMapping(value = "/getToyByIdAndToyNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getToyByIdAndToyNo(Long id, String toyNo) {

        return String.valueOf(toyService.getToyByIdAndToyNo(id, toyNo));
    }

    /**
     * 根据id和玩具编号修改玩具记录
     * @param toyStr 玩具记录
     * @return
     */
    @RequestMapping(value = "/updateToyByIdAndToyNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateToyByIdAndToyNo(String toyStr) {
        try {
            toyService.updateToyByIdAndToyNo(JSON.parseObject(toyStr, Toy.class));
            return BaseConstant.SUCCESS;
        } catch (Exception e) {
            logger.error("{} updateToyByIdAndToyNo param:{} error "+e, BaseConstant.LOG_ERR_MSG, toyStr, e);
            return BaseConstant.FAIL;
        }
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
        try {
            toyService.deleteToyByIdAndToyNo(id, toyNo);
            return BaseConstant.SUCCESS;
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("id",id);
            json.put("toyNo",toyNo);
            logger.error("{} deleteToyByIdAndToyNo param:{} error "+e, BaseConstant.LOG_ERR_MSG, json, e);
            return BaseConstant.FAIL;
    }
    }
}
