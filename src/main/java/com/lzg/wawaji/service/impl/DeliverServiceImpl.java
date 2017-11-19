package com.lzg.wawaji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.DeliverDao;
import com.lzg.wawaji.entity.Deliver;
import com.lzg.wawaji.service.DeliverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("deliverService")
public class DeliverServiceImpl implements DeliverService {

    private static final Logger logger = LoggerFactory.getLogger(DeliverServiceImpl.class);

    @Autowired
    private DeliverDao deliverDao;
    /**
     * 添加发货记录
     * @param deliver 发货记录
     */
    @Override
    public void addDeliver(Deliver deliver) {
        deliverDao.addDeliver(deliver);
    }

    /**
     * 根据用户编号获得用户发货记录数量
     * @param userNo 用户编号
     * @return
     */
    @Override
    public Integer countDeliverByUserNo(String userNo) {
        try {
            return deliverDao.countDeliverByUserNo(userNo);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("userNo",userNo);
            logger.error("{} countDeliverByUserNo param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
        }
        return null;
    }

    /**
     * 获得所有用户发货数量
     * @return
     */
    @Override
    public Integer countAllDeliver() {
        return deliverDao.countAllDeliver();
    }

    /**
     * 根据用户编号分页获得用户发货记录集合
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @Override
    public List<Deliver> getDeliverByUserNo(String userNo, int startPage) {
        try {
            startPage = startPage * BaseConstant.DEFAULT_PAGE_SIZE;
            return deliverDao.getDeliverByUserNo(userNo, startPage, BaseConstant.DEFAULT_PAGE_SIZE);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("userNo",userNo);
            json.put("startPage",startPage);
            json.put("pageSize",BaseConstant.DEFAULT_PAGE_SIZE);
            logger.error("{} getDeliverByUserNo param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
        }
        return null;
    }

    /**
     * 分页获得所有用户发货记录
     * @param startPage 开始页
     * @return
     */
    @Override
    public List<Deliver> getAllDeliverByPage(int startPage) {
        try {
            return deliverDao.getAllDeliverByPage(startPage, BaseConstant.DEFAULT_PAGE_SIZE);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("startPage",startPage);
            json.put("pageSize",BaseConstant.DEFAULT_PAGE_SIZE);
            logger.error("{} getAllDeliverByPage param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
        }
        return null;
    }

    /**
     * 根据id,用户编号获得用户发货记录
     * @param id id
     * @param userNo 用户编号
     * @return
     */
    @Override
    public Deliver getDeliverByIdAndUserNo(Long id, String userNo) {
        try {
            return deliverDao.getDeliverByIdAndUserNo(id, userNo);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("id",id);
            json.put("userNo",userNo);
            logger.error("{} getDeliverByIdAndUserNo param:{} error "+ e, BaseConstant.LOG_ERR_MSG, json, e);
        }
        return null;
    }

    /**
     * 根据id,用户编号修改货物详情及状态
     * @param deliver 货物详情
     */
    @Override
    public void updateDeliverMsgByIdAndUserNo(Deliver deliver) {
        deliverDao.updateDeliverMsgByIdAndUserNo(deliver);
    }
}
