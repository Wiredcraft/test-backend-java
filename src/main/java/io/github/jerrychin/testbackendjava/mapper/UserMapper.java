package io.github.jerrychin.testbackendjava.mapper;

import io.github.jerrychin.testbackendjava.dto.UserDTO;
import io.github.jerrychin.testbackendjava.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User entity);

    User userDTOtoUser(UserDTO dto);
}