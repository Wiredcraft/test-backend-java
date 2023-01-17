package com.wiredcraft.user.tiny.modules.ums.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiredcraft.user.tiny.modules.ums.dto.UserParam;
import com.wiredcraft.user.tiny.modules.ums.dto.UpdateAdminPasswordParam;
import com.wiredcraft.user.tiny.modules.ums.mapper.UserLoginLogMapper;
import com.wiredcraft.user.tiny.modules.ums.model.User;
import com.wiredcraft.user.tiny.security.util.JwtTokenUtil;
import com.wiredcraft.user.tiny.modules.ums.model.UserLoginLog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private JwtTokenUtil mockJwtTokenUtil;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private UserLoginLogMapper mockLoginLogMapper;
    @Mock
    private InMemoryUserDetailsManager mockManager;
    @Mock
    private MongoTemplate mockMongoTemplate;

    @InjectMocks
    private UserServiceImpl umsAdminServiceImplUnderTest;

    @Test
    void testGetAdminByUsername() {
        // Setup
        final User expectedResult = new User();
        expectedResult.setId(0L);
        expectedResult.setUsername("username");
        expectedResult.setPassword("password");
        expectedResult.setIcon("icon");
        expectedResult.setEmail("email");
        expectedResult.setNickName("nickName");
        expectedResult.setNote("note");
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setLoginTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setStatus(0);
        expectedResult.setDescription("description");
        expectedResult.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setAddress("address");

        // Run the test
        final User result = umsAdminServiceImplUnderTest.getUserByUsername("username");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }





    @Test
    void testExist() {
        assertThat(umsAdminServiceImplUnderTest.exist("username")).isFalse();
    }

    @Test
    void testRegister() {
        // Setup
        final UserParam userParam = new UserParam();
        userParam.setUsername("username");
        userParam.setPassword("password");
        userParam.setIcon("icon");
        userParam.setEmail("email");
        userParam.setNickName("nickName");
        userParam.setNote("note");

        final User expectedResult = new User();
        expectedResult.setId(0L);
        expectedResult.setUsername("username");
        expectedResult.setPassword("password");
        expectedResult.setIcon("icon");
        expectedResult.setEmail("email");
        expectedResult.setNickName("nickName");
        expectedResult.setNote("note");
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setLoginTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setStatus(0);
        expectedResult.setDescription("description");
        expectedResult.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setAddress("address");

        when(mockPasswordEncoder.encode("password")).thenReturn("password");

        // Run the test
        final User result = umsAdminServiceImplUnderTest.register(userParam);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testLogin() {
        // Setup
        when(mockPasswordEncoder.matches("password", "encodedPassword")).thenReturn(false);
        when(mockJwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("result");
        when(mockManager.userExists("username")).thenReturn(false);
        when(mockLoginLogMapper.insert(new UserLoginLog())).thenReturn(0);

        // Run the test
        final String result = umsAdminServiceImplUnderTest.login("username", "password");

        // Verify the results
        assertThat(result).isEqualTo("result");
        verify(mockManager).createUser(any(UserDetails.class));
        verify(mockLoginLogMapper).insert(new UserLoginLog());
    }

    @Test
    void testRefreshToken() {
        // Setup
        when(mockJwtTokenUtil.refreshHeadToken("oldToken")).thenReturn("result");

        // Run the test
        final String result = umsAdminServiceImplUnderTest.refreshToken("oldToken");

        // Verify the results
        assertThat(result).isEqualTo("result");
    }

    @Test
    void testList() {
        // Setup
        // Run the test
        final Page<User> result = umsAdminServiceImplUnderTest.list("keyword", 0, 0);

        // Verify the results
    }

    @Test
    void testUpdate() {
        // Setup
        final User admin = new User();
        admin.setId(0L);
        admin.setUsername("username");
        admin.setPassword("password");
        admin.setIcon("icon");
        admin.setEmail("email");
        admin.setNickName("nickName");
        admin.setNote("note");
        admin.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        admin.setLoginTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        admin.setStatus(0);
        admin.setDescription("description");
        admin.setBirthday(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        admin.setAddress("address");

        when(mockPasswordEncoder.encode("password")).thenReturn("password");

        // Run the test
        final boolean result = umsAdminServiceImplUnderTest.update(0L, admin);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testDelete() {
        assertThat(umsAdminServiceImplUnderTest.delete(0L)).isFalse();
    }

    @Test
    void testUpdatePassword() {
        // Setup
        final UpdateAdminPasswordParam param = new UpdateAdminPasswordParam();
        param.setUsername("username");
        param.setOldPassword("oldPassword");
        param.setNewPassword("newPassword");

        when(mockPasswordEncoder.matches("oldPassword", "password")).thenReturn(false);
        when(mockPasswordEncoder.encode("newPassword")).thenReturn("password");

        // Run the test
        final int result = umsAdminServiceImplUnderTest.updatePassword(param);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }


}
