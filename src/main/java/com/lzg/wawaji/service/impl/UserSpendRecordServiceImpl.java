package com.lzg.wawaji.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.bean.Callback;
import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.UserSpendRecordDao;
import com.lzg.wawaji.entity.UserSpendRecord;
import com.lzg.wawaji.service.UserSpendRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("all")
@Service("userSpendRecordService")
public class UserSpendRecordServiceImpl extends BaseServiceImpl implements UserSpendRecordService {

    private static final Logger logger = LoggerFactory.getLogger(UserSpendRecordServiceImpl.class);

    @Autowired
    private UserSpendRecordDao userSpendRecordDao;

    /**
     * 添加用户消费记录
     * @param userSpendRecord 用户消费记录
     */
    @Override
    public CommonResult addUserSpendRecord(final UserSpendRecord userSpendRecord) {

        return exec(new Callback() {
            @Override
            public void exec() {
                userSpendRecordDao.addUserSpendRecord(userSpendRecord);
            }
        }, "addUserSpendRecord", JSON.toJSONString(userSpendRecord));
    }

    /**
     * 根据用户编号获得用户消费记录数量
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<Integer> countUserSpendRecordByUserNo(final String userNo) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userSpendRecordDao.countUserSpendRecordByUserNo(userNo));
            }
        }, "countUserSpendRecordByUserNo", json.toJSONString());

    }

    /**
     * 根据用户编号分页获得用户消费记录
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<UserSpendRecord>> getUserSpendRecordByUserNo(final String userNo, final int startPage) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);
        json.put("startPage",startPage);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userSpendRecordDao.getUserSpendRecordByUserNo(userNo, startPage, BaseConstant.DEFAULT_PAGE_SIZE));
            }
        }, "getUserSpendRecordByUserNo", json.toJSONString());
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
