package io.github.jerrychin.testbackendjava.service;

import io.github.jerrychin.testbackendjava.model.dto.UserDTO;
import io.github.jerrychin.testbackendjava.model.vo.UserVO;
import io.github.jerrychin.testbackendjava.model.entity.User;
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

    public UserVO getUser(String id) {
        return userMapper.userToUserVO(repository.findById(id).orElseThrow(() ->
                new RestApiException(HttpStatus.BAD_REQUEST, "user not found! id " + id)));
    }

    public UserVO createUser(UserDTO userDTO) {
        User user = userMapper.userDTOtoUser(userDTO);
        user.setCreatedAt(LocalDateTime.now());
        return userMapper.userToUserVO(repository.save(user));
    }

    public UserVO updateUser(String id, UserDTO userDTO) {
        User user = repository.findById(id).orElseThrow(() ->
                new RestApiException(HttpStatus.BAD_REQUEST, "user not found! id " + id));

        userMapper.updateUserWithUserDTO(userDTO, user);
        return userMapper.userToUserVO(repository.save(user));
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }
}
