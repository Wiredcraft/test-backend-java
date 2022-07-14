package com.lyt.backend.models;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "user_personal_info_table")
public class UserPersonalInfo {
    //now assume it's self-incrementing int
    @Id
    private Integer id;

    @Column(nullable = false, columnDefinition = "timestamp")
    @ColumnDefault("current_timestamp")
    private Date dob;

    @Column(nullable = false)
    @ColumnDefault("''")
    private String address;

    @Column(nullable = false)
    @ColumnDefault("''")
    private String description;

    @Column(nullable = false, columnDefinition = "timestamp")
    @ColumnDefault("current_timestamp")
    private Date createdAt;

    @Column(nullable = false, columnDefinition = "timestamp")
    @ColumnDefault("current_timestamp")
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
