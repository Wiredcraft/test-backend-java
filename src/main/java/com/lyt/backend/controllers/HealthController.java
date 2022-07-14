package com.lyt.backend.controllers;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(
        tags = {"health check"}
)
@RestController
public class HealthController {
    @RequestMapping(value = "/health", method = {RequestMethod.GET})
    public ResponseEntity health() {
        return ResponseEntity.ok().build();
    }
}
