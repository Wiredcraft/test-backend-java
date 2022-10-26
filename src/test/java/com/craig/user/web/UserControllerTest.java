package com.craig.user.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.GreaterOrEqual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.craig.user.TestApplication;
import com.craig.user.model.PageResult;
import com.craig.user.model.SimpleUserModel;
import com.craig.user.model.UserDetailModel;
import com.craig.user.model.UserModel;
import com.craig.user.model.UserQueryModel;
import com.craig.user.service.UserService;

@SpringBootTest(classes = TestApplication.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    @BeforeEach
    void setup() throws Exception {
        Mockito.when(userService.getUserDetail(Mockito.longThat(new GreaterOrEqual<Long>(0L)), any(), any()))
            .thenReturn(new UserDetailModel());

        Mockito.when(userService.getUsers(any())).thenAnswer(a->{
            UserQueryModel query = a.getArgument(0);
            PageResult<UserModel> result = new PageResult<UserModel>();
            if(query.getCurrent() > 0L){
                result.setTotal(1L);
            }else{
                result.setTotal(0L);
            }
            return result;
        });

        Mockito.when(userService.getNearbyUsers(Mockito.longThat(new GreaterOrEqual<Long>(0L))))
           .thenReturn(List.of(new SimpleUserModel()));

        Mockito.when(userService.deleteUser(Mockito.longThat(new GreaterOrEqual<Long>(0L))))
           .thenReturn(true);

        Mockito.when(userService.createUser(any())).thenAnswer(a->{
            UserModel model = a.getArgument(0);
            if("invalid".equals(model.getName())){
                return model;
            }
            model.setId(1L);
            return model;
        });

        Mockito.when(userService.updateUser(any())).thenAnswer(a->{
            UserModel model = a.getArgument(0);
            if(model.getId() == -1L){
                return null;
            }
            return model;
        });
    }

    @Test
    void testCreateUser() {
        UserModel user = new UserModel();
        user.setName("invalid");
        ResponseEntity<UserModel> invalidResult = userController.createUser(user);
        assertEquals(HttpStatus.NO_CONTENT, invalidResult.getStatusCode());

        user.setName("admin");
        ResponseEntity<UserModel> result = userController.createUser(user);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void testDeleteUser() {
        ResponseEntity<Void> notFoundResult = userController.deleteUser(-1L);
        assertEquals(HttpStatus.NOT_FOUND, notFoundResult.getStatusCode());

        ResponseEntity<Void> result = userController.deleteUser(1L);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void testGetNearbyUser() {
        ResponseEntity<List<SimpleUserModel>> notFoundResult = userController.getNearbyUser(-1L);
        assertEquals(HttpStatus.NOT_FOUND, notFoundResult.getStatusCode());
        assertNull(notFoundResult.getBody());

        ResponseEntity<List<SimpleUserModel>> result = userController.getNearbyUser(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void testGetUserDetail() {
        ResponseEntity<UserDetailModel> notFoundResult = userController.getUserDetail(-1L, null, null);
        assertEquals(HttpStatus.NOT_FOUND, notFoundResult.getStatusCode());
        assertNull(notFoundResult.getBody());

        ResponseEntity<UserDetailModel> result = userController.getUserDetail(1L, null, null);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void testGetUsers() {
        UserQueryModel query = new UserQueryModel();

        ResponseEntity<PageResult<UserModel>> okResult = userController.getUsers(query);
        assertEquals(HttpStatus.OK, okResult.getStatusCode());

        query.setCurrent(-1L);
        ResponseEntity<PageResult<UserModel>> noResult = userController.getUsers(query);
        assertEquals(HttpStatus.NO_CONTENT, noResult.getStatusCode());
    }

    @Test
    void testUpdateUser() {
        UserModel user = new UserModel();
        user.setName("invalid");
        ResponseEntity<UserModel> invalidResult = userController.updateUser(user);
        assertEquals(HttpStatus.NO_CONTENT, invalidResult.getStatusCode());

        user.setName("admin");
        ResponseEntity<UserModel> result = userController.updateUser(user);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        user.setId(-1L);
        ResponseEntity<UserModel> notFoundResult = userController.updateUser(user);
        assertEquals(HttpStatus.NO_CONTENT, notFoundResult.getStatusCode());

        user.setId(1L);
        ResponseEntity<UserModel> createResult = userController.updateUser(user);
        assertEquals(HttpStatus.OK, createResult.getStatusCode());
        assertNotNull(createResult.getBody());
    }
}
