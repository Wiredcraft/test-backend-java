package me.solution.service.biz;

import me.solution.model.domain.User;
import me.solution.model.reqresp.UpdateProfileReq;
import me.solution.model.transfer.LoginUser;
import me.solution.service.UserService;
import me.solution.utils.LoginUtils;
import me.solution.utils.component.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * profile biz service
 *
 * @author davincix
 * @since 2023/5/22 02:18
 */
@Service
public class ProfileBizService {
    @Autowired
    private UserService userService;
    @Autowired
    private LoginBizService loginBizService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getProfileById(Long userId) {
        return userService.getUserById(userId);
    }

    public void updateProfile(Long userId, UpdateProfileReq req) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return;
        }

        user.setAddress(req.getAddress());
        user.setDescription(req.getDescription());
        userService.updateByUserId(user);

        LoginUser loginUser = LoginUtils.getLoginUserRequireNonNull();
        loginUser.setUser(user);
        redisCache.setCacheObject("login:" + userId, loginUser);
    }

    public void updatePasswd(Long userId, String passwd) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return;
        }

        String encodedPasswd = passwordEncoder.encode(passwd);
        user.setPasswd(encodedPasswd);
        userService.updateByUserId(user);

        LoginUser loginUser = LoginUtils.getLoginUserRequireNonNull();
        loginUser.setUser(user);
        redisCache.setCacheObject("login:" + userId, loginUser);
    }

    public void delAccount(Long userId) {
        userService.softDelById(userId);
        loginBizService.logoutById(userId);
    }
}
