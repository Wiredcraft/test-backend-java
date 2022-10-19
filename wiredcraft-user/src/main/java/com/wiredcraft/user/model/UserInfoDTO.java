package com.wiredcraft.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiredcraft.user.entity.UserInfo;
import com.wiredcraft.user.utils.RequestHeaderUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author jeremy.zhang
 * @date 2022-10-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    @ApiModelProperty(name = "", value = "")
    private String name;
    @JsonFormat(pattern = "yyyy:MM:DD HH:mm:SS")
    private LocalDateTime dob;
    private String address;
    private String description;

    public static UserInfo convertUserInfo(UserInfoDTO userInfoDTO) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(userInfoDTO.getName());
        userInfo.setDescription(userInfoDTO.getDescription());
        userInfo.setUpdatedBy(RequestHeaderUtils.get());
        userInfo.setCreatedBy(RequestHeaderUtils.get());
        userInfo.setAddress(userInfoDTO.getAddress());
        return userInfo;
    }
}
