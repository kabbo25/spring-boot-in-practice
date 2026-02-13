# Spring Boot in Practice — Chapter 6 Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Build a Maven multi-module project for "Spring Boot in Practice" with Chapter 6 (Implementing additional security with Spring Security) as the fully implemented module, and Chapters 1-5 as stubs.

**Architecture:** Maven aggregator parent POM inheriting from `spring-boot-starter-parent:4.0.2`. Each chapter is a separate Maven module with its own git repository. Chapter 6 uses feature branches per book technique (6.1 through 6.10), each building an independent Spring Security feature.

**Tech Stack:** Java 21, Spring Boot 4.0.2, Spring Security 7.x, Spring Data JPA, H2, Thymeleaf, Lombok, Maven, JUnit 5 + Spring Security Test

---

## Task 1: Create Parent Aggregator POM

**Files:**
- Create: `spring-boot-in-practice/pom.xml`

**Step 1: Create the parent POM**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>4.0.2</version>
        <relativePath/>
    </parent>

    <groupId>com.kabbo.sbip</groupId>
    <artifactId>spring-boot-in-practice</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    <name>Spring Boot in Practice</name>
    <description>Learning project for Spring Boot in Practice by Somnath Musib</description>

    <modules>
        <module>ch01-booting-spring-boot</module>
        <module>ch02-common-tasks</module>
        <module>ch03-database-access</module>
        <module>ch04-autoconfiguration-actuator</module>
        <module>ch05-securing-applications</module>
        <module>ch06-additional-security</module>
    </modules>

    <properties>
        <java.version>21</java.version>
    </properties>

    <dependencies>
        <!-- Shared across all chapters -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

**Step 2: Create shared .gitignore at root level**

Create `spring-boot-in-practice/.gitignore`:

```
# Maven
target/
!.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

# IDE
.idea/
*.iml
*.iws
.project
.classpath
.settings/
.vscode/
*.swp
*.swo

# OS
.DS_Store
Thumbs.db

# Build
*.jar
*.war
*.class
*.log
```

**Step 3: Verify parent POM is valid**

