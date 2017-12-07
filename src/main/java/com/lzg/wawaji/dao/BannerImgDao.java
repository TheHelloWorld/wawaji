package com.lzg.wawaji.dao;

import com.lzg.wawaji.entity.BannerImg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bannerImgDao")
public interface BannerImgDao {

    /**
     * 添加banner图片
     * @param bannerImg banner图片
     */
    void addBannerImg(BannerImg bannerImg);

    /**
     * 分页获得所有banner图片记录
     * @param startPage 开始页
     * @param pageSize 每页数据数
     * @return
     */
    List<BannerImg> getBannerImgByPage(@Param("startPage") int startPage, @Param("pageSize") int pageSize);

    /**
     * 获得所有banner图片记录数量
     * @return
     */
    Integer countAllBannerImg();

    /**
     * 根据banner图片id获得数据信息
     * @param id id
     * @return
     */
    BannerImg getBannerImgById(Long id);

    /**
     * 修改banner图片
     * @param bannerImg bean
     */
    void updateBannerImg(BannerImg bannerImg);

}
