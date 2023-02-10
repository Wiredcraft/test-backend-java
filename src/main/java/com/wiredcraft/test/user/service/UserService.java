package com.wiredcraft.test.user.service;

import com.wiredcraft.test.user.model.req.UserReq;
import com.wiredcraft.test.user.model.vo.UserVO;

import java.util.List;

/**
 * User Service
 */
public interface UserService {

    /**
     * List users
     *
     * @return set of UserVO
     */
    List<UserVO> list();

    /**
     * Get user by ID
     *
     * @param id user ID
     * @return UserVO
     */
    UserVO get(int id);

    /**
     * Create user
     *
     * @param userReq user form
     * @return
     */
    int create(UserReq userReq);

    /**
     * Update user
     *
     * @param userReq user form
     * @return
     */
    int update(UserReq userReq);

    /**
     * Delete user by ID
     *
     * @param id user ID
     * @return
     */
    boolean delete(int id);

    /**
     * Logic delete by user ID
     *
     * @param id user ID
     * @return
     */
    boolean logicDelete(int id);

}
