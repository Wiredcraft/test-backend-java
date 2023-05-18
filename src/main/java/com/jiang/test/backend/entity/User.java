package com.jiang.test.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tbl_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Column(name = "id")
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

}
