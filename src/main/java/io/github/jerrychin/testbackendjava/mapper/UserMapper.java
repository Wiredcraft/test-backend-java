package io.github.jerrychin.testbackendjava.mapper;

import io.github.jerrychin.testbackendjava.dto.PeopleVO;
import io.github.jerrychin.testbackendjava.dto.UserDTO;
import io.github.jerrychin.testbackendjava.dto.UserVO;
import io.github.jerrychin.testbackendjava.entity.User;
import io.github.jerrychin.testbackendjava.util.Coordinate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    void updateUserWithUserDTO(UserDTO updateUser, @MappingTarget User user);

    User userDTOtoUser(UserDTO dto);

    UserVO userToUserVO(User entity);

    UserDTO userToUserDTO(User user);

    default PeopleVO userToPeopleVO(User user, Coordinate currentCoordinate) {
        PeopleVO peopleVO = new PeopleVO();
        peopleVO.setId(user.getAccountId());
        peopleVO.setName(user.getName());

        Coordinate userCoordinate = extractUserCoordinate(user);
        if(currentCoordinate != null && userCoordinate != null) {
            peopleVO.setDistanceInMeters(Coordinate.dBetween(currentCoordinate, userCoordinate));
        }

        return peopleVO;
    }

    default Coordinate extractUserCoordinate(User user) {
        if(user.getLatitude() != null && user.getLongitude() != null) {
            return new Coordinate(user.getLatitude(), user.getLongitude());
        }

        return null;
    }

}