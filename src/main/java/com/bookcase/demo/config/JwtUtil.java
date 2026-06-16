//package com.bookcase.demo.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    private final Key key = Keys.hmacShaKeyFor(
//            "12345678901234567890123456789012".getBytes()
//    );
//    private final long EXPIRATION = 1000 * 60 * 60;
//
//    public String generateToken(String username, String role) {
//        return Jwts.builder()
//                .setSubject(username)
//                .claim("role", role)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
//                .signWith(key)
//                .compact();
//    }
//
//    public Claims extractClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(key)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public String extractUsername(String token) {
//        return extractClaims(token).getSubject();
//    }
//
//    public String extractRole(String token) {
//        return (String) extractClaims(token).get("role");
//    }
//
//    public boolean isTokenExpired(String token) {
//        return extractClaims(token).getExpiration().before(new Date());
//    }
//}
//
