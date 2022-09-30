package com.coffee.user.domain.po;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserGeohashPO extends BasePO{
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

    /**
     * 经纬度所计算的geohash码
     */
    private String geoCode;
}
