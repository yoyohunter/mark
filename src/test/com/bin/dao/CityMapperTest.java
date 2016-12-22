package com.bin.dao;

import com.bin.entity.City;
import com.bin.entity.CouponDO;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangbin on 16/10/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class CityMapperTest extends TestCase {
    @Resource
    private CityMapper cityMapper;

    @Resource
    private CouponMapper couponMapper;

    @Test
    public void testQueryAll() throws Exception {
        List<City> cityList=cityMapper.queryAll();
        System.out.println(cityList);
    }

    @Test
    public void testQueryCoupon() throws Exception {
        CouponDO couponDO = new CouponDO();
        couponDO.setStatus(1);
        couponDO.setCouponType(1);
        List<CouponDO> list=couponMapper.queryCouponMap(couponDO);
        System.out.println(list);
    }

}