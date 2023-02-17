package com.wiredcraft.testbackend.dao;

import com.wiredcraft.testbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author: yongchen
 * date: 2023/2/17
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
