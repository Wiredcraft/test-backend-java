package com.craig.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.craig.user.entity.User;
import com.craig.user.entity.dto.SimpleUserFollowerDto;
import com.craig.user.model.PageResult;
import com.craig.user.model.UserDetailModel;
import com.craig.user.model.UserFollowerModel;
import com.craig.user.model.UserModel;
import com.craig.user.model.UserQueryModel;
import com.craig.user.repository.UserFollowingRepository;
import com.craig.user.repository.UserRepository;
import com.craig.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowingRepository userFollowingRepository;

    @Override
    public UserDetailModel getUserDetail(Long userId, Boolean getFollower) {
        User user = userRepository.getUser(userId);
        if (user == null) {
            log.warn("get null user, userId:", userId);
            return null;
        }

        UserDetailModel detailModel = new UserDetailModel();
        BeanUtils.copyProperties(user, detailModel);

        if (getFollower != null && getFollower) {
            List<SimpleUserFollowerDto> followerDto = userFollowingRepository.getFollowers(userId);
            detailModel.setFollowers(followerDto.stream().map(c -> {
                UserFollowerModel followerModel = new UserFollowerModel();
                BeanUtils.copyProperties(c, followerModel);
                return followerModel;
            }).collect(Collectors.toList()));
        }

        return detailModel;
    }

    @Override
    public PageResult<UserModel> getUsers(UserQueryModel query) {
        Page<User> userPage = userRepository.getUsers(query);
        List<UserModel> userModels = userPage.convert(c -> {
            UserModel model = new UserModel();
            BeanUtils.copyProperties(c, model);
            return model;
        }).getRecords();

        PageResult<UserModel> result = new PageResult<UserModel>();
        result.setData(userModels);
        result.setCurrent(userPage.getCurrent());
        result.setSize(userPage.getSize());
        result.setTotal(userPage.getTotal());

        return result;
    }

    @Override
    public UserModel createUser(UserModel insertModel) {
        User newUser = new User();
        // todo:any duplicate check logic?
        newUser.setAddress(insertModel.getAddress());
        newUser.setDescription(insertModel.getDescription());
        newUser.setDob(insertModel.getDob());
        newUser.setName(insertModel.getName());
        newUser.setCreatedAt(new Date());

        userRepository.save(newUser);

        insertModel.setId(newUser.getId());
        insertModel.setCreatedAt(newUser.getCreatedAt());

        return insertModel;
    }

    @Override
    public UserModel updateUser(UserModel updateModel) {
        User user = userRepository.getUser(updateModel.getId());
        if (user == null) {
            log.warn("update null user, userId:", updateModel.getId());
            return null;
        }

        user.setAddress(updateModel.getAddress());
        user.setDescription(updateModel.getDescription());
        user.setDob(updateModel.getDob());
        user.setName(updateModel.getName());

        userRepository.update(user);
        return updateModel;
    }

    @Override
    public Boolean deleteUser(Long userId) {
        User user = userRepository.getUser(userId);
        if (user == null) {
            log.warn("delete not exist user, userId:", userId);
            return false;
        }
        userRepository.deleteUser(user);
        return true;
    }

}
