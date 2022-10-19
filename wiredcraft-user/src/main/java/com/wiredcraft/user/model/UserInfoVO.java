package com.wiredcraft.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiredcraft.user.entity.UserInfo;
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
    private String uuid;
    private String name;
    private String address;
    private String description;
    @JsonFormat(pattern = "yyyy:MM:DD HH:mm:SS")
    private LocalDateTime dob;
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
