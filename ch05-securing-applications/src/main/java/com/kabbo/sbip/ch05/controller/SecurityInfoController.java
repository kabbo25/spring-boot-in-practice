package com.kabbo.sbip.ch05.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SecurityInfoController {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private int port;

    @GetMapping("/info")
    public Map<String, Object> getInfo() {
        return Map.of(
                "service", appName,
                "port", port,
                "status", "UP",
                "message", "Hello from Ch05 — Securing Spring Boot Applications!"
        );
    }
}
