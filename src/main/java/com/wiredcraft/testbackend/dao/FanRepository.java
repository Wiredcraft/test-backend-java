package com.wiredcraft.testbackend.dao;

import com.wiredcraft.testbackend.entity.Fan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * author: yongchen
 * date: 2023/2/17
 */
public interface FanRepository extends JpaRepository<Fan, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from Fan where user_id =?1 and fan_user_id=?2", nativeQuery = true)
    int deleteByUserIdAndFanUserId(Long userId, Long fanUserId);

}
