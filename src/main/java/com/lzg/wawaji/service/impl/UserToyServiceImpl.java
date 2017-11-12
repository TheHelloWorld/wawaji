package com.lzg.wawaji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.UserToyDao;
import com.lzg.wawaji.entity.UserToy;
import com.lzg.wawaji.enums.ChoiceType;
import com.lzg.wawaji.enums.HandleStatus;
import com.lzg.wawaji.service.UserToyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userToyService")
public class UserToyServiceImpl implements UserToyService {

    private static final Logger logger = LoggerFactory.getLogger(UserToyServiceImpl.class);

    @Autowired
    private UserToyDao userToyDao;

    /**
     * 添加用户娃娃记录
     * @param userToy 用户娃娃Bean
     */
    @Override
    public void addUserToy(UserToy userToy) {
        try {
            userToyDao.addUserToy(userToy);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("userToy",userToy);
            logger.error("{} addUserToy param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
        }
    }

    /**
     * 根据用户编号获得用户玩具记录数
     * @param userNo 用户编号
     * @return
     */
    @Override
    public Integer countUserToyByUserNo(String userNo) {
        try {
            return userToyDao.countUserToyByUserNo(userNo);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("userNo", userNo);
            logger.error("{} countUserToyByUserNo param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
            return null;
        }
    }

    /**
     * 根据用户编号分页获得所有用户娃娃记录
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @Override
    public List<UserToy> getUserToyByUserNo(String userNo, int startPage) {
        try {
            return userToyDao.getUserToyByUserNo(userNo, startPage, BaseConstant.DEFAULT_PAGE_SIZE);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("userNo", userNo);
            json.put("startPage", startPage);
            json.put("pageSize", BaseConstant.DEFAULT_PAGE_SIZE);
            logger.error("{} getUserToyByUserNo param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
            return null;
        }

    }

    /**
     * 根据id,用户编号修改选择方式
     * @param choiceType 用户选择方式
     * @param id id
     * @param userNo 用户编号
     */
    @Override
    public void updateChoiceTypeByIdAndUserNo(Integer choiceType, Long id, String userNo) {
        try {
            userToyDao.updateChoiceTypeByIdAndUserNo(choiceType, id, userNo);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("choiceType", ChoiceType.getValueMapByKey(choiceType).name());
            json.put("id", id);
            json.put("userNo", userNo);
            logger.error("{} updateChoiceTypeByIdAndUserNo param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
        }
    }

    /**
     * 根据用id,用户编号修改处理状态
     * @param handleStatus 处理状态
     * @param id id
     * @param userNo 用户编号
     */
    @Override
    public void updateHandleStatusByIdAndUserNo(Integer handleStatus, Long id, String userNo) {
        try {
            userToyDao.updateHandleStatusByIdAndUserNo(handleStatus, id, userNo);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("handleStatus", HandleStatus.getValueMapByKey(handleStatus).name());
            json.put("id", id);
            json.put("userNo", userNo);
            logger.error("{} updateHandleStatusByIdAndUserNo param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
        }

    }
}
