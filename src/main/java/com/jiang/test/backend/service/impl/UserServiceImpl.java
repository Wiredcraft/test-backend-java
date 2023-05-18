package com.jiang.test.backend.service.impl;

import com.jiang.test.backend.entity.User;
import com.jiang.test.backend.repository.UserRepository;
import com.jiang.test.backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserRepository userRepository;

    public Optional<User> getUserById(int id){
        logger.info("User get: {}", id);
        return userRepository.findById(id);
    }

    @Override
    public User addUser(User user) {
        User saveUser = userRepository.save(user);
        logger.info("User saved: {}", user);
        return saveUser;
    }

    @Override
    public User updateUser(User user) {
        User updateUser = userRepository.save(user);
        logger.info("User updated: {}", updateUser);
        return updateUser;
    }

    @Override
    public void deleteUserById(int id) {
        logger.info("User deleted: {}", id);
        userRepository.deleteById(id);
    }


}
