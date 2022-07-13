package com.lyt.backend.models;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * {
 *     "id": "xxx",                  // user ID(self-increment/random generated)
 *     "name": "test",               // user name
 *     "password": "",               // user password(hashed and salt-added)
 *     "dob": "",                    // date of birth
 *     "address": "",                // user address
 *     "description": "",            // user description
 *     "createdAt": ""               // user created date
 * }
 */
@Entity
@DynamicInsert
@Table(name = "user_table")
public class User {
    //now assume it's self-incrementing int
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(required = true)
    private Integer id;


    @NotEmpty
    @Column(nullable = false)
    @ColumnDefault("''")
    @ApiModelProperty(value = "Name of user", required = true)
    private String name;

    //private String password;

    @Column(nullable = false)
    @ColumnDefault("current_timestamp")
    @ApiModelProperty(value = "Date of birth of user")
    private Date dob;

    @Column(nullable = false)
    @ColumnDefault("''")
    @ApiModelProperty(value = "Address of user")
    private String address;

    @Column(nullable = false)
    @ColumnDefault("''")
    @ApiModelProperty(value = "Description of user")
    private String description;

    @Column(nullable = false)
    @ColumnDefault("current_timestamp")
    @ApiModelProperty(value = "Time the user was created", required = true)
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
