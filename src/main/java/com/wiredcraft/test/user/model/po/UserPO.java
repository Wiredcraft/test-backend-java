package com.wiredcraft.test.user.model.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * User PO 持久化
 */
@Data
@Entity
public class UserPO {

    /**
     * User ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
