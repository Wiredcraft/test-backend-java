package com.craig.user.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;

@Data
public class UserModel {
    private Long id;
    
    private String name;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "day of birth(only 'yyyy-MM-dd' format supported)", example = "2022-10-23")
    private Date dob;

    private String address;

    @Schema(description = "address longitude", accessMode = AccessMode.WRITE_ONLY)
    private BigDecimal addressLng;

    @Schema(description = "address latitude", accessMode = AccessMode.WRITE_ONLY)
    private BigDecimal addressLat;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(accessMode = AccessMode.READ_ONLY, example = "2022-10-23 18:53:44")
    private Date createdAt;

    @Schema(accessMode = AccessMode.WRITE_ONLY)
    private String password;
}
