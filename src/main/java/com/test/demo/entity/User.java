package com.test.demo.entity;

import lombok.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangrucheng on 2023/5/19
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "description")
    private String description;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}
