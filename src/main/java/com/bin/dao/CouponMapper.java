package com.bin.dao;


import com.bin.entity.CouponDO;

import java.util.List;

/**
 * Created by fengruxiao on 15/9/24.
 */
public interface CouponMapper {

    List<CouponDO> queryCouponMap(CouponDO couponQuery);
}
