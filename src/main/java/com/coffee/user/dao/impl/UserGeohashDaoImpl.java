package com.coffee.user.dao.impl;

import com.coffee.user.dao.UserGeohashDao;
import com.coffee.user.dao.mapper.UserGeohashMapper;
import com.coffee.user.domain.po.UserGeohashPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserGeohashDaoImpl implements UserGeohashDao {
    @Autowired
    private UserGeohashMapper userGeohashMapper;

    @Override
    public boolean create(UserGeohashPO userGeohashPO) {
        return userGeohashMapper.create(userGeohashPO) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean update(UserGeohashPO userGeohashPO) {
        return userGeohashMapper.update(userGeohashPO) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public UserGeohashPO findByUserId(String userId) {
        return userGeohashMapper.findByUserId(userId);
    }

    @Override
    public List<UserGeohashPO> findByGeoCode(String geoCode) {
        return userGeohashMapper.findByGeoCode(geoCode);
    }
}
