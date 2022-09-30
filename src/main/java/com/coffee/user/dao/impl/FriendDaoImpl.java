package com.coffee.user.dao.impl;

import com.coffee.user.dao.FriendDao;
import com.coffee.user.dao.mapper.FriendMapper;
import com.coffee.user.domain.po.FriendPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FriendDaoImpl implements FriendDao {
    @Autowired
    private FriendMapper friendMapper;

    @Override
    public boolean create(FriendPO friend) {
        return friendMapper.create(friend) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean delete(String id) {
        return friendMapper.delete(id) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public FriendPO findById(String id) {
        return friendMapper.findById(id);
    }

    @Override
    public List<FriendPO> findByUserId(String userId) {
        return friendMapper.findByUserId(userId);
    }
}
