import com.jiang.test.backend.entity.User;
import com.jiang.test.backend.repository.UserRepository;
import com.jiang.test.backend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        user = User.builder()
                .id(3)
                .address("shenzhen")
                .name("jiang")
                .dob(new Date())
                .description("test")
                .createdAt(new Date())
                .build();
    }

    @Test
    public void testGetUser(){
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Optional<User> user1 = userService.getUserById(3);
        Assertions.assertEquals(user,user1.get());
    }

    @Test
    public void testAddUser(){
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        User user1 = userService.addUser(user);
        Assertions.assertEquals(3,user1.getId());
    }

    @Test
    public void testUpdateUser(){
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        User user1 = userService.updateUser(user);
        Assertions.assertEquals(3,user1.getId());
    }

    @Test
    public void testDelUser(){
        Mockito.doNothing().when(userRepository).deleteById(3);
        userService.deleteUserById(3);
    }
}
