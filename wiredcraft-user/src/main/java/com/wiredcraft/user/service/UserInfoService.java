package com.wiredcraft.user.service;

import com.wiredcraft.common.ServiceException;
import com.wiredcraft.user.entity.UserInfo;
import com.wiredcraft.user.model.UserInfoDTO;
import com.wiredcraft.user.model.UserInfoVO;
import com.wiredcraft.user.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * @author jeremy.zhang
 * @date 2022-10-19
 */
@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    public String createUser(UserInfoDTO userInfoDTO) {
        UserInfo userInfo = UserInfoDTO.convertUserInfo(userInfoDTO);
        userInfo.setUuid(UUID.randomUUID().toString());
        return userInfoRepository.save(userInfo).getUuid();
    }


    public void updateUser(UserInfoDTO userInfoDTO) {
        UserInfo userInfo = userInfoRepository.findByUuid(userInfoDTO.getUuid());
        userInfo.setAddress(userInfo.getAddress());
        userInfo.setName(userInfo.getName());
        userInfo.setDob(userInfo.getDob());
        userInfo.setDescription(userInfo.getDescription());
        userInfoRepository.save(userInfo);
    }

    public void deleteUser(String uuid) {
        UserInfo userInfo = userInfoRepository.findByUuid(uuid);
        if (Objects.isNull(userInfo)) {
            throw new ServiceException(-1, "user is not existed..");
        }
        userInfo.setIsDeleted(true);
        userInfoRepository.save(userInfo);
    }

    public UserInfoVO queryByUuid(String uuid) {
        UserInfo userInfo = userInfoRepository.findByUuid(uuid);
        if (Objects.isNull(userInfo)) {
            throw new ServiceException(-1, "user is not existed..");
        }
        return UserInfoVO.convertTOVo(userInfo);
    }


    public void saveUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setAddress("1");
        userInfo.setCreatedBy("-1");
        userInfo.setUpdatedBy("-1'");
        userInfo.setIsDeleted(false);
        userInfo.setName("xxx");
        userInfo.setCreatedDate(LocalDateTime.now());
        userInfo.setUpdatedDate(LocalDateTime.now());
        userInfo.setDescription("xxx");
        userInfo.setUuid("2e");
        userInfo.setDob(LocalDateTime.now());
        userInfoRepository.save(userInfo);
    }
}
