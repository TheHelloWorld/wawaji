package com.lzg.wawaji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.UserSpendRecordDao;
import com.lzg.wawaji.entity.UserSpendRecord;
import com.lzg.wawaji.service.UserSpendRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userSpendRecordService")
public class UserSpendRecordServiceImpl implements UserSpendRecordService {

    private static final Logger logger = LoggerFactory.getLogger(UserSpendRecordServiceImpl.class);

    @Autowired
    private UserSpendRecordDao userSpendRecordDao;

    /**
     * 添加用户消费记录
     * @param userSpendRecord 用户消费记录
     */
    @Override
    public void addUserSpendRecord(UserSpendRecord userSpendRecord) {
        try {
            userSpendRecordDao.addUserSpendRecord(userSpendRecord);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("userSpendRecord",userSpendRecord);
            logger.error("{} addUserSpendRecord param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
        }
    }

    /**
     * 根据用户编号获得用户消费记录数量
     * @param userNo 用户编号
     * @return
     */
    @Override
    public Integer countUserSpendRecordByUserNo(String userNo) {
        try {
            return userSpendRecordDao.countUserSpendRecordByUserNo(userNo);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("userNo",userNo);
            logger.error("{} countUserSpendRecordByUserNo param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
            return null;
        }
    }

    /**
     * 根据用户编号分页获得用户消费记录
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @Override
    public List<UserSpendRecord> getUserSpendRecordByUserNo(String userNo, int startPage) {
        try {
            return userSpendRecordDao.getUserSpendRecordByUserNo(userNo, startPage, BaseConstant.DEFAULT_PAGE_SIZE);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("userNo",userNo);
            json.put("startPage",startPage);
            logger.error("{} getUserSpendRecordByUserNo param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
            return null;
        }
    }
}
