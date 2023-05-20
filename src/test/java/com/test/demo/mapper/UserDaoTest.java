package com.test.demo.mapper;

import com.alibaba.druid.util.StringUtils;
import com.test.demo.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * @author zhangrucheng on 2023/5/20
 */
public class UserDaoTest {

    private SqlSession sqlSession;

    @Before
    public void init() {
        SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = Resources.getResourceAsStream("mybatisConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        SqlSessionFactory factory = ssfb.build(resourceAsStream);
        sqlSession = factory.openSession(true);
    }

    @After
    public void release() {
        sqlSession.close();
    }

    @Test
    public void testInsert() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("test" + i);
            Calendar calendar = Calendar.getInstance();
            calendar.set(1990, 6, 6);
            user.setDob(calendar.getTime());
            user.setDescription("this is first guy " + i);
            user.setPassword("testpassword" + i);
            user.setLatitude(new Double(123.12));
            user.setLongitude(new Double(445.23));
            userMapper.addUser(user);
            System.out.println(user.getId());
        }
    }

    @Test
    public void testUpdate() throws ParseException {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(6);
        user.setName("updated_name");
        user.setCreateTime(new SimpleDateFormat("yyyy-mm-dd").parse("1970-05-09"));
        userMapper.updateUser(user);
        User expected = new User();
        expected.setId(6);
        List<User> users = userMapper.selectUserByCondition(expected);
        for (User test : users) {
            System.out.println(test);
        }
    }

    @Test
    public void testQueryByCondition() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setName("rucheng");
        List<User> users = userMapper.selectUserByCondition(user);
        for (User test : users) {
            Assert.assertEquals("rucheng", test.getName());
        }
    }

    @Test
    public void testQueryByNameAndPassword() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        Optional<User> user = userMapper.selectUserByNameAndPassword("updated_name", "testpassword");
        User user1 = user.get();
        System.out.println(user1);
    }

    @Test
    public void testDeleteUser() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.deleteUserById(9);
        User user = new User();
        user.setId(9);
        List<User> users = userMapper.selectUserByCondition(user);
        Assert.assertTrue(users.isEmpty());
    }
}
