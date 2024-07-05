package com.bookcase.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
//@ComponentScan("com.bookcase")
public class BookcaseApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookcaseApplication.class, args);

	}
}
