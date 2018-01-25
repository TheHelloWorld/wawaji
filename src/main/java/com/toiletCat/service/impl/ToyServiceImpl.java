package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.dao.ToyDao;
import com.toiletCat.entity.Toy;
import com.toiletCat.service.ToyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("all")
@Service("toyService")
public class ToyServiceImpl extends BaseServiceImpl implements ToyService {

    private static final Logger logger = LoggerFactory.getLogger(ToyServiceImpl.class);

    @Autowired
    private ToyDao toyDao;

    /**
     * 添加娃娃记录
     * @param toy 娃娃记录
     */
    @Override
    public CommonResult addToy(final Toy toy) {

        return exec(new Callback() {
            @Override
            public void exec() {
                toyDao.addToy(toy);
            }
        }, "addToy", toy);
    }

    /**
     * 获得所有娃娃记录数量
     * @return
     */
    @Override
    public CommonResult<Integer> countAllToy() {

        return exec(new Callback() {
            @Override
            public void exec() {
                got(toyDao.countAllToy());
            }
        },"countAllToy", new JSONObject());

    }

    /**
     * 分页获得所有娃娃记录
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<Toy>> getAllToyByPage(final int startPage) {

        JSONObject json = new JSONObject();
        json.put("startPage",startPage);
        json.put("pageSize", BaseConstant.DEFAULT_PAGE_SIZE);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(toyDao.getAllToyByPage(startPage, BaseConstant.DEFAULT_PAGE_SIZE));
            }
        }, "getAllToyByPage", json);
    }

    /**
     * 根据id,娃娃编号获得娃娃及信息
     * @param id id
     * @param toyNo 玩具编号
     * @return
     */
    @Override
    public CommonResult<Toy> getToyByIdAndToyNo(final Long id, final String toyNo) {

        JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("toyNo",toyNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(toyDao.getToyByIdAndToyNo(id, toyNo));
            }
        }, "getToyByIdAndToyNo", json);
    }

    /**
     * 根据id,娃娃机编号修改娃娃机记录
     * @param toy 娃娃机Bean
     */
    @Override
    public CommonResult updateToyByIdAndToyNo(final Toy toy) {
        return exec(new Callback() {
            @Override
            public void exec() {
                toyDao.updateToyByIdAndToyNo(toy);
            }
        }, "updateToyByIdAndToyNo", toy);
    }

    /**
     * 根据id,玩具编号删除娃娃记录
     * @param id id
     * @param toyNo 玩具编号
     */
    @Override
    public CommonResult deleteToyByIdAndToyNo(final Long id, final String toyNo) {

        JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("toyNo",toyNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                toyDao.deleteToyByIdAndToyNo(id, toyNo);
            }
        }, "deleteToyByIdAndToyNo", json);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
