package com.wiredcraft.user.tiny.modules.ums.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserLocationParam {

    @Pattern(regexp = "/^[\\-\\+]?(0?\\d{1,2}\\.\\d{1,5}|1[0-7]?\\d{1}\\.\\d{1,5}|180\\.0{1,5})$/",message = "latitude is invalid ")
    private Double latitude;

    @Pattern(regexp = "/^[\\-\\+]?(0?\\d{1,2}\\.\\d{1,5}|1[0-7]?\\d{1}\\.\\d{1,5}|180\\.0{1,5})$/",message = "longitude is invalid ")
    private Double longitude;
}