Run: `mvn help:effective-pom -N -f spring-boot-in-practice/pom.xml 2>&1 | head -5`
Expected: XML output showing the effective POM (will fail on modules until they exist, that's OK)

---

## Task 2: Create Chapter 1-5 Stubs

Each stub follows an identical pattern. Create all 5 in one go.

**Files (per chapter):**
- Create: `chXX-<name>/pom.xml`
- Create: `chXX-<name>/src/main/java/com/kabbo/sbip/chXX/ChXXApplication.java`
- Create: `chXX-<name>/src/main/resources/application.properties`
- Create: `chXX-<name>/src/test/java/com/kabbo/sbip/chXX/ChXXApplicationTests.java`

**Step 1: Create ch01 stub**

`ch01-booting-spring-boot/pom.xml`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.kabbo.sbip</groupId>
        <artifactId>spring-boot-in-practice</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <relativePath>../pom.xml</relativePath>
    </parent>

    <artifactId>ch01-booting-spring-boot</artifactId>
    <name>Ch01 - Booting Spring Boot</name>
    <description>Chapter 1: Booting Spring Boot</description>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
</project>
```

`ch01-booting-spring-boot/src/main/java/com/kabbo/sbip/ch01/Ch01Application.java`:
```java
package com.kabbo.sbip.ch01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ch01Application {

    public static void main(String[] args) {
        SpringApplication.run(Ch01Application.class, args);
    }
}
```

`ch01-booting-spring-boot/src/main/resources/application.properties`:
```properties
spring.application.name=ch01-booting-spring-boot
server.port=8081
```

`ch01-booting-spring-boot/src/test/java/com/kabbo/sbip/ch01/Ch01ApplicationTests.java`:
```java
package com.kabbo.sbip.ch01;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Ch01ApplicationTests {

    @Test
    void contextLoads() {
    }
}
```

**Step 2: Create ch02-ch05 stubs following same pattern**

Repeat the ch01 pattern with these variations:

| Chapter | artifactId | Package | Port | Dependencies |
|---------|-----------|---------|------|-------------|
| ch02 | `ch02-common-tasks` | `com.kabbo.sbip.ch02` | 8082 | `spring-boot-starter-web` |
| ch03 | `ch03-database-access` | `com.kabbo.sbip.ch03` | 8083 | `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `h2` (runtime) |
| ch04 | `ch04-autoconfiguration-actuator` | `com.kabbo.sbip.ch04` | 8084 | `spring-boot-starter-web`, `spring-boot-starter-actuator` |
| ch05 | `ch05-securing-applications` | `com.kabbo.sbip.ch05` | 8085 | `spring-boot-starter-web`, `spring-boot-starter-security` |

Each gets: `pom.xml`, `ChXXApplication.java`, `application.properties`, `ChXXApplicationTests.java`.

**Step 3: Init git repos for ch01-ch05**

For each chapter directory:
```bash
cd ch01-booting-spring-boot && git init && git add . && git commit -m "ch01: initial stub"
cd ../ch02-common-tasks && git init && git add . && git commit -m "ch02: initial stub"
cd ../ch03-database-access && git init && git add . && git commit -m "ch03: initial stub"
cd ../ch04-autoconfiguration-actuator && git init && git add . && git commit -m "ch04: initial stub"
cd ../ch05-securing-applications && git init && git add . && git commit -m "ch05: initial stub"
```

**Step 4: Verify full build from parent**

Run: `mvn clean package -f spring-boot-in-practice/pom.xml`
Expected: BUILD SUCCESS for all 6 modules (ch06 doesn't exist yet, so this will fail — that's OK, create ch06 next)

---

## Task 3: Create Chapter 6 Base Project (main branch)

This is the foundation. The `main` branch has: Spring Boot app + Spring Security form login + Thymeleaf login/home pages + H2 database + in-memory user.

**Files:**
- Create: `ch06-additional-security/pom.xml`
- Create: `ch06-additional-security/src/main/java/com/kabbo/sbip/ch06/Ch06Application.java`
- Create: `ch06-additional-security/src/main/java/com/kabbo/sbip/ch06/config/SecurityConfig.java`
- Create: `ch06-additional-security/src/main/java/com/kabbo/sbip/ch06/controller/HomeController.java`
- Create: `ch06-additional-security/src/main/resources/application.properties`
- Create: `ch06-additional-security/src/main/resources/templates/login.html`
- Create: `ch06-additional-security/src/main/resources/templates/home.html`
- Create: `ch06-additional-security/src/main/resources/templates/dashboard.html`
- Test: `ch06-additional-security/src/test/java/com/kabbo/sbip/ch06/Ch06ApplicationTests.java`
- Test: `ch06-additional-security/src/test/java/com/kabbo/sbip/ch06/SecurityConfigTests.java`

**Step 1: Create ch06 pom.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.kabbo.sbip</groupId>
        <artifactId>spring-boot-in-practice</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <relativePath>../pom.xml</relativePath>
    </parent>

    <artifactId>ch06-additional-security</artifactId>
    <name>Ch06 - Implementing Additional Security</name>
    <description>Chapter 6: Implementing additional security with Spring Security</description>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity6</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <!-- Database -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Test -->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

**Step 2: Write the failing security integration test**

`ch06-additional-security/src/test/java/com/kabbo/sbip/ch06/SecurityConfigTests.java`:
```java
package com.kabbo.sbip.ch06;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void unauthenticatedUserRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void loginPageIsAccessibleWithoutAuth() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    void homePageIsAccessibleWithoutAuth() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}
```

**Step 3: Run test to verify it fails**

Run: `mvn test -pl ch06-additional-security -f spring-boot-in-practice/pom.xml`
Expected: FAIL — classes don't exist yet

**Step 4: Create the Application class**

`ch06-additional-security/src/main/java/com/kabbo/sbip/ch06/Ch06Application.java`:
```java
package com.kabbo.sbip.ch06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ch06Application {

    public static void main(String[] args) {
        SpringApplication.run(Ch06Application.class, args);
    }
}
```

**Step 5: Create SecurityConfig**

`ch06-additional-security/src/main/java/com/kabbo/sbip/ch06/config/SecurityConfig.java`:
```java
package com.kabbo.sbip.ch06.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

**Step 6: Create HomeController**

`ch06-additional-security/src/main/java/com/kabbo/sbip/ch06/controller/HomeController.java`:
```java
package com.kabbo.sbip.ch06.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
```

**Step 7: Create Thymeleaf templates**

`ch06-additional-security/src/main/resources/templates/home.html`:
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Spring Boot in Practice - Ch06</title>
</head>
<body>
    <h1>Spring Boot in Practice — Chapter 6</h1>
    <p>Implementing additional security with Spring Security</p>
    <a th:href="@{/login}">Login</a> |
    <a th:href="@{/dashboard}">Dashboard</a>
</body>
</html>
```

`ch06-additional-security/src/main/resources/templates/login.html`:
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <h1>Login</h1>
    <div th:if="${param.error}" style="color:red;">Invalid username or password.</div>
    <div th:if="${param.logout}" style="color:green;">You have been logged out.</div>
    <form th:action="@{/login}" method="post">
        <div>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required autofocus/>
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required/>
        </div>
        <button type="submit">Sign In</button>
    </form>
    <a th:href="@{/}">Back to Home</a>
</body>
</html>
```

`ch06-additional-security/src/main/resources/templates/dashboard.html`:
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
</head>
<body>
    <h1>Dashboard</h1>
    <p>Welcome, <span sec:authentication="name">User</span>!</p>
    <p>Roles: <span sec:authentication="authorities">ROLE_USER</span></p>
    <form th:action="@{/logout}" method="post">
        <button type="submit">Logout</button>
    </form>
</body>
</html>
```

**Step 8: Create application.properties**

`ch06-additional-security/src/main/resources/application.properties`:
```properties
spring.application.name=ch06-additional-security
server.port=8086

# H2 Database
spring.datasource.url=jdbc:h2:mem:ch06db
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# H2 Console (dev only)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Thymeleaf
spring.thymeleaf.cache=false
```

**Step 9: Create basic context test**

`ch06-additional-security/src/test/java/com/kabbo/sbip/ch06/Ch06ApplicationTests.java`:
```java
package com.kabbo.sbip.ch06;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Ch06ApplicationTests {

    @Test
    void contextLoads() {
    }
}
```

**Step 10: Run tests to verify they pass**

Run: `mvn test -pl ch06-additional-security -f spring-boot-in-practice/pom.xml`
Expected: All 4 tests PASS (contextLoads + 3 security tests)

**Step 11: Verify full build from parent**

Run: `mvn clean package -f spring-boot-in-practice/pom.xml`
Expected: BUILD SUCCESS for all 6 modules

**Step 12: Init git repo and commit for ch06**

```bash
cd spring-boot-in-practice/ch06-additional-security
git init
git add .
git commit -m "ch06: base setup with Spring Security form login"
```

---

## Task 4: Branch 6.3 — User Registration

Branch from `main`. Adds: User + Role JPA entities, custom `UserDetailsService` backed by DB, registration form + controller.

**Branch:** `6.3-user-registration` (from `main`)

**Files:**
- Create: `src/main/java/com/kabbo/sbip/ch06/model/User.java`
- Create: `src/main/java/com/kabbo/sbip/ch06/model/Role.java`
- Create: `src/main/java/com/kabbo/sbip/ch06/repository/UserRepository.java`
- Create: `src/main/java/com/kabbo/sbip/ch06/dto/UserRegistrationDto.java`
- Create: `src/main/java/com/kabbo/sbip/ch06/service/CustomUserDetailsService.java`
- Create: `src/main/java/com/kabbo/sbip/ch06/service/UserService.java`
- Create: `src/main/java/com/kabbo/sbip/ch06/controller/RegistrationController.java`
- Create: `src/main/resources/templates/register.html`
- Modify: `src/main/java/com/kabbo/sbip/ch06/config/SecurityConfig.java` (switch to DB-backed UserDetailsService)
- Test: `src/test/java/com/kabbo/sbip/ch06/service/UserServiceTests.java`
- Test: `src/test/java/com/kabbo/sbip/ch06/controller/RegistrationControllerTests.java`

**Step 1: Create branch**

```bash
cd spring-boot-in-practice/ch06-additional-security
git checkout -b 6.3-user-registration
```

**Step 2: Write failing test for UserService**

`src/test/java/com/kabbo/sbip/ch06/service/UserServiceTests.java`:
```java
package com.kabbo.sbip.ch06.service;

import com.kabbo.sbip.ch06.dto.UserRegistrationDto;
import com.kabbo.sbip.ch06.model.User;
import com.kabbo.sbip.ch06.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void registerNewUser_savesUserWithEncodedPassword() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setUsername("testuser");
        dto.setEmail("test@example.com");
        dto.setPassword("password123");
        dto.setConfirmPassword("password123");

        User saved = userService.registerNewUser(dto);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUsername()).isEqualTo("testuser");
        assertThat(saved.getPassword()).isNotEqualTo("password123"); // BCrypt encoded
        assertThat(saved.isEnabled()).isTrue();
    }

    @Test
    void registerDuplicateUsername_throwsException() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setUsername("duplicate");
        dto.setEmail("first@example.com");
        dto.setPassword("password123");
        dto.setConfirmPassword("password123");
        userService.registerNewUser(dto);

        UserRegistrationDto dto2 = new UserRegistrationDto();
        dto2.setUsername("duplicate");
        dto2.setEmail("second@example.com");
        dto2.setPassword("password123");
        dto2.setConfirmPassword("password123");

        assertThatThrownBy(() -> userService.registerNewUser(dto2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");
    }
}
```

**Step 3: Run test to verify it fails**

Run: `mvn test -pl ch06-additional-security -f spring-boot-in-practice/pom.xml -Dtest=UserServiceTests`
Expected: FAIL — classes don't exist

**Step 4: Create User entity**

`src/main/java/com/kabbo/sbip/ch06/model/User.java`:
```java
package com.kabbo.sbip.ch06.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
```

**Step 5: Create Role entity**

`src/main/java/com/kabbo/sbip/ch06/model/Role.java`:
```java
package com.kabbo.sbip.ch06.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
```

**Step 6: Create UserRepository**

`src/main/java/com/kabbo/sbip/ch06/repository/UserRepository.java`:
```java
package com.kabbo.sbip.ch06.repository;

import com.kabbo.sbip.ch06.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
```

**Step 7: Create UserRegistrationDto**

`src/main/java/com/kabbo/sbip/ch06/dto/UserRegistrationDto.java`:
```java
package com.kabbo.sbip.ch06.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDto {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;
}
```

**Step 8: Create UserService**

`src/main/java/com/kabbo/sbip/ch06/service/UserService.java`:
```java
package com.kabbo.sbip.ch06.service;

import com.kabbo.sbip.ch06.dto.UserRegistrationDto;
import com.kabbo.sbip.ch06.model.Role;
import com.kabbo.sbip.ch06.model.User;
import com.kabbo.sbip.ch06.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerNewUser(UserRegistrationDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + dto.getUsername());
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + dto.getEmail());
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        user.setRoles(Set.of(new Role("ROLE_USER")));

        return userRepository.save(user);
    }
}
```

**Step 9: Create CustomUserDetailsService**

`src/main/java/com/kabbo/sbip/ch06/service/CustomUserDetailsService.java`:
```java
package com.kabbo.sbip.ch06.service;

import com.kabbo.sbip.ch06.model.User;
import com.kabbo.sbip.ch06.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.isEnabled())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .toList())
                .build();
    }
}
```

**Step 10: Update SecurityConfig to use DB-backed auth and allow /register**

Replace the `SecurityConfig.java` — remove the `InMemoryUserDetailsManager` bean (the `CustomUserDetailsService` @Service automatically becomes the `UserDetailsService`), and add `/register` to permitted paths:

```java
package com.kabbo.sbip.ch06.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login", "/register", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

**Step 11: Create RegistrationController**

`src/main/java/com/kabbo/sbip/ch06/controller/RegistrationController.java`:
```java
package com.kabbo.sbip.ch06.controller;

import com.kabbo.sbip.ch06.dto.UserRegistrationDto;
import com.kabbo.sbip.ch06.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto dto,
                               BindingResult result, Model model) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.user", "Passwords do not match");
        }
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userService.registerNewUser(dto);
            return "redirect:/login?registered";
        } catch (IllegalArgumentException e) {
            model.addAttribute("registrationError", e.getMessage());
            return "register";
        }
    }
}
```

**Step 12: Create register.html template**

`src/main/resources/templates/register.html`:
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>
    <h1>Create Account</h1>
    <div th:if="${registrationError}" style="color:red;" th:text="${registrationError}"></div>
    <form th:action="@{/register}" th:object="${user}" method="post">
        <div>
            <label for="username">Username:</label>
            <input type="text" id="username" th:field="*{username}" required/>
            <span th:if="${#fields.hasErrors('username')}" th:errors="*{username}" style="color:red;"></span>
        </div>
        <div>
            <label for="email">Email:</label>
            <input type="email" id="email" th:field="*{email}" required/>
            <span th:if="${#fields.hasErrors('email')}" th:errors="*{email}" style="color:red;"></span>
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" th:field="*{password}" required/>
            <span th:if="${#fields.hasErrors('password')}" th:errors="*{password}" style="color:red;"></span>
        </div>
        <div>
            <label for="confirmPassword">Confirm Password:</label>
            <input type="password" id="confirmPassword" th:field="*{confirmPassword}" required/>
            <span th:if="${#fields.hasErrors('confirmPassword')}" th:errors="*{confirmPassword}" style="color:red;"></span>
        </div>
        <button type="submit">Register</button>
    </form>
    <a th:href="@{/login}">Already have an account? Login</a>
</body>
</html>
```

