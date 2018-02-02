package com.toiletCat.controller;

import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.entity.UserToyHandle;
import com.toiletCat.service.UserToyHandleService;
import com.toiletCat.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/toiletCat/api/userToyHandle")
@Controller
public class UserToyHandleController {

    @Autowired
    private UserToyHandleService userToyHandleService;

    /**
     * 根据用户编号分页获得所有用户战利品处理记录集合
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @RequestMapping(value = "/getUserToyHandleListByUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserToyHandleListByUserNo(String userNo, int startPage) {

        CommonResult<List<UserToyHandle>> result = userToyHandleService.getUserToyHandleListByUserNo(userNo, startPage);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 根据用户编号获得用户玩具战利品处理数
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "/countUserToyHandleByUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String countUserToyHandleByUserNo(String userNo) {

        CommonResult<Integer> result = userToyHandleService.countUserToyHandleByUserNo(userNo);

        return JSONUtil.getTotalCountAndPageSize(result, BaseConstant.DEFAULT_PAGE_SIZE);
    }

    /**
     * 根据用户编号和id获得用户战利品处理记录
     * @param userNo 用户编号
     * @param id id
     * @return
     */
    @RequestMapping(value = "/getUserToyHandleByUserNoAndId", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserToyHandleByUserNoAndId(String userNo, Long id) {

        CommonResult<UserToyHandle> result = userToyHandleService.getUserToyHandleByUserNoAndId(userNo, id);

        return JSONUtil.getReturnStrString(result, String.valueOf(result.getValue()));
    }

}
