package com.lzg.wawaji.controller;

import com.alibaba.fastjson.JSON;
import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.entity.BannerImg;
import com.lzg.wawaji.enums.BannerType;
import com.lzg.wawaji.service.BannerImgService;
import com.lzg.wawaji.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/wawaji/bannerImg")
@Controller
public class BannerImgController {

    @Autowired
    private BannerImgService bannerImgService;

    /**
     * 分页获得所有banner图片记录
     * @param startPage 开始页
     * @return
     */
    @RequestMapping(value = "/getBannerImgByPage", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getBannerImgByPage(int startPage) {

        CommonResult<List<BannerImg>> result = bannerImgService.getBannerImgByPage(startPage);

        return JSONUtil.getReturnBeanString(result);
    }


    /**
     * 获得总记录数和每页数据数
     * @return
     */
    @RequestMapping(value = "/getBannerImgTotalCountAndPageSize", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getBannerImgTotalCountAndPageSize() {

        CommonResult<Integer> result = bannerImgService.countAllBannerImg();

        return JSONUtil.getTotalCountAndPageSize(result, BaseConstant.DEFAULT_PAGE_SIZE);
    }

    /**
     * 添加banner图片记录
     * @param paramStr banner图片记录
     * @return
     */
    @RequestMapping(value = "/addBannerImg", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addBannerImg(String paramStr) {

        CommonResult result = bannerImgService.addBannerImg(JSON.parseObject(paramStr, BannerImg.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 根据banner图片id获得数据信息
     * @param id id
     * @return
     */
    @RequestMapping(value = "/getBannerImgById", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getBannerImgById(Long id) {

        CommonResult<BannerImg> result = bannerImgService.getBannerImgById(id);

        return JSONUtil.getReturnStrString(result, String.valueOf(result.getValue()));
    }

    /**
     * 修改banner图片
     * @param paramStr banner图片记录
     */
    @RequestMapping(value = "/updateBannerImg", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateBannerImg(String paramStr) {

        CommonResult result = bannerImgService.updateBannerImg(JSON.parseObject(paramStr, BannerImg.class));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 根据banner类型获得banner图
     * @param bannerType banner类型
     * @return
     */
    @RequestMapping(value = "/getBannerImgByBannerType", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getBannerImgByBannerType(Integer bannerType) {

        CommonResult result = bannerImgService.getBannerImgByBannerType(BannerType.getValueMapByKey(bannerType));

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

}
