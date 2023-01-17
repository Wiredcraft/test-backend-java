package com.wiredcraft.user.tiny.modules.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wiredcraft.user.tiny.common.api.ResultCode;
import com.wiredcraft.user.tiny.common.exception.ApiException;
import com.wiredcraft.user.tiny.modules.ums.mapper.FollowerMapper;
import com.wiredcraft.user.tiny.modules.ums.mapper.FriendMapper;
import com.wiredcraft.user.tiny.modules.ums.model.Follower;
import com.wiredcraft.user.tiny.modules.ums.model.Friend;
import com.wiredcraft.user.tiny.modules.ums.service.FollowerService;
import com.wiredcraft.user.tiny.modules.ums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**

 *
 * @author yuao
 * @since 2023-01-14
 */
@Service
public class FollowerServiceImpl extends ServiceImpl<FollowerMapper, Follower> implements FollowerService {


    @Autowired
    private FollowerMapper followerMapper;

    @Autowired
    private FriendMapper friendMapper;

    @Value("${follow.limit:500}")
    private Long followerLimit;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public boolean follow(String followName, String currentUser) {

        if (followerLimit<followerNumber(currentUser)){
             throw new ApiException(ResultCode.FOLLOW_LIMIT);
        }
        if (!userService.exist(followName)){
            throw new ApiException(ResultCode.NO_EXIST);
        }
        if (!haveFollowed(currentUser,followName)){
            throw new ApiException(ResultCode.REPEAT_FOLLOW);
        }

        if (beFollowed(currentUser,followName)){
            friendMapper.insert(Friend.builder().followerId(followName)
                    .userId(currentUser).build());
            friendMapper.insert(Friend.builder().followerId(currentUser)
                    .userId(followName).build());
        }


        return followerMapper.insert(Follower.builder().followerId(followName)
                .userId(currentUser).build())>0;
    }

    @Transactional
    public void cancelFollow(String followName,String currentUser){
        deleteFollowRelationShip(followName, currentUser);
        deleteFriendRelationShip(followName, currentUser);
        deleteFriendRelationShip(currentUser, followName);
    }

    private boolean deleteFollowRelationShip(String followName,String currentUser){
        QueryWrapper<Follower> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Follower::getFollowerId, currentUser)
                .eq(Follower::getUserId, followName);
        return    remove(wrapper);
    }

    private boolean deleteFriendRelationShip(String followName,String currentUser){
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Friend::getFollowerId, currentUser)
                .eq(Friend::getUserId, followName);
        return    friendMapper.delete(wrapper)>0;
    }

    private Long followerNumber(String currentUser){
        QueryWrapper<Follower> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Follower::getUserId, currentUser);

        return count(wrapper);
    }

    private Boolean beFollowed(String currentUser, String followName){
        QueryWrapper<Follower> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Follower::getFollowerId, currentUser)
                .eq(Follower::getUserId, followName);
        return count(wrapper)> 0;
    }

    private Boolean haveFollowed(String currentUser, String followName){
        QueryWrapper<Follower> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Follower::getUserId, currentUser)
                .eq(Follower::getFollowerId, followName);
        return count(wrapper)> 0;
    }



    @Override
    public List<String> getUserFollow(String currentUser) {
        QueryWrapper<Follower> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Follower::getUserId, currentUser);
        List<Follower> list = list(wrapper);
        return list.stream().map(e -> e.getFollowerId()).collect(Collectors.toList());

    }





    public List<String> getUserFriends(String currentUser) {

        return new ArrayList<>();


    }

}
