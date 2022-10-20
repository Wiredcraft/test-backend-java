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
    @ApiModelProperty(name = "name", value = "userName", notes = "userName")
    private String name;
    @ApiModelProperty(name = "dob", value = "2022:01:01 00:00:01", notes = "date of birth")
    @JsonFormat(pattern = "yyyy:MM:DD HH:mm:SS")
    private LocalDateTime dob;
    @ApiModelProperty(name = "address", value = "Shanghai", notes = "address of user")
    private String address;
    @ApiModelProperty(name = "description", value = "I am good person", notes = "description about user")
    private String description;
    @ApiModelProperty(name = "uuid", value = "asdada-asdasd-asdadq-asdsasdx", notes = "generated uuid for user")
    private String uuid;

    public static UserInfo convertUserInfo(UserInfoDTO userInfoDTO) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(userInfoDTO.getName());
        userInfo.setDescription(userInfoDTO.getDescription());
        userInfo.setUpdatedBy(RequestHeaderUtils.get());
        userInfo.setCreatedBy(RequestHeaderUtils.get());
        userInfo.setAddress(userInfoDTO.getAddress());
        userInfo.setDob(userInfoDTO.getDob());
        return userInfo;
    }
}
