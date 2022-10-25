package com.craig.user.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import org.mockito.internal.matchers.GreaterOrEqual;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.craig.user.TestApplication;
import com.craig.user.entity.User;
import com.craig.user.entity.dto.NearbyUserDto;
import com.craig.user.model.PageResult;
import com.craig.user.model.SimpleUserModel;
import com.craig.user.model.UserDetailModel;
import com.craig.user.model.UserModel;
import com.craig.user.model.UserQueryModel;
import com.craig.user.repository.UserCoordinateRepository;
import com.craig.user.repository.UserRepository;
import com.craig.user.service.FollowerService;
import com.craig.user.util.PasswordUtil;

@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FollowerService followerService;

    @MockBean
    private UserCoordinateRepository userCoordinateRepository;

    @Autowired
    private UserServiceImpl userService;

    private static Map<Long, User> mockDb = new HashMap<>();

    private static Map<String, User> mockUserDbByName = new HashMap<>();

    private static String password = "CAD21ECC71B7633CA3BFE018822F24C143EFFA1505F030FCCF0E3695";

    private static Long idSeed = 4L;

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
        Mockito.reset(userRepository);

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

        Mockito.when(followerService.getFollowers(Mockito.any())).thenReturn(List.of(new SimpleUserModel()));
        Mockito.when(followerService.getFollowings(Mockito.any())).thenReturn(List.of(new SimpleUserModel()));

        Mockito.when(userCoordinateRepository.getNearbyUsers(Mockito.longThat(new GreaterOrEqual<Long>(0L)), Mockito.intThat(new GreaterOrEqual<Integer>(1))))
            .thenAnswer(a -> {
                List<NearbyUserDto> dtos = new ArrayList<>();
                int size = a.getArgument(1);
                for(Integer i = 0;i<size;i++) {
                    NearbyUserDto dto = new NearbyUserDto();
                    dto.setDistance(BigDecimal.valueOf(i+10));
                    dto.setUserId(i.longValue());
                    dto.setUserName("coordUser"+i);
                    dtos.add(dto);
                }
                return dtos;
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
        model.setAddressLat(BigDecimal.valueOf(10.1));
        model.setAddressLng(BigDecimal.valueOf(20.1));
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

        UserDetailModel userWithFollwer = userService.getUserDetail(idSeed - 1, true, true);
        assertNotNull(userWithFollwer);
        assertNotNull(userWithFollwer.getFollowers());
        assertTrue(!CollectionUtils.isEmpty(userWithFollwer.getFollowers()));
        assertNotNull(userWithFollwer.getFollowing());
        assertTrue(!CollectionUtils.isEmpty(userWithFollwer.getFollowing()));
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
    void testGetNearbyUsers() {

        assertNull(userService.getNearbyUsers(-1L));

        List<SimpleUserModel> result = userService.getNearbyUsers(idSeed - 1);
        assertNotNull(result);
        assertEquals(10, result.size());
        assertEquals(0L, result.get(0).getUserId());
    }
}
