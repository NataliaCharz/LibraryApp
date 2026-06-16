//package com.bookcase.demo.config;
//
//import com.bookcase.demo.entity.AppUser;
//import com.bookcase.demo.service.MyUserDetailsService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//@Component
//public class JwtAuthFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//    private final MyUserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        String username = null;
//        String token = null;
//
//        if (request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if ("JWT_TOKEN".equals(cookie.getName())) {
//                    token = cookie.getValue();
//                    break;
//                }
//            }
//        }
//
//        if (token != null) {
//            try {
//                username = jwtUtil.extractUsername(token);
//            } catch (Exception e) {
//                System.out.println("JWT ERROR: " + e.getMessage());
//            }
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            AppUser user = userDetailsService.loadAppUserByUsername(username);
//
//            UsernamePasswordAuthenticationToken authToken =
//                    new UsernamePasswordAuthenticationToken(
//                            user,
//                            null,
//                            user.getAuthorities()
//                    );
//
//            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//        }
//        filterChain.doFilter(request, response);
//    }
//
//}
