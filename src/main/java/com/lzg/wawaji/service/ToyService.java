package com.lzg.wawaji.service;

import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.entity.Toy;

import java.util.List;

public interface ToyService {

    /**
     * 添加娃娃记录
     * @param toy 娃娃记录
     */
    CommonResult addToy(Toy toy);

    /**
     * 获得所有娃娃记录的数量
     * @return
     */
    CommonResult<Integer> countAllToy();

    /**
     * 分页获得所有娃娃记录
     * @param startPage 开始页
     * @return
     */
    CommonResult<List<Toy>> getAllToyByPage(int startPage);

    /**
     * 根据id,娃娃编号获得娃娃及信息
     * @param id id
     * @param toyNo 玩具编号
     * @return
     */
    CommonResult<Toy> getToyByIdAndToyNo(Long id, String toyNo);

    /**
     * 根据id,娃娃机编号修改娃娃机记录
     * @param toy 娃娃机Bean
     */
    CommonResult updateToyByIdAndToyNo(Toy toy);

    /**
     * 根据id,玩具编号删除娃娃记录
     * @param id id
     * @param toyNo 玩具编号
     */
    CommonResult deleteToyByIdAndToyNo(Long id, String toyNo);
}
