package com.craig.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.craig.user.entity.User;
import com.craig.user.entity.UserFollower;
import com.craig.user.entity.dto.NearbyUserDto;
import com.craig.user.entity.dto.SimpleFollowerDto;
import com.craig.user.entity.dto.SimpleFollowingDto;
import com.craig.user.model.FollowerModel;
import com.craig.user.model.PageResult;
import com.craig.user.model.SimpleUserModel;
import com.craig.user.model.UserDetailModel;
import com.craig.user.model.UserModel;
import com.craig.user.model.UserQueryModel;
import com.craig.user.repository.UserCoordinateRepository;
import com.craig.user.repository.UserFollowerRepository;
import com.craig.user.repository.UserRepository;
import com.craig.user.service.UserService;
import com.craig.user.util.PasswordUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowerRepository userFollowerRepository;

    @Autowired
    private UserCoordinateRepository userCoordinateRepository;

    private final static int TOP_USER_LIST_COUNT = 10;

    @Override
    public UserDetailModel getUserDetail(Long userId, Boolean getFollower, Boolean getFollowing) {
        User user = userRepository.getUser(userId);
        if (user == null) {
            log.warn("get null user, userId:", userId);
            return null;
        }

        return convertToUserDetailModel(user, getFollower, getFollowing);
    }

    private UserDetailModel convertToUserDetailModel(User user, Boolean getFollower, Boolean getFollowing) {
        UserDetailModel detailModel = new UserDetailModel();
        BeanUtils.copyProperties(user, detailModel);

        if (getFollower != null && getFollower) {
            List<SimpleUserModel> followerModels = getFollowers(user.getId());
            detailModel.setFollowers(followerModels);
        }

        if (getFollowing != null && getFollowing) {
            List<SimpleUserModel> followingModels = getFollowings(user.getId());
            detailModel.setFollowing(followingModels);
        }

        return detailModel;
    }

    @Override
    public UserDetailModel getUserByName(String userName, Boolean getFollower, Boolean getFollowing) {
        User user = userRepository.getByName(userName);
        if (user == null) {
            log.warn("get null user, uesrName:", userName);
            return null;
        }

        return convertToUserDetailModel(user, getFollower, getFollowing);
    }

    @Override
    public List<SimpleUserModel> getFollowings(Long userId) {
        List<SimpleFollowingDto> followingDto = userFollowerRepository.getFollowings(userId);
        if (CollectionUtils.isEmpty(followingDto)) {
            return null;
        }
        List<SimpleUserModel> followingModels = followingDto.stream().map(c -> {
            SimpleUserModel followerModel = new SimpleUserModel();
            followerModel.setUserId(c.getFollowingId());
            followerModel.setUserName(c.getFollowingName());
            return followerModel;
        }).collect(Collectors.toList());
        return followingModels;
    }

    @Override
    public List<SimpleUserModel> getFollowers(Long userId) {
        List<SimpleFollowerDto> followerDto = userFollowerRepository.getFollowers(userId);
        if (CollectionUtils.isEmpty(followerDto)) {
            return null;
        }
        List<SimpleUserModel> followerModels = followerDto.stream().map(c -> {
            SimpleUserModel followerModel = new SimpleUserModel();
            followerModel.setUserId(c.getFollowerId());
            followerModel.setUserName(c.getFollowerName());
            return followerModel;
        }).collect(Collectors.toList());
        return followerModels;
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

        User existUser = userRepository.getByName(insertModel.getName());
        if (existUser != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        User newUser = new User();
        newUser.setAddress(insertModel.getAddress());
        newUser.setDescription(insertModel.getDescription());
        newUser.setDob(insertModel.getDob());
        newUser.setName(insertModel.getName());
        try {
            newUser.setPassword(PasswordUtil.getEncryptedPwd(insertModel.getPassword()));
        } catch (Exception e) {
            log.error("build password failed", e);
            throw new RuntimeException(e);
        }

        User addedUser = userRepository.addUser(newUser);

        if (insertModel.getAddressLat() != null && insertModel.getAddressLng() != null) {
            userCoordinateRepository.addCoord(addedUser.getId(), insertModel.getAddressLng(),
                    insertModel.getAddressLat());
        }

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

        if (updateModel.getAddressLng() != null && updateModel.getAddressLat() != null) {
            userCoordinateRepository.addCoordOrUpdate(updateModel.getId(), updateModel.getAddressLng(),
                    updateModel.getAddressLat());
        }
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

    @Override
    public FollowerModel addFollowers(Long userId, Long followerId) {
        User user = userRepository.getUser(userId);
        if (user == null) {
            return null;
        }

        UserFollower existRecord = userFollowerRepository.getRecord(userId, followerId);
        if (existRecord != null) {
            // deal with existRecord, for idempotent
            FollowerModel model = new FollowerModel();
            BeanUtils.copyProperties(existRecord, model);
            return model;
        }

        UserFollower userFollower = userFollowerRepository.addUserFollower(userId, followerId);

        FollowerModel model = new FollowerModel();
        BeanUtils.copyProperties(userFollower, model);
        return model;
    }

    @Override
    public Boolean deleteFollower(Long userId, Long follwerId) {
        UserFollower record = userFollowerRepository.getRecord(userId, follwerId);
        if (record == null) {
            return false;
        }
        userFollowerRepository.delete(record);
        return true;
    }

    @Override
    public List<SimpleUserModel> getNearbyUsers(Long userId) {
        List<NearbyUserDto> coordinates = userCoordinateRepository.getNearbyUsers(userId, TOP_USER_LIST_COUNT);
        if (CollectionUtils.isEmpty(coordinates)) {
            return null;
        }

        return coordinates.stream().map(c -> {
            SimpleUserModel user = new SimpleUserModel();
            user.setUserId(c.getUserId());
            user.setUserName(c.getUserName());
            return user;
        }).collect(Collectors.toList());
    }
}
