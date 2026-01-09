package com.bookcase.demo.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("admin"));
        System.out.println(encoder.encode("password1"));
        System.out.println(encoder.encode("password2"));
        System.out.println(encoder.encode("password3"));
        System.out.println(encoder.encode("password4"));
        System.out.println(encoder.encode("password5"));
        System.out.println(encoder.encode("test"));
    }
}

