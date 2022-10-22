package com.craig.user.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserModel {
    private Long id;
    
    private String name;
    
    @JsonFormat(pattern =  "yyyy-MM-dd")
    private Date dob;

    private String address;

    private String description;

    @JsonFormat(pattern =  "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
}
