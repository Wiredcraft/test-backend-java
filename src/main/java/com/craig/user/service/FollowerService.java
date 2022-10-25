package com.craig.user.service;

import java.util.List;

import com.craig.user.model.FollowerModel;
import com.craig.user.model.SimpleUserModel;

public interface FollowerService {
    
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

    /**
     * get user's follower list
     * 
     * @param userId
     * @return follower's id and name
     */
    List<SimpleUserModel> getFollowers(Long userId);

    /**
     * get user's following list
     * 
     * @param userId
     * @return following's id and name
     */
    List<SimpleUserModel> getFollowings(Long userId);

}
