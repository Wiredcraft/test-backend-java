package com.wiredcraft.testbackend.service;

import com.wiredcraft.testbackend.dao.FanRepository;
import com.wiredcraft.testbackend.dao.FollowRepository;
import com.wiredcraft.testbackend.entity.Fan;
import com.wiredcraft.testbackend.entity.Follow;
import com.wiredcraft.testbackend.entity.PageResult;
import com.wiredcraft.testbackend.entity.Result;
import com.wiredcraft.testbackend.entity.User;
import com.wiredcraft.testbackend.entity.param.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements FollowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FollowServiceImpl.class);

    @Autowired
    private FanRepository fanRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CacheService cacheService;

    @Override
    public Result<PageResult<User>> getFanById(Long userId, PageParam pageParam) {
        PageResult<Long> cacheResult = cacheService.getFanUserIdFromCache(userId, pageParam);
        // if fan list exist in cache
        if (cacheResult != null && cacheResult.getTotal() > 0) {
            List<User> userList = userService.getUserByIds(cacheResult.getData());
            PageResult<User> result = new PageResult();
            BeanUtils.copyProperties(cacheResult, result, "data");
            result.setData(userList);
            return Result.success(result);
        }

        Fan fan = new Fan();
        fan.setUserId(userId);
        Example<Fan> example = Example.of(fan);
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getPageSize());
        Page<Fan> page = fanRepository.findAll(example, pageable);

        List<User> list = new ArrayList<>();
        if (page != null && !CollectionUtils.isEmpty(page.getContent())) {
            List<Long> ids = page.getContent().stream().map(e -> e.getFanUserId()).collect(Collectors.toList());
            List<User> userList = userService.getUserByIds(ids);
            if (!CollectionUtils.isEmpty(userList)) {
                list.addAll(userList);
            }
            // add cache
            cacheService.addFanToCache(userId, ids.toArray(new Long[ids.size()]));
        }

        PageResult<User> pageResult = new PageResult();
        pageResult.setTotal(page.getTotalElements());
        pageResult.setPageSize(page.getSize());
        pageResult.setCurPage(page.getNumber());
        pageResult.setTotalPage(page.getTotalPages());
        pageResult.setData(list);
        return Result.success(pageResult);
    }

    @Override
    public Result<PageResult<User>> getFollowById(Long userId, PageParam pageParam) {
        PageResult<Long> cacheResult = cacheService.getFollowUserIdFromCache(userId, pageParam);
        // if fan list exist in cache
        if (cacheResult != null && cacheResult.getTotal() > 0) {
            List<User> userList = userService.getUserByIds(cacheResult.getData());
            PageResult<User> result = new PageResult();
            BeanUtils.copyProperties(cacheResult, result, "data");
            result.setData(userList);
            return Result.success(result);
        }

        Follow following = new Follow();
        following.setUserId(userId);
        Example<Follow> example = Example.of(following);
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getPageSize());
        Page<Follow> page = followRepository.findAll(example, pageable);

        List<User> list = new ArrayList<>();
        if (page != null && !CollectionUtils.isEmpty(page.getContent())) {
            List<Long> ids = page.getContent().stream().map(e -> e.getFollowUserId()).collect(Collectors.toList());
            List<User> userList = userService.getUserByIds(ids);
            if (!CollectionUtils.isEmpty(userList)) {
                list.addAll(userList);
            }
            // add cache
            cacheService.addFollowToCache(userId, ids.toArray(new Long[ids.size()]));
        }
        PageResult<User> pageResult = new PageResult();
        pageResult.setTotal(page.getTotalElements());
        pageResult.setPageSize(page.getSize());
        pageResult.setCurPage(page.getNumber());
        pageResult.setTotalPage(page.getTotalPages());
        pageResult.setData(list);
        return Result.success(pageResult);
    }

    @Transactional
    @Override
    public Result<Boolean> follow(Long originalUserId, Long targetUserId) {
        LOGGER.info("follow, originalUserId={}, targetUserId={}", originalUserId, targetUserId);
        Follow following = new Follow();
        following.setUserId(originalUserId);
        following.setFollowUserId(targetUserId);
        following.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        following = followRepository.save(following);
        if (following.getId() != null) {
            Fan fan = new Fan();
            fan.setUserId(targetUserId);
            fan.setFanUserId(originalUserId);
            fan.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            fanRepository.save(fan);

            // add cache
            cacheService.addFollowToCache(originalUserId, targetUserId);
            cacheService.addFanToCache(targetUserId, originalUserId);
        }
        return Result.success(Boolean.TRUE);
    }

    @Transactional
    @Override
    public Result<Boolean> unfollow(Long originalUserId, Long targetUserId) {
        LOGGER.info("unfollow, originalUserId={}, targetUserId={}", originalUserId, targetUserId);
        int count = followRepository.deleteByUserIdAndFollowUserId(originalUserId, targetUserId);
        if (count > 0) {
            fanRepository.deleteByUserIdAndFanUserId(targetUserId, originalUserId);

            // remove from cache
            cacheService.removeFollowFromCache(originalUserId, targetUserId);
            cacheService.removeFanFromCache(targetUserId, originalUserId);
        }
        return Result.success(Boolean.TRUE);
    }

}