**Step 13: Update login.html to add registration link and registered message**

Add to `login.html` before the "Back to Home" link:
```html
    <div th:if="${param.registered}" style="color:green;">Registration successful! Please login.</div>
```
And add: `<a th:href="@{/register}">Create Account</a>`

**Step 14: Add data.sql for default ROLE_USER role**

`src/main/resources/data.sql`:
```sql
INSERT INTO roles (name) VALUES ('ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO roles (name) VALUES ('ROLE_ADMIN') ON CONFLICT DO NOTHING;
```

Also add to `application.properties`:
```properties
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
```

**Step 15: Write registration controller integration test**

`src/test/java/com/kabbo/sbip/ch06/controller/RegistrationControllerTests.java`:
```java
package com.kabbo.sbip.ch06.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registrationPageIsAccessible() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void successfulRegistrationRedirectsToLogin() throws Exception {
        mockMvc.perform(post("/register")
                        .with(csrf())
                        .param("username", "newuser")
                        .param("email", "new@example.com")
                        .param("password", "password123")
                        .param("confirmPassword", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered"));
    }

    @Test
    void mismatchedPasswordsShowsError() throws Exception {
        mockMvc.perform(post("/register")
                        .with(csrf())
                        .param("username", "user2")
                        .param("email", "user2@example.com")
                        .param("password", "password123")
                        .param("confirmPassword", "different"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }
}
```

