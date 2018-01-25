package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.UserCatchRecord;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.dao.CatchRecordDao;
import com.toiletCat.entity.CatchRecord;
import com.toiletCat.service.CatchRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("all")
@Service("catchRecordService")
public class CatchRecordServiceImpl extends BaseServiceImpl implements CatchRecordService {

    private static final Logger logger = LoggerFactory.getLogger(CatchRecordServiceImpl.class);

    @Autowired
    private CatchRecordDao catchRecordDao;

    /**
     * 根据玩具编号获得最近成功的抓取记录(10条)
     * @param toyNo 玩具编号
     * @return
     */
    @Override
    public CommonResult<List<UserCatchRecord>> getLatelyCatchSuccessRecordByToyNo(final String toyNo) {
        JSONObject json = new JSONObject();
        json.put("toyNo", toyNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(catchRecordDao.getLatelyCatchSuccessRecordByToyNo(toyNo));
            }
        },"getLatelyCatchSuccessRecordByToyNo", json);
    }

    /**
     * 根据用户编号获得用户抓取记录数量
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<Integer> countCatchRecordByUserNo(final String userNo) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(catchRecordDao.countCatchRecordByUserNo(userNo));
            }
        },"countCatchRecordByUserNo", json);
    }

    /**
     * 根据用户编号分页获得用户抓取记录集合
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<UserCatchRecord>> getCatchRecordListByUserNo(final String userNo, final int startPage) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("startPage", startPage);
        json.put("pageSize", BaseConstant.DEFAULT_PAGE_SIZE);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(catchRecordDao.getCatchRecordListByUserNo(userNo, startPage, BaseConstant.DEFAULT_PAGE_SIZE));
            }
        },"getCatchRecordListByUserNo", json);
    }

    /**
     * 根据用户编号,id获得用户抓取记录
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<CatchRecord> getCatchRecordByUserNo(final Long id, final String userNo) {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("userNo", userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(catchRecordDao.getCatchRecordByUserNo(id, userNo));
            }
        },"getCatchRecordByUserNo", json);
    }

    /**
     * 根据id,用户编号修改抓取记录状态
     * @param catchStatus 抓取记录状态
     * @param id id
     * @param userNo 用户编号
     */
    @Override
    public CommonResult updateCatchStatusByIdAndUserNo(final Integer catchStatus, final Long id, final String userNo) {
        JSONObject json = new JSONObject();
        json.put("catchStatus", catchStatus);
        json.put("id", id);
        json.put("userNo", userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                catchRecordDao.updateCatchStatusByIdAndUserNo(catchStatus, id, userNo);
            }
        },"updateCatchStatusByIdAndUserNo", json);
    }

    /**
     * 根据抓取id修改抓取结果
     * @param catchResult 抓取记录结果
     * @param catchId 抓取id
     */
    @Override
    public CommonResult updateCatchResultByCatchId(final Integer catchResult, final String catchId) {
        JSONObject json = new JSONObject();
        json.put("catchResult", catchResult);
        json.put("catchId", catchId);

        return exec(new Callback() {
            @Override
            public void exec() {
                catchRecordDao.updateCatchResultByCatchId(catchResult, catchId);
            }
        },"updateCatchResultByCatchId", json);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
