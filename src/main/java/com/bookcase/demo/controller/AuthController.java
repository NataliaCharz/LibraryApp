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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    private final RestClient keycloakClient = RestClient.builder().build();

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        String keycloakId = jwt.getSubject();
        String username = jwt.getClaimAsString("preferred_username");

        @SuppressWarnings("unchecked")
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest body) {
        try {
            String adminToken = getAdminToken();

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
                    .uri(keycloakServerUrl + "/admin/realms/" + realm + "/users")
                    .header("Authorization", "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(userRepresentation)
                    .retrieve()
                    .toBodilessEntity();

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User created"));
        } catch (Exception e) {
            String msg = e.getMessage() != null && e.getMessage().contains("409")
                    ? "Username already exists"
                    : "Registration failed";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
    }

    private String getAdminToken() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", "admin-cli");
        form.add("username", adminUsername);
        form.add("password", adminPassword);

        @SuppressWarnings("unchecked")
        Map<String, Object> response = keycloakClient.post()
                .uri(keycloakServerUrl + "/realms/master/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .body(Map.class);

        return (String) response.get("access_token");
    }
}