**Step 16: Run all tests**

Run: `mvn test -pl ch06-additional-security -f spring-boot-in-practice/pom.xml`
Expected: All tests PASS

**Step 17: Commit**

```bash
git add .
git commit -m "feat(6.3): user registration with JPA-backed auth"
```

---

## Task 5: Branch 6.4 — Email Verification

Branch from `6.3-user-registration`. Adds: `VerificationToken` entity, email service, verification endpoint. Users must verify email before login.

**Branch:** `6.4-email-verification` (from `6.3-user-registration`)

**Files:**
- Create: `src/main/java/com/kabbo/sbip/ch06/model/VerificationToken.java`
- Create: `src/main/java/com/kabbo/sbip/ch06/repository/VerificationTokenRepository.java`
- Create: `src/main/java/com/kabbo/sbip/ch06/service/EmailService.java`
- Modify: `src/main/java/com/kabbo/sbip/ch06/service/UserService.java` (set enabled=false on register, create token)
- Modify: `src/main/java/com/kabbo/sbip/ch06/controller/RegistrationController.java` (add verify endpoint)
- Modify: `src/main/java/com/kabbo/sbip/ch06/config/SecurityConfig.java` (permit /verify)
- Modify: `pom.xml` (add spring-boot-starter-mail)
- Test: `src/test/java/com/kabbo/sbip/ch06/service/EmailVerificationTests.java`

