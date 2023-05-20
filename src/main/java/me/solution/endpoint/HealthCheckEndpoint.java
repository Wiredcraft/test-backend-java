package me.solution.endpoint;

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
    @GetMapping("/ping")
    public String ping() {
        return "pong, roger that, i'm alive!";
    }

}
