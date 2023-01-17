package com.wiredcraft.user.tiny.modules.ums.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wiredcraft.user.tiny.modules.ums.dto.UserParam;
import com.wiredcraft.user.tiny.modules.ums.dto.UpdateAdminPasswordParam;
import com.wiredcraft.user.tiny.modules.ums.model.User;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserService extends IService<User> {
    /**
     * getUserByUsername
     */
    User getUserByUsername(String username);
    /**
     * report location
     */
    void reportLocation(String username, Double x, Double y);

    Boolean exist(String username);

    /**
     * register
     */
    User register(UserParam userParam);

    /**
     * login feature
     * @param username
     * @param password
     * @return generate token
     */
    String login(String username,String password);

    /**
     * refresh token
     * @param oldToken
     */
    String refreshToken(String oldToken);

    /**
     * Paging queries for users by user name or nickname
     */
    Page<User> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     */
    boolean update(Long id, User admin);

    /**
     * 删除指定用户
     */
    boolean delete(Long id);



    /**
     * change password
     */
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);


    UserDetails loadUserByUsername(String username);
}