**Key implementation details:**
- `VerificationToken` entity: UUID token, expiry date (24h), linked to User
- `EmailService`: sends verification link (use `spring.mail.*` config, MailHog for local dev)
- Registration flow: register → user.enabled=false → send email → user clicks link → enabled=true
- Add `/verify?token=xxx` endpoint to SecurityConfig permitAll list
- `application.properties` additions: `spring.mail.host=localhost`, `spring.mail.port=1025` (MailHog)

**Steps:** Create branch → write failing test → create VerificationToken entity → create repository → create EmailService → modify UserService → modify RegistrationController → update SecurityConfig → run tests → commit

---

## Task 6: Branch 6.5 — Login Attempt Control

Branch from `6.3-user-registration`. Adds: track failed login attempts, lock account after 3 failures, unlock after 15 minutes.

**Branch:** `6.5-login-attempt-ctrl` (from `6.3-user-registration`)

**Files:**
- Create: `src/main/java/com/kabbo/sbip/ch06/service/LoginAttemptService.java`
- Create: `src/main/java/com/kabbo/sbip/ch06/security/CustomAuthenticationFailureHandler.java`
- Create: `src/main/java/com/kabbo/sbip/ch06/security/CustomAuthenticationSuccessHandler.java`
- Modify: `src/main/java/com/kabbo/sbip/ch06/model/User.java` (add failedAttempts, lockTime)
- Modify: `src/main/java/com/kabbo/sbip/ch06/config/SecurityConfig.java` (register handlers)
- Test: `src/test/java/com/kabbo/sbip/ch06/service/LoginAttemptServiceTests.java`

