package com.wiredcraft.user.repository;

import com.wiredcraft.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByName(String name);
}
