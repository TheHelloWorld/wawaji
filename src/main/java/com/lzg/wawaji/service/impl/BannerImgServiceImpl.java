package com.lzg.wawaji.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.bean.Callback;
import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.BannerImgDao;
import com.lzg.wawaji.entity.BannerImg;
import com.lzg.wawaji.enums.BannerType;
import com.lzg.wawaji.service.BannerImgService;
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
        }, "addBannerImg", JSON.toJSONString(bannerImg));
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
        },"getBannerImgByPage", json.toJSONString());
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
        },"countAllBannerImg", new JSONObject().toJSONString());
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
        },"getBannerImgById", json.toJSONString());
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
        }, "updateBannerImg", JSON.toJSONString(bannerImg));
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
        }, "getBannerImgByBannerType", json.toJSONString());
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
