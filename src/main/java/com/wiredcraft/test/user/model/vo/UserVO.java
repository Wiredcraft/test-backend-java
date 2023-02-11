package com.wiredcraft.test.user.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * User VO 出参
 */
@Data
public class UserVO {

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
