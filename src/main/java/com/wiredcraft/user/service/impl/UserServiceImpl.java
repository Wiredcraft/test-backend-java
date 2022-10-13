package com.wiredcraft.user.service.impl;

import com.wiredcraft.user.model.User;
import com.wiredcraft.user.repository.UserRepository;
import com.wiredcraft.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void createUser(User user) {
        user.setCreatedAt(new Date());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);

    }

    @Override
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);

    }
}
