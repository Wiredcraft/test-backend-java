package com.test.demo.mapper;

import com.test.demo.entity.UserDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * The interface User mapper.
 *
 * @author zhangrucheng on 2023/5/19
 */
@Mapper
public interface UserMapper {

    /**
     * Add user int.
     *
     * @param userDo the user
     * @return the int
     */
    int addUser(UserDo userDo);

    /**
     * Select user by name and password optional.
     *
     * @param name     the name
     * @param password the password
     * @return the optional
     */
    Optional<UserDo> selectUserByNameAndPassword(String name, String password);


    /**
     * Select user by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<UserDo> selectUserById(int id);


    /**
     * Select user by condition list.
     *
     * @param userDo the user
     * @return the list
     */
    List<UserDo> selectUserByCondition(UserDo userDo);

    /**
     * Update user.
     *
     * @param userDo the user
     */
    void updateUser(UserDo userDo);

    /**
     * Delete user by id int.
     *
     * @param id the id
     * @return the int
     */
    int deleteUserById(Integer id);
}
