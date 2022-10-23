package com.craig.user.entity.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class NearbyUserDto {
    private Long userId;

    private String userName;

    private BigDecimal distance;
}
