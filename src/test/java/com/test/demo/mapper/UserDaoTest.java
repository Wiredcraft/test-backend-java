package com.test.demo.mapper;

import com.test.demo.entity.UserDo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
@SpringBootTest
public class UserDaoTest {

    private SqlSession sqlSession;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
            UserDo userDo = new UserDo();
            userDo.setName("aaa" + i);
            Calendar calendar = Calendar.getInstance();
            calendar.set(1990, 6, 6);
            userDo.setDob(calendar.getTime());
            userDo.setDescription("this is first guy " + i);
            userDo.setPassword(passwordEncoder.encode("password" + i));
            userDo.setLatitude(new Double(123.12));
            userDo.setLongitude(new Double(445.23));
            userMapper.addUser(userDo);
            System.out.println(userDo.getId());
        }
    }

    @Test
    public void testUpdateDOB() throws ParseException {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        UserDo userDo = new UserDo();
        userDo.setId(6);
        userDo.setDob(new SimpleDateFormat("yyyy-mm-dd").parse("1970-05-09"));
        userMapper.updateUser(userDo);
        System.out.println(userDo);
        UserDo expected = new UserDo();
        expected.setId(6);
        List<UserDo> userDos = userMapper.selectUserByCondition(expected);
        for (UserDo test : userDos) {
            System.out.println(test);
        }
    }

    @Test
    public void testUpdate() throws ParseException {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        UserDo userDo = new UserDo();
        userDo.setId(6);
        userDo.setName("updated_name");
        userDo.setCreateTime(new SimpleDateFormat("yyyy-mm-dd").parse("1970-05-09"));
        userMapper.updateUser(userDo);
        UserDo expected = new UserDo();
        expected.setId(6);
        List<UserDo> userDos = userMapper.selectUserByCondition(expected);
        for (UserDo test : userDos) {
            System.out.println(test);
        }
    }

    @Test
    public void testQueryByCondition() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        UserDo userDo = new UserDo();
        userDo.setName("rucheng");
        List<UserDo> userDos = userMapper.selectUserByCondition(userDo);
        for (UserDo test : userDos) {
            System.out.println(test);
            Assert.assertEquals("rucheng", test.getName());
        }
    }

    @Test
    public void testDeleteUser() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.deleteUserById(9);
        UserDo userDo = new UserDo();
        userDo.setId(9);
        List<UserDo> userDos = userMapper.selectUserByCondition(userDo);
        Assert.assertTrue(userDos.isEmpty());
    }
}
