package com.wiredcraft.user.tiny.modules.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiredcraft.user.tiny.modules.ums.model.Friend;
import com.wiredcraft.user.tiny.modules.ums.mapper.FriendMapper;
import com.wiredcraft.user.tiny.modules.ums.model.MongoGeoUser;
import com.wiredcraft.user.tiny.modules.ums.service.FriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author yuao
 * @since 2023-01-14
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<Friend> list(String currentUSer, Integer pageSize, Integer pageNum) {
        Page<Friend> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Friend::getUserId,currentUSer);
        return page(page,wrapper);
    }

    @Override
    public List<String> nearByFriends(String currentUSer, Double latitude, Double longitude) {
        List<String> friendOfCurrentUser = getFriendOfCurrentUser(currentUSer);
        Query query = new Query(

                Criteria.where("").andOperator(
                        Criteria.where("location")
                                .nearSphere(new GeoJsonPoint(longitude, latitude))
                                .maxDistance(5000),
                        Criteria.where("name").in(friendOfCurrentUser)
                ));
        List<MongoGeoUser> venues = mongoTemplate.find(query, MongoGeoUser.class);

        return venues.stream().map(MongoGeoUser::getName).collect(Collectors.toList());
    }

    public List<String> getFriendOfCurrentUser(String currentUSer){
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Friend::getUserId,currentUSer);
        return list(wrapper).stream().map(Friend::getFollowerId).collect(Collectors.toList());
    }
}
