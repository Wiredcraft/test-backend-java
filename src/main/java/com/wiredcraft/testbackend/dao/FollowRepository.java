package com.wiredcraft.testbackend.dao;

import com.wiredcraft.testbackend.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * author: yongchen
 * date: 2023/2/17
 */
public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from follow where user_id =?1 and follow_user_id=?2", nativeQuery = true)
    int deleteByUserIdAndFollowUserId(Long userId, Long followUserId);
}
