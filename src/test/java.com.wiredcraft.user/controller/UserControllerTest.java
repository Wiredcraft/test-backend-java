package controller;

import com.wiredcraft.user.UserApplication;
import com.wiredcraft.user.controller.UserController;
import com.wiredcraft.user.model.User;
import com.wiredcraft.user.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@ActiveProfiles("test")
public class UserControllerTest {


    @Test
    public void createTest(){
        //todo
    }

    @Test
    public void deleteTest(){
        //todo
    }

    @Test
    public void updateTest(){

    }

    @Test
    public void getTest(){
        UserController userController  = new UserController();
        UserServiceImpl userService = Mockito.mock(UserServiceImpl.class);
        try {
            Field field = userController.getClass().getDeclaredField("userService");
            field.setAccessible(true);
            field.set(userController, userService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //mock user service call error
        Mockito.when(userService.getUserById(1L))
                .thenThrow(new NullPointerException());
        //mock user not exists
        Mockito.when(userService.getUserById(2L))
                .thenReturn(Optional.empty());
        //mock user exists
        Mockito.when(userService.getUserById(3L))
                .thenReturn(Optional.of(new User()));
        Method method;
        try {
            method = userController.getClass().getDeclaredMethod("get", long.class);
            method.setAccessible(true);
            ResponseEntity<Object> responseEntity = (ResponseEntity<Object>)method.invoke(userController, 1L);
            Assert.assertEquals(400, responseEntity.getStatusCode().value());
            responseEntity = (ResponseEntity<Object>)method.invoke(userController, 2L);
            Assert.assertEquals(201, responseEntity.getStatusCode().value());
            responseEntity = (ResponseEntity<Object>)method.invoke(userController, 3L);
            Assert.assertEquals(200, responseEntity.getStatusCode().value());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }



}
