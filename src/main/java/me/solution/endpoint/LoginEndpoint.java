package me.solution.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.solution.annotations.NonToken;
import me.solution.model.reqresp.LoginReq;
import me.solution.model.reqresp.SignUpReq;
import me.solution.service.biz.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * login/logout endpoint
 *
 * @author davincix
 * @since 2023/5/20 15:22
 */
@Api(tags = "login endpoint")
@RestController
@RequestMapping("/user")
public class LoginEndpoint {
    @Autowired
    private LoginService loginService;

    /**
     * sign up user
     *
     * @param req
     * @return
     */

    @NonToken
    @ApiOperation("signup")
    @PostMapping("/signUp")
    public String signUp(@RequestBody SignUpReq req) {
        loginService.signUp(req);
        return null;
    }

    /**
     * login by username and password
     *
     * @param req
     * @return
     */
    @NonToken
    @ApiOperation("login")
    @PostMapping("/login")
    public String signIn(@RequestBody LoginReq req) {
        String jwtToken = loginService.login(req);
        return jwtToken;
    }

    /**
     * logout
     *
     * @return
     */
    @ApiOperation("logout")
    @PostMapping("/logout")
    public void logout() {
        loginService.logout();
    }
}
