package me.solution.mapper;

import me.solution.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
* test for user mapper
*
* @author davincix
* @since 2023/5/20 19:21
*/
@SpringBootTest
class UserMapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }
}
