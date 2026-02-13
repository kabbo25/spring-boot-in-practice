# Adding API Gateway (Spring Cloud Gateway)

This guide explains how to add an API Gateway to the project when you're ready.

## What API Gateway Does

```
Without Gateway:
  Client → ch05 (localhost:8085)
  Client → ch06 (localhost:8086)

With Gateway:
  Client → Gateway (localhost:8080) → routes to ch05 or ch06
```

The gateway provides: single entry point, routing, load balancing, rate limiting, auth.

## Step 1: Create the Module

Create `api-gateway/` with this `pom.xml`:

```xml
<parent>
    <groupId>com.kabbo.sbip</groupId>
    <artifactId>spring-boot-in-practice</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</parent>

<artifactId>api-gateway</artifactId>

<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
</dependencies>
```

## Step 2: Application Class

```java
@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
```

## Step 3: Configuration

`application.properties`:

```properties
spring.application.name=api-gateway
server.port=8080

# Eureka
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# Routes — Gateway discovers services via Eureka and routes by name
spring.cloud.gateway.discovery.locator.enabled=true
spring.cloud.gateway.discovery.locator.lower-case-service-id=true
```

With `discovery.locator.enabled=true`, the gateway auto-creates routes for every
Eureka-registered service. You can call:

```
GET http://localhost:8080/ch05-securing-applications/api/info
GET http://localhost:8080/ch06-additional-security/api/discovery/call-ch05
```

## Step 4: Custom Routes (Optional)

For cleaner URLs, define explicit routes in `application.properties`:

```properties
# Route /ch05/** to ch05-securing-applications
spring.cloud.gateway.routes[0].id=ch05-route
spring.cloud.gateway.routes[0].uri=lb://ch05-securing-applications
spring.cloud.gateway.routes[0].predicates[0]=Path=/ch05/**
spring.cloud.gateway.routes[0].filters[0]=StripPrefix=1

# Route /ch06/** to ch06-additional-security
spring.cloud.gateway.routes[1].id=ch06-route
spring.cloud.gateway.routes[1].uri=lb://ch06-additional-security
spring.cloud.gateway.routes[1].predicates[0]=Path=/ch06/**
spring.cloud.gateway.routes[1].filters[0]=StripPrefix=1
```

Now you can call:
```
GET http://localhost:8080/ch05/api/info          → forwards to ch05:8085/api/info
GET http://localhost:8080/ch06/api/discovery/call-ch05  → forwards to ch06:8086/api/discovery/call-ch05
```

## Step 5: Add to Parent POM

Add `<module>api-gateway</module>` to the parent `pom.xml` modules list.

## Startup Order

1. `service-registry` (port 8761) — always first
2. `ch05-securing-applications` (port 8085)
3. `ch06-additional-security` (port 8086)
4. `api-gateway` (port 8080) — last, after services register

## Key Concepts

- `lb://` prefix means "load balanced" — Gateway uses Eureka to resolve the service name
- `StripPrefix=1` removes the first path segment (`/ch05` prefix) before forwarding
- Gateway is reactive (uses Netty, not Tomcat) — Spring WebFlux under the hood
- You can add filters for rate limiting, circuit breaking, auth, logging