**Key implementation details:**
- `User` entity adds: `int failedAttempts = 0`, `LocalDateTime lockTime`
- `LoginAttemptService`: increments attempts on failure, locks after 3, checks lock expiry (15 min)
- `CustomAuthenticationFailureHandler` implements `AuthenticationFailureHandler`: calls LoginAttemptService
- `CustomAuthenticationSuccessHandler` implements `AuthenticationSuccessHandler`: resets attempts on success
- SecurityConfig registers both handlers via `.formLogin(f -> f.failureHandler(...).successHandler(...))`

**Steps:** Create branch from 6.3 → write failing test → modify User entity → create LoginAttemptService → create handlers → update SecurityConfig → run tests → commit

---

## Task 7: Branch 6.6 — Remember Me

Branch from `main`. Adds persistent token-based Remember Me functionality.

**Branch:** `6.6-remember-me` (from `main`)

**Files:**
- Modify: `src/main/java/com/kabbo/sbip/ch06/config/SecurityConfig.java` (add rememberMe config)
- Modify: `src/main/resources/templates/login.html` (add remember-me checkbox)
- Test: `src/test/java/com/kabbo/sbip/ch06/RememberMeTests.java`

**Key implementation details:**
- SecurityConfig adds: `.rememberMe(rm -> rm.tokenValiditySeconds(86400).key("ch06-remember-key"))`
- For persistent tokens: add `JdbcTokenRepositoryImpl` bean with DataSource, set `createTableOnStartup(true)`
- Login template adds: `<input type="checkbox" name="remember-me" id="remember-me"/> <label for="remember-me">Remember me</label>`
- Test: login with remember-me → verify remember-me cookie exists

**Steps:** Create branch → write failing test → update SecurityConfig → update login template → run tests → commit

---

## Task 8: Branch 6.1 — Enabling HTTPS

Branch from `main`. Config-only task: generate self-signed cert, configure SSL in properties.

**Branch:** `6.1-enabling-https` (from `main`)

**Files:**
- Modify: `src/main/resources/application.properties` (SSL config)
- Create: `src/main/resources/keystore.p12` (generated via keytool)
- Create: `src/main/java/com/kabbo/sbip/ch06/config/HttpsRedirectConfig.java` (redirect HTTP→HTTPS)
- Test: `src/test/java/com/kabbo/sbip/ch06/HttpsConfigTests.java`

**Key implementation details:**
- Generate keystore: `keytool -genkeypair -alias ch06 -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore src/main/resources/keystore.p12 -validity 365 -storepass changeit`
- Properties: `server.ssl.key-store=classpath:keystore.p12`, `server.ssl.key-store-password=changeit`, `server.ssl.key-store-type=PKCS12`, `server.ssl.key-alias=ch06`
- Optional HTTP→HTTPS redirect via additional Tomcat connector

