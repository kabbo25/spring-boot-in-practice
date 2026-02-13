# Design: Spring Boot in Practice — Chapter 6 Microservice

**Date**: 2026-02-13
**Book**: Spring Boot in Practice by Somnath Musib (Manning)
**Chapter**: 6 — Implementing additional security with Spring Security

---

## Project Structure

### Maven Multi-Module with Separate Git Repos

```
spring-boot-in-practice/                     (aggregator, own git or no git)
├── pom.xml                                  (parent POM: packaging=pom)
├── ch01-booting-spring-boot/                (own git repo, stub)
├── ch02-common-tasks/                       (own git repo, stub)
├── ch03-database-access/                    (own git repo, stub)
├── ch04-autoconfiguration-actuator/         (own git repo, stub)
├── ch05-securing-applications/              (own git repo, stub)
└── ch06-additional-security/                (own git repo, full impl)
```

- **Parent POM** inherits from `spring-boot-starter-parent:4.0.2`
- Each chapter inherits from parent via `<relativePath>../pom.xml</relativePath>`
- `mvn clean package` from root builds all chapters
- Chapters 1-5 are minimal stubs (Application class + pom.xml)
- Chapter 6 is the full implementation target

---

## Technology Stack

| Layer       | Technology               | Version     |
|-------------|--------------------------|-------------|
| Runtime     | Java                     | 21          |
| Framework   | Spring Boot              | 4.0.2       |
| Security    | Spring Security          | 7.x (Boot-managed) |
| Web         | Spring MVC + Thymeleaf   | Boot-managed |
| Data        | Spring Data JPA + Hibernate | Boot-managed |
| DB (dev)    | H2 (in-memory)           | Boot-managed |
| Build       | Maven                    | 3.9+        |
| Testing     | JUnit 5 + Spring Security Test | Boot-managed |

---

## Chapter 6: Feature Branch Strategy

### Branch Dependency Graph

```
main ─── base setup (Spring Boot + Security + JPA + H2 + Thymeleaf + form login)
 │
 ├── 6.1-enabling-https          (from main)
 ├── 6.2-vault-secrets           (from main)
 │
 ├── 6.3-user-registration       (from main)
 │   ├── 6.4-email-verification  (from 6.3)
 │   └── 6.5-login-attempt-ctrl  (from 6.3)
 │
 ├── 6.6-remember-me             (from main)
 ├── 6.7-recaptcha               (from main)
 ├── 6.8-two-factor-auth         (from main)
 ├── 6.9-oauth2-google-login     (from main)
 └── 6.10-actuator-security      (from main)
```

### Branch Details

| Branch | Description | Key Classes | Deps |
|--------|-------------|-------------|------|
| `main` | Base app: form login, in-memory user, Thymeleaf login page | `SecurityConfig`, `Ch06Application` | spring-boot-starter-security, thymeleaf, data-jpa, h2 |
| `6.1-enabling-https` | Self-signed cert, HTTPS redirect, TLS config | `application.properties` (ssl config) | — |
| `6.2-vault-secrets` | HashiCorp Vault for secrets management | `bootstrap.properties`, Vault config | spring-cloud-vault |
| `6.3-user-registration` | User + Role entities, registration form, BCrypt encoding | `User`, `Role`, `UserRepository`, `RegistrationController`, `CustomUserDetailsService` | — |
| `6.4-email-verification` | Verification token, email sending, confirm endpoint | `VerificationToken`, `EmailService`, `VerificationController` | spring-boot-starter-mail |
| `6.5-login-attempt-ctrl` | Track failed logins, lock after N attempts | `LoginAttemptService`, `CustomAuthenticationFailureHandler` | — |
| `6.6-remember-me` | Persistent token remember-me with DB storage | `PersistentTokenRepository` bean, SecurityConfig update | — |
| `6.7-recaptcha` | Google reCAPTCHA v2 on login page | `CaptchaService`, `CaptchaResponse`, login template update | — |
| `6.8-two-factor-auth` | TOTP setup + verification with Google Authenticator | `TotpService`, `TotpController`, QR code generation | dev.samstevens.totp |
| `6.9-oauth2-google-login` | OAuth2 client registration, Google social login | OAuth2 client config, `CustomOAuth2UserService` | spring-boot-starter-oauth2-client |
| `6.10-actuator-security` | Secure actuator endpoints, role-based access | Actuator security config in `SecurityConfig` | spring-boot-starter-actuator |

---

## Chapter 6 Package Structure

```
ch06-additional-security/src/main/java/com/kabbo/sbip/ch06/
├── Ch06Application.java
├── config/
│   └── SecurityConfig.java
├── model/
│   ├── User.java
│   └── Role.java
├── repository/
│   └── UserRepository.java
├── service/
│   ├── CustomUserDetailsService.java
│   └── (feature-specific services per branch)
└── controller/
    └── (feature-specific controllers per branch)
```

---

## Implementation Order

Recommended sequence for learning:

1. **`main`** — Base Spring Security with form login
2. **`6.3-user-registration`** — Foundation for 6.4 and 6.5
3. **`6.4-email-verification`** — Branch from 6.3
4. **`6.5-login-attempt-ctrl`** — Branch from 6.3
5. **`6.6-remember-me`** — Standalone from main
6. **`6.1-enabling-https`** — Config-only task
7. **`6.7-recaptcha`** — Needs Google API key
8. **`6.8-two-factor-auth`** — Needs Google Authenticator
9. **`6.9-oauth2-google-login`** — Needs Google OAuth2 credentials
10. **`6.2-vault-secrets`** — Needs HashiCorp Vault running
11. **`6.10-actuator-security`** — Quick config task

---

## Database Strategy

- **Development**: H2 in-memory (zero setup)
- **Optional**: PostgreSQL via `application-postgres.properties` profile
- Tables auto-created from JPA entities via `spring.jpa.hibernate.ddl-auto=create-drop`

---

## Chapters 1-5 Stubs

Minimal stubs to keep Maven build working:
- `pom.xml` with chapter-specific artifactId
- `src/main/java/.../ChXXApplication.java` (main class only)
- `src/main/resources/application.properties` (minimal config)
- Dependencies: only `spring-boot-starter-web` for stubs
