package com.wiredcraft.user.service;

import com.wiredcraft.user.model.User;

import java.util.Optional;

public interface UserService {

    void createUser(User user);

    void deleteUser(long id);

    void updateUser(User user);

    Optional<User> getUserById(long id);
}
