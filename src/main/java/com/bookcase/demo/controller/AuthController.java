package com.bookcase.demo.controller;

import com.bookcase.demo.dto.LoginRequest;
import com.bookcase.demo.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;
    private final ObjectMapper objectMapper;

    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    private final RestClient keycloakClient = RestClient.builder().build();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest body) {
        String tokenUrl = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("username", body.getUsername());
        form.add("password", body.getPassword());

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> tokenResponse = keycloakClient.post()
                    .uri(tokenUrl)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(form)
                    .retrieve()
                    .body(Map.class);

            String accessToken = (String) tokenResponse.get("access_token");
            Map<String, Object> payload = decodeJwtPayload(accessToken);

            String keycloakId = (String) payload.get("sub");
            String username = (String) payload.getOrDefault("preferred_username", body.getUsername());
            String role = extractRole(payload);

            appUserService.resolveOrCreateUser(keycloakId, username);

            return ResponseEntity.ok(Map.of(
                    "token", accessToken,
                    "username", username,
                    "role", role
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest body) {
        try {
            String adminToken = getAdminToken();

            String createUserUrl = keycloakServerUrl + "/admin/realms/" + realm + "/users";
            Map<String, Object> userRepresentation = Map.of(
                    "username", body.getUsername(),
                    "enabled", true,
                    "credentials", List.of(Map.of(
                            "type", "password",
                            "value", body.getPassword(),
                            "temporary", false
                    ))
            );

            keycloakClient.post()
                    .uri(createUserUrl)
                    .header("Authorization", "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(userRepresentation)
                    .retrieve()
                    .toBodilessEntity();

            return login(body);
        } catch (Exception e) {
            String msg = e.getMessage() != null && e.getMessage().contains("409")
                    ? "Username already exists"
                    : "Registration failed";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        String keycloakId = jwt.getSubject();
        String username = jwt.getClaimAsString("preferred_username");
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        @SuppressWarnings("unchecked")
        List<String> roles = realmAccess != null ? (List<String>) realmAccess.get("roles") : List.of();
        String role = roles.contains("ADMIN") ? "ADMIN" : "USER";

        appUserService.resolveOrCreateUser(keycloakId, username);

        return ResponseEntity.ok(Map.of(
                "username", username,
                "role", role
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }

    private String getAdminToken() {
        String tokenUrl = keycloakServerUrl + "/realms/master/protocol/openid-connect/token";

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", "admin-cli");
        form.add("username", adminUsername);
        form.add("password", adminPassword);

        @SuppressWarnings("unchecked")
        Map<String, Object> response = keycloakClient.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .body(Map.class);

        return (String) response.get("access_token");
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> decodeJwtPayload(String token) throws Exception {
        String[] parts = token.split("\\.");
        byte[] decoded = Base64.getUrlDecoder().decode(parts[1]);
        return objectMapper.readValue(decoded, Map.class);
    }

    @SuppressWarnings("unchecked")
    private String extractRole(Map<String, Object> payload) {
        Map<String, Object> realmAccess = (Map<String, Object>) payload.get("realm_access");
        if (realmAccess == null) return "USER";
        List<String> roles = (List<String>) realmAccess.get("roles");
        if (roles == null) return "USER";
        return roles.contains("ADMIN") ? "ADMIN" : "USER";
    }
}
