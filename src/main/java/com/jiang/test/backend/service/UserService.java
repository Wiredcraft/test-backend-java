package com.jiang.test.backend.service;

import com.jiang.test.backend.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(int id);

    User addUser(User user);

    User updateUser(User user);

    void deleteUserById(int id);
}
