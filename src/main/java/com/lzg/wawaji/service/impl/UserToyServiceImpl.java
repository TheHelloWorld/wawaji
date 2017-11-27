package com.lzg.wawaji.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.bean.Callback;
import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.DeliverDao;
import com.lzg.wawaji.dao.UserToyDao;
import com.lzg.wawaji.entity.Deliver;
import com.lzg.wawaji.entity.UserToy;
import com.lzg.wawaji.enums.ChoiceType;
import com.lzg.wawaji.enums.HandleStatus;
import com.lzg.wawaji.service.UserToyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("all")
@Service("userToyService")
public class UserToyServiceImpl extends BaseServiceImpl implements UserToyService {

    private static final Logger logger = LoggerFactory.getLogger(UserToyServiceImpl.class);

    @Autowired
    private UserToyDao userToyDao;

    @Autowired
    private DeliverDao deliverDao;

    /**
     * 添加用户娃娃记录
     * @param userToy 用户娃娃Bean
     */
    @Override
    public CommonResult addUserToy(final UserToy userToy) {

        return exec(new Callback() {
            @Override
            public void exec() {
                userToyDao.addUserToy(userToy);
            }
        }, "addUserToy", JSON.toJSONString(userToy));
    }

    /**
     * 根据用户编号获得用户玩具记录数
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<Integer> countUserToyByUserNo(final String userNo) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userToyDao.countUserToyByUserNo(userNo));
            }
        },"countUserToyByUserNo", json.toJSONString());
    }

    /**
     * 根据用户编号分页获得所有用户娃娃记录
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<UserToy>> getUserToyListByUserNo(final String userNo, final int startPage) {

        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("startPage", startPage);
        json.put("pageSize", BaseConstant.DEFAULT_PAGE_SIZE);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userToyDao.getUserToyListByUserNo(userNo, startPage, BaseConstant.DEFAULT_PAGE_SIZE));
            }
        },"getUserToyByUserNo", json.toJSONString());
    }

    /**
     * 根据用户编号和id获得用户娃娃记录
     * @param userNo 用户编号
     * @param id id
     * @return
     */
    @Override
    public CommonResult<UserToy> getUserToyByUserNoAndId(final String userNo, final Long id) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("id", id);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userToyDao.getUserToyByUserNoAndId(userNo, id));
            }
        },"getUserToyByUserNoAndId", json.toJSONString());
    }

    /**
     * 根据id,用户编号修改选择方式
     * @param choiceType 用户选择方式
     * @param id id
     * @param userNo 用户编号
     */
    @Override
    public CommonResult updateChoiceTypeByIdAndUserNo(final Integer choiceType, final Long id, final String userNo) {

        JSONObject json = new JSONObject();
        json.put("choiceType", ChoiceType.getValueMapByKey(choiceType).name());
        json.put("id", id);
        json.put("userNo", userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                // TODO lzk 在琢磨琢磨选择发货后怎么办
                if(ChoiceType.FOR_DELIVER.getStatus() == choiceType) {
                    Deliver deliver = new Deliver();
                    deliver.setUserNo(userNo);

                }
                userToyDao.updateChoiceTypeByIdAndUserNo(choiceType, id, userNo);
            }
        }, "updateChoiceTypeByIdAndUserNo", json.toJSONString());
    }

    /**
     * 根据用id,用户编号修改处理状态
     * @param handleStatus 处理状态
     * @param id id
     * @param userNo 用户编号
     */
    @Override
    public CommonResult updateHandleStatusByIdAndUserNo(final Integer handleStatus, final Long id, final String userNo) {

        JSONObject json = new JSONObject();
        json.put("handleStatus", HandleStatus.getValueMapByKey(handleStatus).name());
        json.put("id", id);
        json.put("userNo", userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                userToyDao.updateHandleStatusByIdAndUserNo(handleStatus, id, userNo);
            }
        }, "updateHandleStatusByIdAndUserNo", json.toJSONString());

    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
