package com.bookcase.demo.controller;

import com.bookcase.demo.dto.AppUserRole;
import com.bookcase.demo.dto.LoginRequest;
import com.bookcase.demo.entity.AppUser;
import com.bookcase.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return ResponseEntity.ok(Map.of(
                "username", userDetails.getUsername(),
                "roles", userDetails.getAuthorities().stream()
                        .map(a -> a.getAuthority())
                        .toList()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request) {
        if(userRepository.existsByUserName(request.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        AppUser appUser = new AppUser();
        appUser.setUserName(request.getUsername());
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUser.setRole(AppUserRole.USER);
        userRepository.save(appUser);

        return ResponseEntity.ok(Map.of(
                "username", appUser.getUsername(),
                "role", appUser.getRole().name()
        ));
    }

    @PostMapping("/guest-login")
    public ResponseEntity<?> guestLogin(HttpServletRequest request) {
        AppUser guest = new AppUser();
        guest.setUserName("guest");
        guest.setRole(AppUserRole.GUEST);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                guest, null, guest.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        request.getSession(true);
        return ResponseEntity.ok(Map.of(
                "username", "guest",
                "roles", guest.getAuthorities().stream()
                        .map(a -> a.getAuthority())
                        .toList()
        ));    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.ok(Map.of(
                    "username", "guest",
                    "roles", List.of("GUEST")
            ));
        }
        return ResponseEntity.ok(Map.of(
                "username", authentication.getName(),
                "roles", authentication.getAuthorities().stream()
                        .map(a -> a.getAuthority()).toList()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }
}

