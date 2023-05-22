package me.solution.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.solution.annotations.NonToken;
import me.solution.model.reqresp.LoginReq;
import me.solution.model.reqresp.ResultResp;
import me.solution.model.reqresp.SignUpReq;
import me.solution.service.biz.LoginBizService;
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
    private LoginBizService loginBizService;

    /**
     * sign up user
     *
     * @param req
     * @return token
     */
    @NonToken
    @ApiOperation("signup")
    @PostMapping("/signUp")
    public ResultResp<String> signUp(@RequestBody SignUpReq req) {
        String token = loginBizService.signUp(req);
        return ResultResp.successData(token);
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
    public ResultResp<String> signIn(@RequestBody LoginReq req) {
        String jwtToken = loginBizService.login(req.getName(), req.getPasswd());
        return ResultResp.successData(jwtToken);
    }

    /**
     * logout
     *
     * @return
     */
    @ApiOperation("logout")
    @PostMapping("/logout")
    public ResultResp<Void> logout() {
        loginBizService.logout();
        return ResultResp.success();
    }
}
