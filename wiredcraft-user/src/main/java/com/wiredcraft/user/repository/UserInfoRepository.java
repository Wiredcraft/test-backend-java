package com.wiredcraft.user.repository;

import com.wiredcraft.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface UserInfoRepository extends JpaRepository<UserInfo,Integer>, Serializable {
    UserInfo findByUuid(String uuid);
}
