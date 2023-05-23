package me.solution.endpoint;

import me.solution.common.annotations.NonToken;
import me.solution.model.reqresp.ResultResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * a simple health check endpoint for web
 *
 * @author davincix
 * @since 2023/5/20 12:46
 */
@RestController
@RequestMapping("/healthcheck")
public class HealthCheckEndpoint {

    @NonToken
    @GetMapping("/ping")
    public ResultResp<String> ping() {
        return ResultResp.successData("pong, roger that, i'm alive!");
    }
}
