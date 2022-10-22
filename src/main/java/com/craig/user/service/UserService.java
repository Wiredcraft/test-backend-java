package com.craig.user.service;

import java.util.List;

import com.craig.user.model.PageResult;
import com.craig.user.model.UserDetailModel;
import com.craig.user.model.UserModel;
import com.craig.user.model.UserQueryModel;

public interface UserService {
    /**
     * get user detail mode
     * @param userId user id
     * @param getFollower is get follower, true get follower, false doesn't get follower
     * @return user detail model
     */
    UserDetailModel getUserDetail(Long userId, Boolean getFollower);

    /**
     * get user list
     * @param query query param
     * @return list of user models
     */
    PageResult<UserModel> getUsers(UserQueryModel query);

    /**
     * insert user
     * @param insertModel model to insert
     * @return inserted user model
     */
    UserModel createUser(UserModel insertModel);

    /**
     * upodate user
     * @param updatModel the update content
     * @return updated user model
     */
    UserModel updateUser(UserModel updatModel);

    /**
     * delete user
     * @param userId user id
     */
    Boolean deleteUser(Long userId);
}
