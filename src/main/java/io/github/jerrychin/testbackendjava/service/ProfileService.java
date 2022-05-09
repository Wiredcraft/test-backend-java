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

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service
public class ProfileService {
    private final UserMapper userMapper;

    private final UserRepository repository;

    public ProfileService(UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    public UserVO getProfile(Long id) {
        return userMapper.userToUserVO(repository.findUserByAccountId(id).orElseThrow(() ->
                new RestApiException(HttpStatus.BAD_REQUEST, "user not found! id " + id)));
    }

    public UserVO updateProfile(Long id, UserDTO userDTO) {
        User user = repository.findUserByAccountId(id).orElseThrow(() ->
                new RestApiException(HttpStatus.BAD_REQUEST, "user not found! id " + id));

        userMapper.updateUserWithUserDTO(userDTO, user);
        return userMapper.userToUserVO(repository.save(user));
    }

    @Transactional
    public void createProfile(Long id, UserDTO userDTO) {
        User user = userMapper.userDTOtoUser(userDTO);
        user.setCreatedAt(LocalDateTime.now());
        user.setAccountId(id);
        repository.save(user);
    }
}