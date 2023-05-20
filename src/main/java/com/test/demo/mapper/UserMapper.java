package com.test.demo.mapper;

import com.test.demo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface User mapper.
 *
 * @author zhangrucheng on 2023/5/19
 */
@Repository
public interface UserMapper {

    /**
     * Add user int.
     *
     * @param user the user
     * @return the int
     */
    int addUser(User user);

    /**
     * Select user by name and password optional.
     *
     * @param name     the name
     * @param password the password
     * @return the optional
     */
    Optional<User> selectUserByNameAndPassword(String name, String password);


    /**
     * Select user by condition list.
     *
     * @param user the user
     * @return the list
     */
    List<User> selectUserByCondition(User user);

    /**
     * Update user.
     *
     * @param user the user
     */
    void updateUser(User user);

    /**
     * Delete user by id int.
     *
     * @param id the id
     * @return the int
     */
    int deleteUserById(Integer id);
}
