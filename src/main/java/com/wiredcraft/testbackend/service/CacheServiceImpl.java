package com.wiredcraft.testbackend.service;

import com.alibaba.fastjson.JSON;
import com.wiredcraft.testbackend.entity.PageResult;
import com.wiredcraft.testbackend.entity.User;
import com.wiredcraft.testbackend.entity.param.PageParam;
import com.wiredcraft.testbackend.util.CacheKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public User getUserByIdFromCache(Long userId) {
        try {
            String key = CacheKeyUtil.getUserInfoKey(userId);
            String value = (String) redisTemplate.opsForValue().get(key);
            if (value != null && value.length() > 0) {
                return JSON.parseObject(value, User.class);
            }
        } catch (Exception e) {
            LOGGER.error("getUserByIdFromCache catch exception, param={}", userId, e);
        }
        return null;
    }

    @Override
    public void addUserToCache(User user) {
        try {
            String key = CacheKeyUtil.getUserInfoKey(user.getId());
            redisTemplate.opsForValue().set(key, JSON.toJSONString(user));
        } catch (Exception e) {
            LOGGER.error("addUserToCache catch exception, param={}", JSON.toJSONString(user), e);
        }
    }

    @Override
    public User getUserByNameFromCache(String userName) {
        try {
            String key = CacheKeyUtil.getUserNameKey(userName);
            String value = (String) redisTemplate.opsForValue().get(key);
            if (value != null && value.length() > 0) {
                return JSON.parseObject(value, User.class);
            }
        } catch (Exception e) {
            LOGGER.error("getUserByNameFromCache catch exception, userName={}", userName, e);
        }
        return null;
    }

    @Override
    public void addUserNameToCache(User user) {
        try {
            String key = CacheKeyUtil.getUserNameKey(user.getName());
            redisTemplate.opsForValue().set(key, JSON.toJSONString(user), 30, TimeUnit.MINUTES);
        } catch (Exception e) {
            LOGGER.error("addUserNameToCache catch exception, param={}", JSON.toJSONString(user), e);
        }
    }

    @Override
    public void removeUserNameCache(String userName) {
        try {
            String key = CacheKeyUtil.getUserNameKey(userName);
            redisTemplate.delete(key);
        } catch (Exception e) {
            LOGGER.error("removeUserNameCache catch exception, userName={}", userName, e);
        }
    }

    @Override
    public void removeUserCache(Long userId) {
        try {
            String key = CacheKeyUtil.getUserInfoKey(userId);
            redisTemplate.delete(key);
        } catch (Exception e) {
            LOGGER.error("removeUserCache catch exception, param={}", userId, e);
        }
    }

    @Override
    public void addFanToCache(Long userId, Long... fanUserId) {
        try {
            String key = CacheKeyUtil.getUserFanIdsKey(userId);
            Double score = (double) System.currentTimeMillis();
            Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
            for (Long id : fanUserId) {
                tuples.add(ZSetOperations.TypedTuple.of(id.toString(), score));
            }
            redisTemplate.opsForZSet().add(key, tuples);
        } catch (Exception e) {
            LOGGER.error("addFanToCache catch exception, userId={}, fanUserId={}", userId, fanUserId, e);
        }
    }

    @Override
    public void addFollowToCache(Long userId, Long... followUserId) {
        try {
            String key = CacheKeyUtil.getUserFollowIdsKey(userId);
            Double score = (double) System.currentTimeMillis();
            Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
            for (Long id : followUserId) {
                tuples.add(ZSetOperations.TypedTuple.of(id.toString(), score));
            }
            redisTemplate.opsForZSet().add(key, tuples);
        } catch (Exception e) {
            LOGGER.error("addFollowToCache catch exception, userId={}, followUserId={}", userId, followUserId, e);
        }
    }

    @Override
    public void removeFanFromCache(Long userId, Long fanUserId) {
        try {
            String key = CacheKeyUtil.getUserFanIdsKey(userId);
            redisTemplate.opsForZSet().remove(key, fanUserId.toString());
        } catch (Exception e) {
            LOGGER.error("removeFanFromCache catch exception, userId={}, fanUserId={}", userId, fanUserId, e);
        }
    }

    @Override
    public void removeFollowFromCache(Long userId, Long followUserId) {
        try {
            String key = CacheKeyUtil.getUserFollowIdsKey(userId);
            redisTemplate.opsForZSet().remove(key, followUserId.toString());
        } catch (Exception e) {
            LOGGER.error("removeFollowFromCache catch exception, userId={}, followUserId={}", userId, followUserId, e);
        }
    }

    @Override
    public PageResult<Long> getFanUserIdFromCache(Long userId, PageParam pageParam) {
        try {
            String key = CacheKeyUtil.getUserFanIdsKey(userId);
            return getUserIdFromZset(key, pageParam);
        } catch (Exception e) {
            LOGGER.error("getFanUserIdFromCache catch exception, userId={}, pageParam={}", userId, JSON.toJSONString(pageParam), e);
        }
        return null;
    }

    @Override
    public PageResult<Long> getFollowUserIdFromCache(Long userId, PageParam pageParam) {
        try {
            String key = CacheKeyUtil.getUserFollowIdsKey(userId);
            return getUserIdFromZset(key, pageParam);
        } catch (Exception e) {
            LOGGER.error("getFollowUserIdFromCache catch exception, userId={}, pageParam={}", userId, JSON.toJSONString(pageParam), e);
        }
        return null;
    }

    /**
     * query page data from redis
     *
     * @param key
     * @param pageParam
     * @return
     */
    private PageResult<Long> getUserIdFromZset(String key, PageParam pageParam) {
        List<Long> userIds = new ArrayList<>();
        Long total = redisTemplate.opsForZSet().size(key);
        if (total > 0) {
            int offset = pageParam.getPage() * pageParam.getPageSize();
            offset = Math.max(0, offset);
            Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet()
                    .reverseRangeByScoreWithScores(key, 0, System.currentTimeMillis(), offset, pageParam.getPageSize());
            if (typedTuples != null && !typedTuples.isEmpty()) {
                for (ZSetOperations.TypedTuple<Object> tuple : typedTuples) {
                    userIds.add(Long.valueOf(tuple.getValue().toString()));
                }
            }
        }

        PageResult<Long> pageResult = new PageResult();
        pageResult.setTotal(total);
        pageResult.setPageSize(pageParam.getPageSize());
        pageResult.setCurPage(pageParam.getPage());
        pageResult.setTotalPage(pageParam.getPageSize() == 0 ? 1 : (int) Math.ceil((double) total / pageParam.getPageSize()));
        pageResult.setData(userIds);
        return pageResult;
    }

}