**Steps:** Create branch → generate keystore → update properties → create redirect config → test manually → commit

---

## Task 9: Branch 6.7 — reCAPTCHA

Branch from `main`. Adds Google reCAPTCHA v2 to the login page.

**Branch:** `6.7-recaptcha` (from `main`)

**Files:**
- Create: `src/main/java/com/kabbo/sbip/ch06/service/CaptchaService.java`
- Create: `src/main/java/com/kabbo/sbip/ch06/dto/CaptchaResponse.java`
- Modify: `src/main/resources/templates/login.html` (add reCAPTCHA widget)
- Modify: `src/main/resources/application.properties` (add captcha keys)
- Test: `src/test/java/com/kabbo/sbip/ch06/service/CaptchaServiceTests.java`

**Key implementation details:**
- Get reCAPTCHA v2 keys from Google (site key + secret key)
- `CaptchaService` verifies token via Google API: POST to `https://www.google.com/recaptcha/api/siteverify`
- `CaptchaResponse` DTO maps Google's JSON response
- Login page includes reCAPTCHA script + div
- Properties: `recaptcha.site-key=xxx`, `recaptcha.secret-key=xxx`
- For testing, use Google's test keys: site=`6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI`, secret=`6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe`

**Steps:** Create branch → write failing test → create CaptchaService → create CaptchaResponse → update login template → update properties → run tests → commit

---

## Task 10: Branch 6.8 — Two-Factor Authentication

Branch from `main`. Adds TOTP-based 2FA using Google Authenticator.

**Branch:** `6.8-two-factor-auth` (from `main`)

**Files:**
- Create: `src/main/java/com/kabbo/sbip/ch06/service/TotpService.java`
- Create: `src/main/java/com/kabbo/sbip/ch06/controller/TotpController.java`
- Create: `src/main/resources/templates/totp-setup.html`
- Create: `src/main/resources/templates/totp-verify.html`
- Modify: `pom.xml` (add TOTP library dependency)
- Test: `src/test/java/com/kabbo/sbip/ch06/service/TotpServiceTests.java`

**Key implementation details:**
- Add dependency: `dev.samstevens.totp:totp:1.7.1` (TOTP library)
- `TotpService`: generate secret, generate QR code data URI, verify TOTP code
- Setup flow: user enables 2FA → show QR code → user scans with Google Authenticator → confirm with code
- Login flow: after password auth, redirect to TOTP verification page if 2FA enabled
- User entity (if on this branch) or session-based 2FA secret storage

**Steps:** Create branch → add TOTP dependency → write failing test → create TotpService → create controller → create templates → run tests → commit

---

## Task 11: Branch 6.9 — OAuth2 Google Login

Branch from `main`. Adds "Login with Google" via OAuth2 Client.

**Branch:** `6.9-oauth2-google-login` (from `main`)

**Files:**
- Create: `src/main/java/com/kabbo/sbip/ch06/service/CustomOAuth2UserService.java`
- Modify: `pom.xml` (add spring-boot-starter-oauth2-client)
- Modify: `src/main/java/com/kabbo/sbip/ch06/config/SecurityConfig.java` (add oauth2Login)
- Modify: `src/main/resources/application.properties` (add Google client ID/secret)
- Modify: `src/main/resources/templates/login.html` (add "Login with Google" link)
- Test: `src/test/java/com/kabbo/sbip/ch06/OAuth2LoginTests.java`

**Key implementation details:**
- Add `spring-boot-starter-oauth2-client` dependency
- Properties: `spring.security.oauth2.client.registration.google.client-id=xxx`, `spring.security.oauth2.client.registration.google.client-secret=xxx`
- SecurityConfig adds: `.oauth2Login(oauth2 -> oauth2.loginPage("/login").userInfoEndpoint(...))`
- `CustomOAuth2UserService` extends `DefaultOAuth2UserService`: maps Google user info to local user
- Login template adds: `<a th:href="@{/oauth2/authorization/google}">Login with Google</a>`
- Google console: create OAuth2 credentials, redirect URI = `http://localhost:8086/login/oauth2/code/google`

