package com.craig.user.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.craig.user.entity.User;
import com.craig.user.entity.UserFollower;
import com.craig.user.model.FollowerModel;
import com.craig.user.model.PageResult;
import com.craig.user.model.UserDetailModel;
import com.craig.user.model.UserModel;
import com.craig.user.model.UserQueryModel;
import com.craig.user.repository.UserCoordinateRepository;
import com.craig.user.repository.UserFollowerRepository;
import com.craig.user.repository.UserRepository;
import com.craig.user.util.PasswordUtil;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserFollowerRepository userFollowerRepository;

    @MockBean
    private UserCoordinateRepository userCoordinateRepository;

    @Autowired
    private UserServiceImpl userService;

    private static Map<Long, User> mockDb = new HashMap<>();

    private static Map<String, User> mockUserDbByName = new HashMap<>();

    private static String password = "CAD21ECC71B7633CA3BFE018822F24C143EFFA1505F030FCCF0E3695";

    private static Long idSeed = 4L;

    private static Map<Long, List<User>> mockFollowerDb = new HashMap<>();

    private static Map<Long, List<User>> mockFollowingDb = new HashMap<>();

    @BeforeAll
    static void setUpStaticVariables() throws Exception {

        for (Integer i = 0; i < 3; i++) {
            User user = new User();
            String name = "user" + i;
            user.setName(name);
            user.setPassword(password);
            user.setDeleted(false);
            user.setId(i.longValue());
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            mockDb.put(i.longValue(), user);
            mockUserDbByName.put(name, user);
        }
    }

    @BeforeEach
    void setup() {
        
        Mockito.when(userRepository.getByName(Mockito.anyString())).thenAnswer(a->{
            String userName = a.getArgument(0, String.class);
            return mockUserDbByName.get(userName);
        });

        Mockito.when(userRepository.getUser(Mockito.anyLong())).thenAnswer(a->{
            return getFromId(a);
        });

        Mockito.when(userRepository.getById(Mockito.anyLong())).thenAnswer(a->{
            return getFromId(a);
        });

        Mockito.when(userRepository.addUser(Mockito.any())).thenAnswer(a->{
            User user = a.getArgument(0);
            user.setId(idSeed++);
            user.setDeleted(false);
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());

            mockDb.put(user.getId(), user);
            mockUserDbByName.put(user.getName(), user);
            return user;
        });

        Mockito.when(userRepository.getUsers(Mockito.any())).thenAnswer(a->{
            List<User> users = new ArrayList<User>(mockDb.values());
            Page<User> page = new Page<>(1, 10);
            page.setRecords(users);
            page.setTotal(mockDb.size());
            return page;
        });

        doAnswer(a->{
            User user = a.getArgument(0);
            mockDb.remove(user.getId());
            mockUserDbByName.remove(user.getName());
            return null;
        }).when(userRepository).deleteUser(Mockito.any());

        doAnswer(a->{
            User user = a.getArgument(0);
            mockDb.put(user.getId(), user);
            mockUserDbByName.put(user.getName(), user);
            return null;
        }).when(userRepository).updateSelective(Mockito.any());

        Mockito.when(userFollowerRepository.getRecord(Mockito.any(), Mockito.any())).thenAnswer(a->{
            Long userId = a.getArgument(0);
            Long followerId = a.getArgument(1);
            List<User> followers = mockFollowerDb.get(userId);
            if(CollectionUtils.isEmpty(followers)){
                return null;
            }
            return followers.stream()
            .filter(f->f.getId() == followerId)
            .map(f->{
                UserFollower uf = new UserFollower();
                uf.setUserId(userId);
                uf.setFollowerId(f.getId());
                return uf;
            })
            .findFirst().orElse(null);
        });

        Mockito.when(userFollowerRepository.addUserFollower(Mockito.any(), Mockito.any())).thenAnswer(a->{
            Long userId = a.getArgument(0);
            Long followerId = a.getArgument(1);
            List<User> followers = mockFollowerDb.get(userId);
            if(CollectionUtils.isEmpty(followers)){
                followers = new ArrayList<>();
                mockFollowerDb.put(userId, followers);
            }
            followers.add(mockDb.get(followerId));
            UserFollower userFollower = new UserFollower();
            userFollower.setUserId(userId);
            userFollower.setFollowerId(followerId);
            return userFollower;
        });
    }

    private Object getFromId(InvocationOnMock a) {
        Long userId = a.getArgument(0, Long.class);
        return mockDb.get(userId);
    }

    @Test
    @Order(1)
    void testCreateUser() {
        UserModel model = new UserModel();
        model.setName("user4");
        model.setDescription("create from unit test code");
        model.setPassword("654321");
        assertNotNull(userService.createUser(model));

    }

    @Test
    @Order(2)
    void testGetUserDetail() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        UserDetailModel user = userService.getUserDetail(idSeed - 1, null, null);
        assertNotNull(user);
        assertNotNull(user.getName());
        assertEquals(user.getId(), idSeed - 1);
        // password never exposed to front end
        assertNull(user.getPassword());
        // no query follower relate data
        assertNull(user.getFollowers());
        assertNull(user.getFollowing());

        User userEntity = mockDb.get(user.getId());
        PasswordUtil.validate("654321", userEntity.getPassword());
    }

    @Test
    @Order(3)
    void testDeleteUser() {
        userService.deleteUser(--idSeed);
        assertEquals(3, mockDb.size());
        assertEquals(3, mockUserDbByName.size());
    }

    @Test
    @Order(4)
    void testGetUserByName() {
        for (Integer i = 0; i < 3; i++) {
            UserDetailModel user = userService.getUserByName("user" + i, null, null);
            assertNotNull(user);
        }
    }

    @Test
    void testGetUsers() {
        UserQueryModel query = new UserQueryModel();
        PageResult<UserModel> users = userService.getUsers(query);
        assertNotNull(users);
        assertNotNull(users.getData());
        assertEquals(3, users.getTotal());
    }

    @Test
    void testUpdateNullUser() {
        UserModel model = new UserModel();
        model.setId(-1L);
        model.setDescription("this is description");
        assertNull(userService.updateUser(model));
    }

    @Test
    void testUpdateUser() {
        UserModel model = new UserModel();
        model.setId(1L);
        model.setDescription("this is description");
        UserModel update = userService.updateUser(model);
        assertNotNull(update);

        UserDetailModel userDetail = userService.getUserDetail(1L, null, null);
        assertNotNull(userDetail);
        assertEquals(userDetail.getDescription(), model.getDescription());
    }

    @Test
    void testAddFollowers() {
        assertNull(userService.addFollowers(-1L, 2L));

        FollowerModel result = userService.addFollowers(1L, 2L);
        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals(2, result.getFollowerId());

        // do again
        FollowerModel newResult = userService.addFollowers(1L, 2L);
        assertNotNull(newResult);
        assertEquals(1, newResult.getUserId());
        assertEquals(2, newResult.getFollowerId());
    }

    @Test
    void testDeleteFollower() {

    }

    @Test
    void testGetFollowers() {

    }

    @Test
    void testGetFollowings() {

    }

    @Test
    void testGetNearbyUsers() {

    }
}
