package com.coffee.user.request;

import lombok.Data;

@Data
public class UserGeohashCreateParam {
    /**
     * 用户Id
     */
    private String userId;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

}
