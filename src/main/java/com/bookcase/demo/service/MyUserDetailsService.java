//package com.bookcase.demo.service;
//
//import com.bookcase.demo.entity.AppUser;
//import com.bookcase.demo.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class MyUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository
//                .findUserByUserName(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//    }
//
//    public AppUser loadAppUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findUserByUserName(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//    }
//}
