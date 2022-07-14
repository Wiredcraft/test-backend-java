package com.lyt.backend.service;

import com.lyt.backend.daos.UserInfoRepository;
import com.lyt.backend.daos.UserPasswordRepository;
import com.lyt.backend.models.UserInfoDTO;
import com.lyt.backend.models.UserPersonalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service to handle retrieving and updating user info.
 */
@Service
public class UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserPasswordRepository userPasswordRepository;

    public UserInfoDTO getUserInfo(String userName){
        return userPasswordRepository.findByName(userName).map(info -> {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            UserPersonalInfo userPersonalInfo = userInfoRepository.findById(info.getId()).get();
            userInfoDTO.setName(info.getName());
            userInfoDTO.setId(info.getId());
            userInfoDTO.setAddress(userPersonalInfo.getAddress());
            userInfoDTO.setDescription(userPersonalInfo.getDescription());
            userInfoDTO.setCreatedAt(userPersonalInfo.getCreatedAt());
            userInfoDTO.setUpdatedAt(userPersonalInfo.getUpdatedAt());
            userInfoDTO.setDescription(userPersonalInfo.getDescription());
            return userInfoDTO;
        }).orElseThrow(() -> new UsernameNotFoundException(userName));
    }

    public void updateAddressAndDescription(String username, String newAddress, String newDescription) {
        userPasswordRepository.findByName(username).map(info -> {
            UserPersonalInfo userPersonalInfo = userInfoRepository.findById(info.getId()).get();
            if(newAddress != null) {
                userPersonalInfo.setAddress(newAddress);
            }
            if(newDescription != null) {
                userPersonalInfo.setDescription(newDescription);
            }
            userInfoRepository.save(userPersonalInfo);
            return userPersonalInfo;
        }).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
