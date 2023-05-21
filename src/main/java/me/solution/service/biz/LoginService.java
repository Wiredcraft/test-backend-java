package me.solution.service.biz;

import me.solution.model.domain.User;
import me.solution.model.reqresp.LoginReq;
import me.solution.model.reqresp.SignUpReq;
import me.solution.model.transfer.LoginUser;
import me.solution.service.UserService;
import me.solution.utils.JwtUtil;
import me.solution.utils.component.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * service for login/logout
 *
 * @author davincix
 * @since 2023/5/20 15:42
 */
@Service
public class LoginService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtil redisUtil;

    public void signUp(SignUpReq req) {
        String encodedPasswd = passwordEncoder.encode(req.getPasswd());

        User saver = User.builder()
                .name(req.getName())
                .passwd(encodedPasswd)
                .dob(req.getDob())
                .address(req.getAddress())
                .description(req.getDescription())
                .build();
        userService.saveUser(saver);
    }

    public String login(LoginReq req) {
        // invoke ProviderManager to check
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(req.getName(), req.getPasswd());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        Optional.ofNullable(authenticate)
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));

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
