package com.wiredcraft.testbackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * description: User Model
 * author: yongchen
 * date: 2023/2/17
 */
@Entity
@Table(name = "user")
public class User implements Serializable {
    /**
     * user ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * user name
     */
    @Length(max = 50, message = "The length of name can not exceed 50")
    @NotNull(message = "name cannot be null")
    private String name;

    /**
     * date of birth
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    /**
     * user address
     */
    @Length(max = 100, message = "The length of address can not exceed 100")
    @NotNull(message = "address cannot be null")
    private String address;

    /**
     * user description
     */
    @Length(max = 255, message = "The length of description can not exceed 255")
    @NotNull(message = "description cannot be null")
    private String description;

    /**
     * user created date
     */
    private Date createdAt;

    /**
     * user updated date
     */
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
