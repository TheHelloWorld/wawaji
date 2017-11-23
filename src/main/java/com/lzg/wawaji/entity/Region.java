package com.lzg.wawaji.entity;

/**
 * 地区表
 */
public class Region {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 地区编码
     */
    private String code;

    /**
     * 名称
     */
    private String regionName;

    /**
     * 父级地区编码
     */
    private String parentCode;

    /**
     * 英文全名
     */
    private String fullNameEn;

    /**
     * 英文简写
     */
    private String shortNameEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getFullNameEn() {
        return fullNameEn;
    }

    public void setFullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn;
    }

    public String getShortNameEn() {
        return shortNameEn;
    }

    public void setShortNameEn(String shortNameEn) {
        this.shortNameEn = shortNameEn;
    }
}
