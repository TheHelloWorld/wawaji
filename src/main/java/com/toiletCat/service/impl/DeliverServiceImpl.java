package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.dao.DeliverDao;
import com.toiletCat.entity.Deliver;
import com.toiletCat.service.DeliverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("all")
@Service("deliverService")
public class DeliverServiceImpl extends BaseServiceImpl implements DeliverService {

    private static final Logger logger = LoggerFactory.getLogger(DeliverServiceImpl.class);

    @Autowired
    private DeliverDao deliverDao;

    /**
     * 添加发货记录
     * @param deliver 发货记录
     */
    @Override
    public CommonResult addDeliver(final Deliver deliver) {

        return exec(new Callback() {
            @Override
            public void exec() {
                deliverDao.addDeliver(deliver);
            }
        }, false, "addDeliver", deliver);
    }

    /**
     * 根据用户编号获得用户发货记录数量
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<Integer> countDeliverByUserNo(final String userNo) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(deliverDao.countDeliverByUserNo(userNo));
            }
        },true, "countDeliverByUserNo", json);
    }

    /**
     * 获得所有用户发货数量
     * @return
     */
    @Override
    public CommonResult<Integer> countAllDeliver() {

        return exec(new Callback() {
            @Override
            public void exec() {
                got(deliverDao.countAllDeliver());
            }
        },false, "countAllDeliver", new JSONObject());

    }

    /**
     * 根据用户编号分页获得用户发货记录集合
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<Deliver>> getDeliverByUserNo(final String userNo, final int startPage) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);
        json.put("startPage",startPage);
        json.put("pageSize", BaseConstant.DEFAULT_PAGE_SIZE);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(deliverDao.getDeliverByUserNo(userNo, startPage, BaseConstant.DEFAULT_PAGE_SIZE));
            }
        },true, "getDeliverByUserNo", json);

    }

    /**
     * 分页获得所有用户发货记录
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<Deliver>> getAllDeliverByPage(final int startPage) {

        JSONObject json = new JSONObject();
        json.put("startPage",startPage);
        json.put("pageSize",BaseConstant.DEFAULT_PAGE_SIZE);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(deliverDao.getAllDeliverByPage(startPage, BaseConstant.DEFAULT_PAGE_SIZE));
            }
        },true, "getAllDeliverByPage", json);
    }

    /**
     * 根据id,用户编号获得用户发货记录
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<Deliver> getDeliverByIdAndUserNo(final Long id, final String userNo) {

        JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("userNo",userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(deliverDao.getDeliverByIdAndUserNo(id, userNo));
            }
        },true, "getDeliverByIdAndUserNo", json);
    }

    /**
     * 根据id,用户编号修改货物详情及状态
     * @param deliver 货物详情
     */
    @Override
    public CommonResult updateDeliverMsgByIdAndUserNo(final Deliver deliver) {

        return exec(new Callback() {
            @Override
            public void exec() {
                deliverDao.updateDeliverMsgByIdAndUserNo(deliver);
            }
        }, false, "updateDeliverMsgByIdAndUserNo", deliver);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
