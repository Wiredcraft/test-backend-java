package com.wiredcraft.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiredcraft.user.entity.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author jeremy.zhang
 * @date 2022-10-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {
    @ApiModelProperty(name = "uuid", value = "generated uuid")
    private String uuid;
    @ApiModelProperty(name = "name", value = "user's name")
    private String name;
    @ApiModelProperty(name = "address", value = "user's address")

    private String address;
    @ApiModelProperty(name = "description", value = "user's brief description")

    private String description;
    @ApiModelProperty(name = "dob", value = "user's birth date")
    @JsonFormat(pattern = "yyyy:MM:DD HH:mm:SS")
    private LocalDateTime dob;
    @ApiModelProperty(value = "date of creating user")
    @JsonFormat(pattern = "yyyy:MM:DD HH:mm:SS")
    private LocalDateTime createdAt;


    public static UserInfoVO convertTOVo(UserInfo userInfo) {
        return UserInfoVO.builder()
                .uuid(userInfo.getUuid())
                .createdAt(userInfo.getCreatedDate())
                .description(userInfo.getDescription())
                .name(userInfo.getName())
                .dob(userInfo.getDob())
                .address(userInfo.getAddress())
                .build();
    }
}
