package com.lzg.wawaji.controller;

import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.entity.Toy;
import com.lzg.wawaji.service.ToyService;
import com.lzg.wawaji.utils.JSONUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        JSONObject json = new JSONObject();

        json.put("list", toyService.getAllToyByPage(startPage));

        return json.toString();
    }

    /**
     * 获得总记录数和每页数据数
     * @return
     */
    @RequestMapping(value = "/getTotalCountAndPageSize", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getTotalCountAndPageSize() {

        return JSONUtil.getTotalCountAndPageSize(toyService.countAllToy(), BaseConstant.DEFAULT_PAGE_SIZE);

    }

    /**
     * 添加玩具记录
     * @param toy 玩具记录
     * @return
     */
    @RequestMapping(value = "/addToy", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addToy(Toy toy) {
        toyService.addToy(toy);
        return BaseConstant.SUCCESS;
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
        JSONObject json = new JSONObject();

        json.put("toy", toyService.getToyByIdAndToyNo(id, toyNo));

        return json.toString();
    }

    /**
     * 根据id和玩具编号修改玩具记录
     * @param toy 玩具记录
     * @return
     */
    @RequestMapping(value = "/updateToyByIdAndToyNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateToyByIdAndToyNo(Toy toy) {

        toyService.updateToyByIdAndToyNo(toy);

        return BaseConstant.SUCCESS;
    }
}
