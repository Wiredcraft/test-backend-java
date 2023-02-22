package com.wiredcraft.testbackend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * users who follow you
 * author: yongchen
 * date: 2023/2/18
 */
@Entity
@Table(name = "fan")
public class Fan {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * current user Id
     */
    private Long userId;

    /**
     * fan user Id
     */
    private Long fanUserId;

    /**
     * user created date
     */
    private Timestamp createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFanUserId() {
        return fanUserId;
    }

    public void setFanUserId(Long fanUserId) {
        this.fanUserId = fanUserId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
