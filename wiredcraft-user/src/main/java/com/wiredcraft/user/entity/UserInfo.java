package com.wiredcraft.user.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author jeremy.zhang
 * @date 2022-10-19
 */
@Data
@Entity
@Table(name = "user_info")
@Where(clause = "is_deleted = 0 ")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String name;

    private LocalDateTime dob;

    private String address;
    private String description;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean isDeleted;


    @PreUpdate
    public void preUpdate() {
        if (Objects.isNull(this.updatedDate)) {
            this.updatedDate = LocalDateTime.now();
        }
        if (StringUtils.isEmpty(this.updatedBy)) {
            this.updatedBy = "-1";
        }
    }

    @PrePersist
    public void prePersist() {
        if (Objects.isNull(this.createdDate)) {
            this.createdDate = LocalDateTime.now();
            this.updatedDate = LocalDateTime.now();
        }
        if (StringUtils.isEmpty(this.createdBy)) {
            this.updatedBy = "-1";
            this.createdBy = "-1";
        }
        if (Objects.isNull(this.isDeleted)) {
            this.isDeleted = false;
        }
    }
}
