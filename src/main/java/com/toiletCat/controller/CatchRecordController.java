package com.toiletCat.controller;

import com.alibaba.fastjson.JSON;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.UserCatchRecord;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.entity.CatchRecord;
import com.toiletCat.service.CatchRecordService;
import com.toiletCat.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/toiletCat/catchRecord")
@Controller
public class CatchRecordController {

    @Autowired
    private CatchRecordService catchRecordService;

    /**
     * 根据玩具编号获得最近成功的抓取记录(10条)
     * @param toyNo 玩具编号
     * @return
     */
    @RequestMapping(value = "/getLatelyCatchSuccessRecordByToyNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getLatelyCatchSuccessRecordByToyNo(String toyNo) {

        CommonResult<List<UserCatchRecord>> result = catchRecordService.getLatelyCatchSuccessRecordByToyNo(toyNo);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 根据用户编号分页获得用户抓取记录集合
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @RequestMapping(value = "/getCatchRecordListByUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getCatchRecordListByUserNo(String userNo, int startPage) {

        CommonResult result = catchRecordService.getCatchRecordListByUserNo(userNo, startPage);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 根据用户编号获得用户抓取记录数量
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "/countCatchRecordByUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String countCatchRecordByUserNo(String userNo) {

        CommonResult<Integer> result = catchRecordService.countCatchRecordByUserNo(userNo);

        return JSONUtil.getTotalCountAndPageSize(result, BaseConstant.DEFAULT_PAGE_SIZE);
    }

    /**
     * 根据用户编号,id获得用户抓取记录
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "/getCatchRecordByUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getCatchRecordByUserNo(Long id, String userNo) {

        CommonResult<CatchRecord> result = catchRecordService.getCatchRecordByUserNo(id, userNo);

        return JSONUtil.getReturnStrString(result, String.valueOf(result.getValue()));
    }

    /**
     * 根据id,用户编号修改抓取记录状态
     * @param catchStatus 抓去记录状态
     * @param id id
     * @param userNo 用户编号
     */
    @RequestMapping(value = "/updateCatchStatusByIdAndUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateCatchStatusByIdAndUserNo(Integer catchStatus, Long id, String userNo) {

        CommonResult result = catchRecordService.updateCatchStatusByIdAndUserNo(catchStatus, id, userNo);

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }
}
