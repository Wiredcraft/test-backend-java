package me.solution.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import me.solution.model.domain.UserDeleteFlag;
import me.solution.mapper.UserMapper;
import me.solution.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * user service to access user
 *
 * @author davincix
 * @since 2023/5/20 19:28
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUserByName(String username, boolean includeDeleted) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName, username);
        if (!includeDeleted) {
            wrapper.eq(User::getDeleteFlag, UserDeleteFlag.NORMAL.getValue());
        }
        return userMapper.selectOne(wrapper);
    }

    public User getUserById(Long id, boolean includeDeleted) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, id);
        if (!includeDeleted) {
            wrapper.eq(User::getDeleteFlag, UserDeleteFlag.NORMAL.getValue());
        }
        return userMapper.selectOne(wrapper);
    }

    public void saveUser(User saver) {
        Optional.ofNullable(saver)
                .ifPresent(userMapper::insert);
    }

    public void softDelById(Long userId) {
        User user = getUserById(userId, false);
        if (user == null) {
            return;
        }

        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId, userId);
        wrapper.set(User::getDeleteFlag, UserDeleteFlag.DELETED.getValue());
        // the username is collected
        wrapper.set(User::getName, user.getName() + "_" + userId);

        userMapper.update(null, wrapper);
    }

    public void updateByUserId(User user) {
        Optional.ofNullable(user.getId())
                .ifPresent(x -> {
                    LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
                    wrapper.eq(User::getId, x);

                    Optional.ofNullable(user.getPasswd())
                            .ifPresent(y -> wrapper.set(User::getPasswd, y));
                    Optional.ofNullable(user.getDob())
                            .ifPresent(y -> wrapper.set(User::getDob, y));
                    Optional.ofNullable(user.getAddress())
                            .ifPresent(y -> wrapper.set(User::getAddress, y));
                    Optional.ofNullable(user.getDescription())
                            .ifPresent(y -> wrapper.set(User::getDescription, y));

                    userMapper.update(null, wrapper);

                });
    }

    public List<User> listByIds(Set<Long> followingIds, boolean includeDeleted) {
        if (CollectionUtils.isEmpty(followingIds)) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(User::getId, followingIds);
        if (!includeDeleted) {
            wrapper.eq(User::getDeleteFlag, UserDeleteFlag.NORMAL.getValue());
        }

        return userMapper.selectList(wrapper);
    }
}
