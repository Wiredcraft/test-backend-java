package com.coffee.user.dao;

import com.coffee.user.domain.po.UserGeohashPO;

import java.util.List;

public interface UserGeohashDao {
    boolean create(UserGeohashPO userGeohashPO);

    boolean update(UserGeohashPO userGeohashPO);

    UserGeohashPO findByUserId(String userId);

    List<UserGeohashPO> findByGeoCode(String geoCode);
}
