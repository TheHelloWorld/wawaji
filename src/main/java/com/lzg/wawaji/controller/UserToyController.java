package com.lzg.wawaji.controller;

import com.alibaba.fastjson.JSON;
import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.entity.UserAddress;
import com.lzg.wawaji.entity.UserToy;
import com.lzg.wawaji.service.UserToyService;
import com.lzg.wawaji.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/wawaji/userToy")
@Controller
public class UserToyController {

    @Autowired
    private UserToyService userToyService;

    /**
     * 根据用户编号分页获得用户战利品
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @RequestMapping(value = "/getUserToyListByUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserToyListByUserNo(String userNo, int startPage) {

        CommonResult<List<UserToy>> result = userToyService.getUserToyListByUserNo(userNo, startPage);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 根据用户编号获得用户战利品数量
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "/countUserToyByUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String countUserToyByUserNo(String userNo) {

        CommonResult<Integer> result = userToyService.countUserToyByUserNo(userNo);

        return JSONUtil.getTotalCountAndPageSize(result, BaseConstant.DEFAULT_PAGE_SIZE);
    }

    /**
     * 添加用户战利品
     * @param paramStr 用户地址
     * @return
     */
    @RequestMapping(value = "/addUserToy", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addUserToy(String paramStr) {

        CommonResult result = userToyService.addUserToy(JSON.parseObject(paramStr, UserToy.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 根据用户编号和id获得用户娃娃记录
     * @param userNo 用户编号
     * @param id id
     * @return
     */
    @RequestMapping(value = "/getUserToyByUserNoAndId", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserToyByUserNoAndId(String userNo, Long id) {

        CommonResult<UserToy> result = userToyService.getUserToyByUserNoAndId(userNo, id);

        return JSONUtil.getReturnStrString(result, String.valueOf(result.getValue()));
    }
    /**
     * 根据用户编号.id修改战利品选择类型
     * @param choiceType 选择类型
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "/updateChoiceTypeByIdAndUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateChoiceTypeByIdAndUserNo(Integer choiceType, Long id, String userNo) {

        CommonResult result =  userToyService.updateChoiceTypeByIdAndUserNo(choiceType, id, userNo);

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 根据用户编号.id修改战利品处理状态
     * @param handleStatus 处理状态
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "/updateHandleStatusByIdAndUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateHandleStatusByIdAndUserNo(Integer handleStatus, Long id, String userNo) {

        CommonResult result =  userToyService.updateHandleStatusByIdAndUserNo(handleStatus, id, userNo);

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

}
