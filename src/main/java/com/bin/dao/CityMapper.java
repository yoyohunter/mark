package com.bin.dao;

import com.bin.entity.City;

import java.util.List;

/**
 * Created by zhangbin on 16/10/14.
 */
public interface CityMapper {
    /**
     * 查询地级市
     *
     * @return
     */
    List<City> queryAll();
}
