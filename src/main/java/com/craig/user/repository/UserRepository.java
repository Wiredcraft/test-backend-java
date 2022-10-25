package com.craig.user.repository;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.craig.user.entity.User;
import com.craig.user.mapper.UserMapper;
import com.craig.user.model.UserQueryModel;

@Repository
public class UserRepository extends ServiceImpl<UserMapper, User> {
    /**
     * get specific user
     * 
     * @param userId
     * @return
     */
    public User getUser(Long userId) {
        Wrapper<User> query = new QueryWrapper<User>().lambda()
                .eq(User::getId, userId)
                .eq(User::getDeleted, false);
        return this.baseMapper.selectOne(query);
    }

    /**
     * using query object search the user (inclue pagination)
     * 
     * @param query
     * @return
     */
    public Page<User> getUsers(UserQueryModel query) {
        Wrapper<User> queryWrapper = new QueryWrapper<User>().lambda()
                .eq(User::getDeleted, false);
        Page<User> page = new Page<>(query.getCurrent(), query.getSize());
        page.setSearchCount(true);
        return this.baseMapper.selectPage(page, queryWrapper);
    }

    public User addUser(User user) {
        this.save(user);
        return this.getById(user.getId());
    }

    /**
     * update user, only 4 fields can be updated
     * 
     * @param user
     */
    public void update(User user) {
        LambdaUpdateWrapper<User> updateWrapper = new UpdateWrapper<User>().lambda();
        updateWrapper.eq(User::getId, user.getId())
                .set(User::getName, user.getName())
                .set(User::getAddress, user.getAddress())
                .set(User::getDob, user.getDob())
                .set(User::getDescription, user.getDescription());
        this.baseMapper.update(null, updateWrapper);
    }

    /**
     * delete user, set logic delete flag to true
     * 
     * @param user
     */
    public void deleteUser(User user) {
        LambdaUpdateWrapper<User> updateWrapper = new UpdateWrapper<User>().lambda();
        updateWrapper.eq(User::getId, user.getId())
                .set(User::getDeleted, true);
        this.baseMapper.update(null, updateWrapper);
    }

    /**
     * get user by name
     * @param userName
     * @return
     */
    public User getByName(String userName) {
        Wrapper<User> query = new QueryWrapper<User>().lambda()
                .eq(User::getName, userName)
                .eq(User::getDeleted, false);
        return this.baseMapper.selectOne(query);
    }
}