**Steps:** Create branch → add OAuth2 dependency → update properties → create CustomOAuth2UserService → update SecurityConfig → update login template → run tests → commit

---

## Task 12: Branch 6.2 — Vault Secrets

Branch from `main`. Adds HashiCorp Vault integration for secrets management.

**Branch:** `6.2-vault-secrets` (from `main`)

**Files:**
- Modify: `pom.xml` (add spring-cloud-starter-vault-config)
- Create: `src/main/resources/bootstrap.properties` (Vault connection config)
- Test: Manual testing with local Vault dev server

**Key implementation details:**
- Add `spring-cloud-starter-vault-config` dependency
- Bootstrap properties: `spring.cloud.vault.uri=http://localhost:8200`, `spring.cloud.vault.token=<dev-token>`, `spring.cloud.vault.kv.backend=secret`, `spring.cloud.vault.kv.application-name=ch06`
- Run Vault dev server: `vault server -dev`
- Store secrets: `vault kv put secret/ch06 spring.datasource.password=secret`
- The app reads DB password from Vault instead of `application.properties`

**Steps:** Create branch → add Vault dependency → create bootstrap.properties → document Vault setup commands → test with local Vault → commit

---

## Task 13: Branch 6.10 — Actuator Security

Branch from `main`. Secures Spring Boot Actuator endpoints with role-based access.

**Branch:** `6.10-actuator-security` (from `main`)

**Files:**
- Modify: `pom.xml` (add spring-boot-starter-actuator)
- Modify: `src/main/java/com/kabbo/sbip/ch06/config/SecurityConfig.java` (secure actuator endpoints)
- Modify: `src/main/resources/application.properties` (expose actuator endpoints)
- Test: `src/test/java/com/kabbo/sbip/ch06/ActuatorSecurityTests.java`

**Key implementation details:**
- Add `spring-boot-starter-actuator` dependency
- Properties: `management.endpoints.web.exposure.include=health,info,metrics,env`, `management.endpoint.health.show-details=when-authorized`
- SecurityConfig: add second `SecurityFilterChain` bean with `@Order(1)` for actuator, require ADMIN role
- Test: unauthenticated → 401, USER role → 403 for sensitive endpoints, ADMIN role → 200

**Step 1: Write failing test**

`src/test/java/com/kabbo/sbip/ch06/ActuatorSecurityTests.java`:
```java
package com.kabbo.sbip.ch06;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ActuatorSecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthEndpointIsPublic() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
    }

    @Test
    void metricsRequiresAdmin() throws Exception {
        mockMvc.perform(get("/actuator/metrics"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void adminCanAccessMetrics() throws Exception {
        mockMvc.perform(get("/actuator/metrics")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }
}
```

**Steps:** Create branch → add actuator dependency → write failing test → update SecurityConfig with ordered filter chain → update properties → run tests → commit

---

## Summary: Implementation Order

| # | Task | Branch | Depends On | Estimated Steps |
|---|------|--------|------------|----------------|
| 1 | Parent POM | — | — | 3 |
| 2 | Ch01-Ch05 stubs | — | Task 1 | 4 |
| 3 | Ch06 base (main) | `main` | Task 1 | 12 |
| 4 | User Registration | `6.3-user-registration` | Task 3 | 17 |
| 5 | Email Verification | `6.4-email-verification` | Task 4 | ~12 |
| 6 | Login Attempt Control | `6.5-login-attempt-ctrl` | Task 4 | ~10 |
| 7 | Remember Me | `6.6-remember-me` | Task 3 | ~6 |
| 8 | Enabling HTTPS | `6.1-enabling-https` | Task 3 | ~5 |
| 9 | reCAPTCHA | `6.7-recaptcha` | Task 3 | ~8 |
| 10 | Two-Factor Auth | `6.8-two-factor-auth` | Task 3 | ~10 |
| 11 | OAuth2 Google Login | `6.9-oauth2-google-login` | Task 3 | ~8 |
| 12 | Vault Secrets | `6.2-vault-secrets` | Task 3 | ~5 |
| 13 | Actuator Security | `6.10-actuator-security` | Task 3 | ~6 |
