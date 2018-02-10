package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.dao.BannerImgDao;
import com.toiletCat.entity.BannerImg;
import com.toiletCat.enums.BannerType;
import com.toiletCat.service.BannerImgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("all")
@Service("bannerImgService")
public class BannerImgServiceImpl extends BaseServiceImpl implements BannerImgService {

    private static final Logger logger = LoggerFactory.getLogger(BannerImgServiceImpl.class);

    @Autowired
    private BannerImgDao bannerImgDao;

    /**
     * 添加banner图片
     * @param bannerImg banner图片
     */
    @Override
    public CommonResult addBannerImg(final BannerImg bannerImg) {
        return exec(new Callback() {
            @Override
            public void exec() {
                bannerImgDao.addBannerImg(bannerImg);
            }
        }, false, "addBannerImg", bannerImg);
    }

    /**
     * 分页获得所有banner图片记录
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<BannerImg>> getBannerImgByPage(final int startPage) {
        JSONObject json = new JSONObject();
        json.put("startPage",startPage);
        json.put("pageSize",BaseConstant.DEFAULT_PAGE_SIZE);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(bannerImgDao.getBannerImgByPage(startPage, BaseConstant.DEFAULT_PAGE_SIZE));
            }
        },true, "getBannerImgByPage", json);
    }

    /**
     * 获得所有banner图片记录数量
     * @return
     */
    @Override
    public CommonResult<Integer> countAllBannerImg() {

        return exec(new Callback() {
            @Override
            public void exec() {
                got(bannerImgDao.countAllBannerImg());
            }
        },false, "countAllBannerImg", new JSONObject());
    }

    /**
     * 根据banner图片id获得数据信息
     * @param id id
     * @return
     */
    @Override
    public CommonResult<BannerImg> getBannerImgById(final Long id) {
        JSONObject json = new JSONObject();
        json.put("id",id);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(bannerImgDao.getBannerImgById(id));
            }
        },true, "getBannerImgById", json);
    }

    /**
     * 修改banner图片
     * @param bannerImg bean
     */
    @Override
    public CommonResult updateBannerImg(final BannerImg bannerImg) {
        return exec(new Callback() {
            @Override
            public void exec() {
                bannerImgDao.updateBannerImg(bannerImg);
            }
        }, false, "updateBannerImg", bannerImg);
    }

    /**
     * 根据banner类型获得banner图
     * @param bannerType banner类型
     * @return
     */
    @Override
    public CommonResult<List<BannerImg>> getBannerImgByBannerType(final BannerType bannerType) {

        JSONObject json = new JSONObject();
        json.put("bannerType",bannerType.name());

        return exec(new Callback() {
            @Override
            public void exec() {
                got(bannerImgDao.getBannerImgByBannerType(bannerType.getStatus()));
            }
        }, true, "getBannerImgByBannerType", json);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
