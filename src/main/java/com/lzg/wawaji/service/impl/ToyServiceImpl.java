package com.lzg.wawaji.service.impl;

import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.ToyDao;
import com.lzg.wawaji.entity.Toy;
import com.lzg.wawaji.enums.CurrentState;
import com.lzg.wawaji.service.ToyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("toyService")
public class ToyServiceImpl implements ToyService {

    private static final Logger logger = LoggerFactory.getLogger(ToyServiceImpl.class);

    @Autowired
    private ToyDao toyDao;

    /**
     * 添加娃娃记录
     * @param toy 娃娃记录
     */
    @Override
    public void addToy(Toy toy) {

        toy.setCurrentState(CurrentState.AVAILABLE.getStatus());
        toyDao.addToy(toy);
    }

    /**
     * 获得所有娃娃记录数量
     * @return
     */
    @Override
    public Integer countAllToy() {
        try {
            return toyDao.countAllToy();
        } catch (Exception e) {
            logger.error("{} countAllToy error "+e, BaseConstant.LOG_ERR_MSG, e);
        }
        return null;

    }

    /**
     * 分页获得所有娃娃记录
     * @param startPage 开始页
     * @return
     */
    @Override
    public List<Toy> getAllToyByPage(int startPage) {
        try {
            return toyDao.getAllToyByPage(startPage, BaseConstant.DEFAULT_PAGE_SIZE);
        } catch (Exception e) {
            logger.error("{} getAllToyByPage error "+e, BaseConstant.LOG_ERR_MSG, e);
        }
        return null;
    }

    /**
     * 根据id,娃娃编号获得娃娃及信息
     * @param id id
     * @param toyNo 玩具编号
     * @return
     */
    @Override
    public Toy getToyByIdAndToyNo(Long id, String toyNo) {
        try {
            return toyDao.getToyByIdAndToyNo(id, toyNo);
        } catch (Exception e) {
            logger.error("{} getToyByIdAndToyNo param:{} error "+e, BaseConstant.LOG_ERR_MSG, id+","+toyNo, e);
        }
        return null;
    }

    /**
     * 根据id,娃娃机编号修改娃娃机记录
     * @param toy 娃娃机Bean
     */
    @Override
    public void updateToyByIdAndToyNo(Toy toy) {
        toyDao.updateToyByIdAndToyNo(toy);

    }

    /**
     * 根据id,玩具编号删除娃娃记录
     * @param id id
     * @param toyNo 玩具编号
     */
    @Override
    public void deleteToyByIdAndToyNo(Long id, String toyNo) {
        toyDao.deleteToyByIdAndToyNo(id, toyNo);
    }

}
