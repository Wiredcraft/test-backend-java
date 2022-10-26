package com.craig.user.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.internal.matchers.GreaterOrEqual;
import org.mockito.internal.matchers.LessThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.craig.user.TestApplication;
import com.craig.user.model.FollowerModel;
import com.craig.user.model.SimpleUserModel;
import com.craig.user.model.UserDetailModel;
import com.craig.user.service.FollowerService;

@SpringBootTest(classes = TestApplication.class)
public class FollowControllerTest {
    @MockBean
    private FollowerService followerService;

    @Autowired
    private FollowController followController;

    @BeforeEach
    void setup() throws Exception {
        Mockito.when(followerService.getFollowers(Mockito.longThat(new GreaterOrEqual<Long>(0L))))
            .thenReturn(List.of(new SimpleUserModel()));

        Mockito.when(followerService.getFollowings(Mockito.longThat(new GreaterOrEqual<Long>(0L))))
            .thenReturn(List.of(new SimpleUserModel()));

        Mockito.when(followerService.deleteFollower(Mockito.longThat(new GreaterOrEqual<Long>(0L)), any()))
        .thenReturn(true);
        Mockito.when(followerService.deleteFollower(Mockito.longThat(new LessThan<Long>(0L)), any()))
        .thenReturn(false);        
        
        FollowerModel result = new FollowerModel();
        result.setId(1L);
        Mockito.when(followerService.addFollowers(Mockito.longThat(new GreaterOrEqual<Long>(0L)),any()))
        .thenReturn(result);
    }

    @Test
    void testAddFollowers() {
        UserDetailModel mockUser = new UserDetailModel();
        mockUser.setId(1L);
        mockUser.setName("mockUser");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(mockUser,null,List.of());
        SecurityContextImpl context = new SecurityContextImpl(token);
       
        try(MockedStatic<SecurityContextHolder> utilities = Mockito.mockStatic(SecurityContextHolder.class)){
            utilities.when(SecurityContextHolder::getContext).thenReturn(context);
                
            ResponseEntity<FollowerModel> notFoundResponse = followController.addFollowers(-1L);
            assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());
    
            ResponseEntity<FollowerModel> result = followController.addFollowers(1L);
            assertEquals(HttpStatus.CREATED, result.getStatusCode());
            assertEquals(1L, result.getBody().getId());
        }
    }

    @Test
    void testGetFollowers() {
        ResponseEntity<List<SimpleUserModel>> notFoundResult = followController.getFollowers(-1L);
        assertEquals(HttpStatus.NOT_FOUND, notFoundResult.getStatusCode());
        assertTrue(CollectionUtils.isEmpty(notFoundResult.getBody()));

        ResponseEntity<List<SimpleUserModel>> result = followController.getFollowers(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertFalse(CollectionUtils.isEmpty(result.getBody()));
        assertEquals(1, result.getBody().size());
    }

    @Test
    void testGetFollowings() {
        ResponseEntity<List<SimpleUserModel>> notFoundResult = followController.getFollowings(-1L);
        assertEquals(HttpStatus.NOT_FOUND, notFoundResult.getStatusCode());
        assertTrue(CollectionUtils.isEmpty(notFoundResult.getBody()));

        ResponseEntity<List<SimpleUserModel>> result = followController.getFollowings(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertFalse(CollectionUtils.isEmpty(result.getBody()));
        assertEquals(1, result.getBody().size());
    }

    @Test
    void testRemoveFollowers() {
        UserDetailModel mockUser = new UserDetailModel();
        mockUser.setId(1L);
        mockUser.setName("mockUser");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(mockUser,null,List.of());
        SecurityContextImpl context = new SecurityContextImpl(token);
       
        try(MockedStatic<SecurityContextHolder> utilities = Mockito.mockStatic(SecurityContextHolder.class)){
            utilities.when(SecurityContextHolder::getContext).thenReturn(context);
                
            ResponseEntity<Void> notFoundResult = followController.removeFollowers(-1L);
            assertEquals(HttpStatus.NOT_FOUND, notFoundResult.getStatusCode());
    
            ResponseEntity<Void> result = followController.removeFollowers(1L);
            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        }
    }
}
