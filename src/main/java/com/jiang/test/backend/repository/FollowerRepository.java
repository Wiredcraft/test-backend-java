package com.jiang.test.backend.repository;

import com.jiang.test.backend.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Integer> {

    List<Follower> findByUserId(int userId);

    Optional<Follower> findByUserIdAndFollowerId(int userId, int followerId);


}
