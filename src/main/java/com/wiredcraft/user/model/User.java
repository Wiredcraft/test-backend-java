package com.wiredcraft.user.model;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Table(name = "user")
@Entity
public class User {

    @Id
    private long id;

    private String name;

    private Date dob;

    private String address;

    private String description;

    private Date createdAt;

}
