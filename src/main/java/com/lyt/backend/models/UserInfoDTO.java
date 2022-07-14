package com.lyt.backend.models;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class UserInfoDTO {
    @ApiModelProperty(required = true)
    private Integer id;

    @ApiModelProperty(required = true, value = "Name of user")
    private String name;

    @ApiModelProperty(value = "Date of birth of user")
    private Date dob;

    @ApiModelProperty(value = "Address of user")
    private String address;

    @ApiModelProperty(value = "Description of user")
    private String description;

    @ApiModelProperty(value = "Time the user was created", required = true)
    private Date createdAt;

    @ApiModelProperty(value = "Time the record was modified", required = true)
    private Date updatedAt;

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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
