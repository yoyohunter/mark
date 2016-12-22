package com.bin.entity;

/**
 * Created by zhangbin on 16/10/14.
 */
public class City {
    /**
     * 唯一id
     */
    private Integer id;
    /**
     * 地区编号
     */
    private String areaCode;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 地区编号父节点
     */
    private String parentAreaCode;
    /**
     * 市级区号
     */
    private String code;

    public Integer getId() {
        return id;
    }

    public City setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public City setAreaCode(String areaCode) {
        this.areaCode = areaCode;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public City setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getParentAreaCode() {
        return parentAreaCode;
    }

    public City setParentAreaCode(String parentAreaCode) {
        this.parentAreaCode = parentAreaCode;
        return this;
    }

    public String getCode() {
        return code;
    }

    public City setCode(String code) {
        this.code = code;
        return this;
    }

    public City() {
    }

    public City(Integer id, String areaCode, String cityName, String parentAreaCode, String code) {
        this.id = id;
        this.areaCode = areaCode;
        this.cityName = cityName;
        this.parentAreaCode = parentAreaCode;
        this.code = code;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", areaCode='" + areaCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", parentAreaCode='" + parentAreaCode + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
