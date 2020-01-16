package com.scalablebackend.project1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Project1Application {

	public static void main(String[] args) {
		SpringApplication.run(Project1Application.class, args);
	}

}
