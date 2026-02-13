package com.kabbo.sbip.ch06.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * Feign client that discovers ch05 via Eureka and calls its API.
 * "ch05-securing-applications" is the spring.application.name of ch05 —
 * Eureka resolves this to the actual host:port at runtime.
 */
@FeignClient(name = "ch05-securing-applications")
public interface Ch05Client {

    @GetMapping("/api/info")
    Map<String, Object> getInfo();
}
