package me.solution.service;

import me.solution.common.enums.RedisKeyEnum;
import me.solution.common.utils.component.RedisUtil;
import me.solution.security.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * login cache service
 *
 * @author davincix
 * @since 2023/5/23 20:38
 */
@Service
public class LoginCacheService {
    @Autowired
    private RedisUtil redisUtil;

    public void setLoginCache(Long userId, LoginUser loginUser) {
        String key = RedisKeyEnum.LOGIN_TOKEN.formatKey(userId);

        long ttl = RedisKeyEnum.LOGIN_TOKEN.getTimeUnit().toSeconds(RedisKeyEnum.LOGIN_TOKEN.getTtl());
        int rand = ThreadLocalRandom.current().nextInt(-90, 90);

        redisUtil.set(key, loginUser, ttl + rand);
    }

    public void delLoginCache(Long userId) {
        redisUtil.del(RedisKeyEnum.LOGIN_TOKEN.formatKey(userId));
    }

    public LoginUser getLoginUserByUserId(String userId) {
        String key = RedisKeyEnum.LOGIN_TOKEN.formatKey(userId);
        return (LoginUser) redisUtil.get(key);
    }
}
