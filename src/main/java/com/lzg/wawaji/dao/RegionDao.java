package com.lzg.wawaji.dao;

import com.lzg.wawaji.entity.Region;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("regionDao")
public interface RegionDao {

    /**
     * 根据父级地区编码获得子地区
     * @param parentCode 父级地区编码
     * @return
     */
    List<Region> getRegionByParentCode(String parentCode);

    /**
     * 根据父级地区编码获得子地区数量
     * @param parentCode 父级地区编码
     * @return
     */
    Integer countRegionByParentCode(String parentCode);
}
