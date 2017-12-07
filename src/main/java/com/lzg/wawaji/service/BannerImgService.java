package com.lzg.wawaji.service;

import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.entity.BannerImg;
import com.lzg.wawaji.enums.BannerType;

import java.util.List;

public interface BannerImgService {

    /**
     * 添加banner图片
     * @param bannerImg banner图片
     */
    CommonResult addBannerImg(BannerImg bannerImg);

    /**
     * 分页获得所有banner图片记录
     * @param startPage 开始页
     * @return
     */
    CommonResult<List<BannerImg>> getBannerImgByPage(int startPage);

    /**
     * 获得所有banner图片记录数量
     * @return
     */
    CommonResult<Integer> countAllBannerImg();

    /**
     * 根据banner图片id获得数据信息
     * @param id id
     * @return
     */
    CommonResult<BannerImg> getBannerImgById(Long id);

    /**
     * 修改banner图片
     * @param bannerImg bean
     */
    CommonResult updateBannerImg(BannerImg bannerImg);

    /**
     * 根据banner类型获得banner图
     * @param bannerType banner类型
     * @return
     */
    CommonResult<List<BannerImg>> getBannerImgByBannerType(BannerType bannerType);

}
