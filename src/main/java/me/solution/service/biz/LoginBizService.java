package me.solution.service.biz;

import me.solution.common.enums.ResultCodeEnum;
import me.solution.common.utils.BizChecker;
import me.solution.common.exception.BizException;
import me.solution.model.domain.User;
import me.solution.model.reqresp.SignUpReq;
import me.solution.security.model.LoginUser;
import me.solution.service.UserService;
import me.solution.security.utils.JwtUtil;
import me.solution.common.utils.component.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * service for login/logout
 *
 * @author davincix
 * @since 2023/5/20 15:42
 */
@Service
public class LoginBizService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtil redisUtil;

    public String signUp(SignUpReq req) {
        String name = req.getName();
        String passwd = req.getPasswd();
        String encodedPasswd = passwordEncoder.encode(passwd);

        User existUser = userService.getUserByName(name, false);
        Optional.ofNullable(existUser)
                .ifPresent(x -> {
                    BizChecker.checkUserNameTaken(Objects.equals(x.getName(), name));
                });

        User saver = User.builder()
                .name(name)
                .passwd(encodedPasswd)
                .dob(req.getDob())
                .address(req.getAddress())
                .description(req.getDescription())
                .build();
        userService.saveUser(saver);

        return login(name, passwd);
    }

    public String login(String name, String passwd) {
        // invoke ProviderManager to check
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(name, passwd);
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            throw new BizException(ResultCodeEnum.INCORRECT_NAME_OR_PASSWD);
        }
        Optional.ofNullable(authenticate)
                .orElseThrow(() -> new BizException(ResultCodeEnum.INCORRECT_NAME_OR_PASSWD));

        // generate jwt token, store the token to redis
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        redisUtil.set("login:" + userId, loginUser);

        return jwt;
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        logoutById(userId);
    }

    public void logoutById(Long userId) {
        redisUtil.del("login:" + userId);
    }
}
