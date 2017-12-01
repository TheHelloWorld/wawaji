package com.lzg.wawaji.controller;

import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.entity.UserSpendRecord;
import com.lzg.wawaji.service.UserSpendRecordService;
import com.lzg.wawaji.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/wawaji/userSpendRecord")
@Controller
public class UserSpendRecordController {

    @Autowired
    private UserSpendRecordService userSpendRecordService;

    /**
     * 根据用户编号和开始页获得用户消费记录
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @RequestMapping(value = "/getUserSpendRecordByUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserSpendRecordByUserNo(String userNo, int startPage) {

        CommonResult<List<UserSpendRecord>> result = userSpendRecordService.getUserSpendRecordByUserNo(userNo, startPage);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 获得总记录数和每页数据数
     * @return
     */
    @RequestMapping(value = "/countUserSpendRecordByUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String countUserSpendRecordByUserNo(String userNo) {

        CommonResult<Integer> result = userSpendRecordService.countUserSpendRecordByUserNo(userNo);

        return JSONUtil.getTotalCountAndPageSize(result, BaseConstant.DEFAULT_PAGE_SIZE);
    }


}
