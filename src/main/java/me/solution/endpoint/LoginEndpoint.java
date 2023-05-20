package me.solution.endpoint;

import me.solution.model.reqresp.LoginReq;
import me.solution.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * login/logout endpoint
 *
 * @author davincix
 * @since 2023/5/20 15:22
 */
@RestController
@RequestMapping("/auth")
public class LoginEndpoint {
    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String ping(@RequestBody LoginReq req) {
        loginService.login(req);
        return null;
    }
}
