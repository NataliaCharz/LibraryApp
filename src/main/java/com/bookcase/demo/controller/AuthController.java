package com.bookcase.demo.controller;

import com.bookcase.demo.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

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

}
