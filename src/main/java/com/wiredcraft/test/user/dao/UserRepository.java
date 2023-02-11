package com.wiredcraft.test.user.dao;

import com.wiredcraft.test.user.model.po.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserPO,Integer> {
}
