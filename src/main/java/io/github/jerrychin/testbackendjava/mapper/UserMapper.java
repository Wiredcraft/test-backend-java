package io.github.jerrychin.testbackendjava.mapper;

import io.github.jerrychin.testbackendjava.dto.UserDTO;
import io.github.jerrychin.testbackendjava.dto.UserVO;
import io.github.jerrychin.testbackendjava.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    void updateUserWithUserDTO(UserDTO updateUser, @MappingTarget User user);

    User userDTOtoUser(UserDTO dto);

    UserVO userToUserVO(User entity);

}