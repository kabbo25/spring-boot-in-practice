//package com.kabbo.sbip.ch06.controller;
//
//import com.kabbo.sbip.ch06.client.Ch05Client;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/discovery")
//public class ServiceDiscoveryDemoController {
//
//    private final Ch05Client ch05Client;
//
//    public ServiceDiscoveryDemoController(Ch05Client ch05Client) {
//        this.ch05Client = ch05Client;
//    }
//
//    @GetMapping("/call-ch05")
//    public Map<String, Object> callCh05() {
//        Map<String, Object> ch05Response = ch05Client.getInfo();
//        return Map.of(
//                "caller", "ch06-additional-security",
//                "calledService", "ch05-securing-applications",
//                "discoveryMethod", "Eureka + OpenFeign",
//                "ch05Response", ch05Response
//        );
//    }
//}
