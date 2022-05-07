package io.github.jerrychin.testbackendjava.service;

import io.github.jerrychin.testbackendjava.dto.UserDTO;
import io.github.jerrychin.testbackendjava.entity.User;
import io.github.jerrychin.testbackendjava.exception.RestApiException;
import io.github.jerrychin.testbackendjava.mapper.UserMapper;
import io.github.jerrychin.testbackendjava.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserService {
    private final UserMapper userMapper;

    private final UserRepository repository;

    public UserService(UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    public UserDTO getUser(String id) {
        return userMapper.userToUserDTO(repository.findById(id).orElseThrow(() ->
                new RestApiException(HttpStatus.BAD_REQUEST, "user not found! id " + id)));
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User entity = userMapper.userDTOtoUser(userDTO);
        entity.setCreatedAt(LocalDateTime.now());
        return userMapper.userToUserDTO(repository.save(entity));
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }
}
