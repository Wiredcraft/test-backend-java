package me.solution.service.biz;

import me.solution.common.converter.UserConverter;
import me.solution.common.utils.BizChecker;
import me.solution.model.domain.User;
import me.solution.model.reqresp.UpdateProfileReq;
import me.solution.model.reqresp.UserResp;
import me.solution.security.model.LoginUser;
import me.solution.security.utils.LoginUtil;
import me.solution.service.LoginCacheService;
import me.solution.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    private LoginCacheService loginCacheService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserConverter userConverter;

    public UserResp getProfileById(Long userId) {
        User user = userService.getUserById(userId, false);
        return Optional.ofNullable(user)
                .map(userConverter::model2Resp)
                .orElse(null);
    }

    public void updateProfile(Long userId, UpdateProfileReq req) {
        User user = userService.getUserById(userId, false);
        BizChecker.checkUserExist(user);

        user.setAddress(req.getAddress());
        user.setDescription(req.getDescription());
        userService.updateByUserId(user);

        LoginUser loginUser = LoginUtil.getLoginUserRequireNonNull();
        loginUser.setUser(user);
        loginCacheService.setLoginCache(userId, loginUser);
    }

    public void updatePasswd(Long userId, String passwd) {
        User user = userService.getUserById(userId, false);
        BizChecker.checkUserExist(user);

        // TODO: 2023/5/22 email/sms check

        String encodedPasswd = passwordEncoder.encode(passwd);
        user.setPasswd(encodedPasswd);
        userService.updateByUserId(user);

        LoginUser loginUser = LoginUtil.getLoginUserRequireNonNull();
        loginUser.setUser(user);
        loginCacheService.setLoginCache(userId, loginUser);
    }

    public void delAccount(Long userId) {
        userService.softDelById(userId);
        loginBizService.logoutById(userId);
    }
}
