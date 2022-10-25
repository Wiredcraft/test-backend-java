package com.craig.user.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.mockito.internal.matchers.GreaterOrEqual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.CollectionUtils;

import com.craig.user.TestApplication;
import com.craig.user.entity.User;
import com.craig.user.entity.UserFollower;
import com.craig.user.entity.dto.SimpleFollowerDto;
import com.craig.user.entity.dto.SimpleFollowingDto;
import com.craig.user.model.FollowerModel;
import com.craig.user.model.SimpleUserModel;
import com.craig.user.repository.UserFollowerRepository;
import com.craig.user.repository.UserRepository;

@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FollowerServiceImplTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserFollowerRepository userFollowerRepository;

    @Autowired
    private FollowerServiceImpl followerService;

    private static Map<Long, List<User>> mockFollowerDb = new HashMap<>();

    private static Map<Long, List<User>> mockFollowingDb = new HashMap<>();

    @BeforeEach
    void setUp() throws Exception {

        Mockito.when(userRepository.getUser(Mockito.longThat(new GreaterOrEqual<Long>(0L)))).thenAnswer(a->{
            User user = new User();
            user.setId(a.getArgument(0));
            user.setName("mockUser" + a.getArgument(0));
            return user;
        });
    
        Mockito.when(userFollowerRepository.getRecord(Mockito.any(), Mockito.any())).thenAnswer(a->{
            Long userId = a.getArgument(0);
            Long followerId = a.getArgument(1);
            List<User> followers = mockFollowerDb.get(userId);
            if(CollectionUtils.isEmpty(followers)){
                return null;
            }
            return followers.stream()
            .filter(f -> f.getId() == followerId)
            .map(f -> {
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
            User followerUser = new User();
            followerUser.setId(followerId);
            followerUser.setName("mockUser" + followerId);
            followers.add(followerUser);
            UserFollower userFollower = new UserFollower();
            userFollower.setUserId(userId);
            userFollower.setFollowerId(followerId);

            List<User> followingList = mockFollowingDb.get(followerId);
            if(followingList == null){
                followingList = new ArrayList<>();
                mockFollowingDb.put(followerId, followingList);
            }
            User followingUser = new User();
            followingUser.setId(userId);
            followingUser.setName("mockUser" + userId);
            followingList.add(followingUser);

            return userFollower;
        });

        Mockito.when(userFollowerRepository.getFollowers(Mockito.any())).thenAnswer(a->{
            List<User> followerList = mockFollowerDb.get(a.getArgument(0));
            if(followerList == null){   
                return  null;
            }
            return followerList.stream().map(c->{
                SimpleFollowerDto dto = new SimpleFollowerDto();
                dto.setFollowerId(c.getId());
                dto.setFollowerName(c.getName());
                return dto;
            }).collect(Collectors.toList());
        });

        Mockito.when(userFollowerRepository.getFollowings(Mockito.any())).thenAnswer(a -> {
            List<User> followingList = mockFollowingDb.get(a.getArgument(0));
            if(followingList == null) {   
                return  null; 
            }
            return followingList.stream().map(c->{
                SimpleFollowingDto dto = new SimpleFollowingDto();
                dto.setFollowingId(c.getId());
                dto.setFollowingName(c.getName());
                return dto;
            }).collect(Collectors.toList());
        });

        doAnswer(a->{
            UserFollower user = a.getArgument(0);
            
            List<User> followerList = mockFollowerDb.get(user.getUserId());
            followerList.removeIf(c->c.getId().equals(user.getFollowerId()));

            List<User> followingList = mockFollowingDb.get(user.getFollowerId());
            followingList.removeIf(c->c.getId().equals(user.getUserId()));

            return null;
        }).when(userFollowerRepository).delete(Mockito.any());
    }

    @Test
    @Order(1)
    void testAddFollowers() {
        assertNull(followerService.addFollowers(-1L, 2L));

        FollowerModel result = followerService.addFollowers(1L, 2L);
        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals(2, result.getFollowerId());

        // do again, test idempotent
        int existCount = mockFollowerDb.get(1L).size();
        FollowerModel newResult = followerService.addFollowers(1L, 2L);
        assertNotNull(newResult);
        assertEquals(1, newResult.getUserId());
        assertEquals(2, newResult.getFollowerId());
        assertEquals(existCount, mockFollowerDb.get(1L).size());
    }

    @Test
    @Order(2)
    void testGetFollowers() {
        List<SimpleUserModel> followerList = followerService.getFollowers(1L);
        assertNotNull(followerList);
        assertEquals(1, followerList.size());
        assertEquals(2, followerList.get(0).getUserId());
    }

    @Test
    @Order(3)
    void testGetFollowings() {
        List<SimpleUserModel> followingList = followerService.getFollowings(2L);
        assertNotNull(followingList);
        assertEquals(1, followingList.size());
        assertEquals(1, followingList.get(0).getUserId());
    }

    @Test
    @Order(4)
    void testDeleteFollower() {
        assertFalse(followerService.deleteFollower(2L, 1L));
        assertTrue(followerService.deleteFollower(1L, 2L));

        assertEquals(0, mockFollowerDb.get(1L).size());
        assertEquals(0, mockFollowingDb.get(2L).size());
    }
}
