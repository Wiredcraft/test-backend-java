package com.wiredcraft.user;

import com.wiredcraft.user.entity.UserInfo;
import com.wiredcraft.user.model.UserInfoDTO;
import com.wiredcraft.user.repository.UserInfoRepository;
import com.wiredcraft.user.service.UserInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author jeremy.zhang
 * @date 2022-10-19
 */
@ActiveProfiles("test")
@DisplayName("用户功能测试")
@SpringBootTest
@Transactional
class TestUserInfoService {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    @DisplayName("创建用户测试")
    void testCreatedUser() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setAddress("pu dong");
        userInfoDTO.setDob(LocalDateTime.now());
        userInfoDTO.setDescription("good man");
        userInfoDTO.setName("jeremy");
        String uuid = userInfoService.createUser(userInfoDTO);
        UserInfo byUuid = userInfoRepository.findByUuid(uuid);
        Assertions.assertNotNull(byUuid, "用户创建失败");
    }

    @Test
    @DisplayName("更新用户信息")
    void testUpdateUserInfo() {
        String uuid = userInfoRepository.save(initUserInfo()).getUuid();
        UserInfo byUuid = userInfoRepository.findByUuid(uuid);
        byUuid.setName("jeremy");
        String newName = userInfoRepository.save(byUuid).getName();
        Assertions.assertEquals("jeremy", newName, "用户信息更新失败");
    }

    @Test
    @DisplayName("删除用户信息")
    void testDeleteUser() {
        String uuid = userInfoRepository.save(initUserInfo()).getUuid();
        UserInfo byUuid = userInfoRepository.findByUuid(uuid);
        byUuid.setIsDeleted(true);
        UserInfo save = userInfoRepository.save(byUuid);
        UserInfo deletedOne = userInfoRepository.findByUuid(uuid);
        Assertions.assertNull(deletedOne, "删除失败");
    }

    @Test
    @DisplayName("根据uuid查询用户信息")
    void queryUserByUuid() {
        String uuid = userInfoRepository.save(initUserInfo()).getUuid();
        UserInfo byUuid = userInfoRepository.findByUuid(uuid);
        Assertions.assertNotNull(byUuid, "根据id查询失败");
    }

    private UserInfo initUserInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.setDescription("I am a good person");
        userInfo.setAddress("pu dong");
        userInfo.setUuid(UUID.randomUUID().toString());
        userInfo.setIsDeleted(false);
        userInfo.setName("jeremy");
        userInfo.setDob(LocalDateTime.now());
        return userInfo;
    }
}
