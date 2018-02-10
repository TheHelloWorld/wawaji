package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.dao.UserToyHandleDao;
import com.toiletCat.entity.UserToyHandle;
import com.toiletCat.service.UserToyHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("all")
@Service("userToyHandleService")
public class UserToyHandleServiceImpl extends BaseServiceImpl implements UserToyHandleService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserToyHandleServiceImpl.class);

    @Autowired
    private UserToyHandleDao userToyHandleDao;

    /**
     * 添加用户战利品处理记录
     * @param userToyHandle 用户战利品处理Bean
     */
    @Override
    public CommonResult addUserToyHandle(final UserToyHandle userToyHandle) {
        return exec(new Callback() {
            @Override
            public void exec() {
                userToyHandleDao.addUserToyHandle(userToyHandle);
            }
        }, false, "addUserToyHandle", userToyHandle);
    }

    /**
     * 根据用户编号获得用户玩具战利品处理数
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<Integer> countUserToyHandleByUserNo(final String userNo) {
        JSONObject json = new JSONObject();
        json.put("userNo",userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userToyHandleDao.countUserToyHandleByUserNo(userNo));
            }
        },true, "countUserToyHandleByUserNo", json);
    }

    /**
     * 根据用户编号分页获得所有用户战利品处理记录集合
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<UserToyHandle>> getUserToyHandleListByUserNo(final String userNo, final int startPage) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("startPage", startPage);
        json.put("pageSize", BaseConstant.DEFAULT_PAGE_SIZE);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userToyHandleDao.getUserToyHandleListByUserNo(userNo, startPage, BaseConstant.DEFAULT_PAGE_SIZE));
            }
        },true, "getUserToyHandleListByUserNo", json);
    }

    /**
     * 根据用户编号和id获得用户战利品处理记录
     * @param userNo 用户编号
     * @param id id
     * @return
     */
    @Override
    public CommonResult<UserToyHandle> getUserToyHandleByUserNoAndId(final String userNo, final Long id) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("id", id);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userToyHandleDao.getUserToyHandleByUserNoAndId(userNo, id));
            }
        },true, "getUserToyByUserNoAndId", json);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

}
