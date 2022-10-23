package com.craig.user.service;

import java.util.List;

import com.craig.user.model.FollowerModel;
import com.craig.user.model.PageResult;
import com.craig.user.model.SimpleUserModel;
import com.craig.user.model.UserDetailModel;
import com.craig.user.model.UserModel;
import com.craig.user.model.UserQueryModel;

public interface UserService {
    /**
     * get user detail mode
     * 
     * @param userId      user id
     * @param getFollower is get follower, true get follower, false doesn't get
     *                    follower
     * @return user detail model
     */
    UserDetailModel getUserDetail(Long userId, Boolean getFollower, Boolean getFollowing);

    /**
     * get user's following list
     * 
     * @param userId
     * @return following's id and name
     */
    List<SimpleUserModel> getFollowings(Long userId);

    /**
     * get user's follower list
     * 
     * @param userId
     * @return follower's id and name
     */
    List<SimpleUserModel> getFollowers(Long userId);

    /**
     * get user list
     * 
     * @param query query param
     * @return list of user models
     */
    PageResult<UserModel> getUsers(UserQueryModel query);

    /**
     * insert user
     * 
     * @param insertModel model to insert
     * @return inserted user model
     */
    UserModel createUser(UserModel insertModel);

    /**
     * upodate user
     * 
     * @param updatModel the update content
     * @return updated user model
     */
    UserModel updateUser(UserModel updatModel);

    /**
     * delete user
     * 
     * @param userId user id
     */
    Boolean deleteUser(Long userId);

    /**
     * add follwer to user's follwer list
     * @param userId user who is following
     * @param followerId the user who follower
     * @return
     */
    FollowerModel addFollowers(Long userId, Long followerId);

    /**
     * delete the relation between user and follower
     * @param userId
     * @param follwerId
     * @return
     */
    Boolean deleteFollower(Long userId, Long follwerId);
}
