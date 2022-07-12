package com.lyt.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @NotEmpty
    @Column(nullable = false)
    @ColumnDefault("''")
    private String name;

    //private String password;

    @Column(nullable = false)
    @ColumnDefault("current_timestamp")
    private Date dob;

    @Column(nullable = false)
    @ColumnDefault("''")
    private String address;

    @Column(nullable = false)
    @ColumnDefault("''")
    private String description;

    @Column(nullable = false)
    @ColumnDefault("current_timestamp")
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
