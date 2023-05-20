package me.solution.service;

import me.solution.utils.component.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * test for #{@link me.solution.utils.component.RedisCache}
 *
 * @author davincix
 * @since 2023/5/20 19:08
 */
@SpringBootTest
public class RedisCacheServiceTest {
    @Autowired
    private RedisCache redisCache;

    @Test
    public void testRedisCache() {
        Boolean hello = redisCache.redisTemplate.hasKey("hello");
        System.out.println(hello);
    }
}
