package com.wiredcraft.test.user.model.req;

import lombok.Data;

import java.util.Date;

/**
 * User API 入参
 */
@Data
public class UserReq {

    /**
     * User ID
     */
    private Integer id;

    /**
     * User name
     */
    private String name;

    /**
     * Date of birth
     */
    private Date dob;

    /**
     * User address
     */
    private String address;

    /**
     * User description
     */
    private String description;

    /**
     * User create date
     */
    private Date createdAt;
}
