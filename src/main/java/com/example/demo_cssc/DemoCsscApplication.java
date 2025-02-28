package com.example.demo_cssc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider") 
@ComponentScan(basePackages = "com.example.demo_cssc")
public class DemoCsscApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoCsscApplication.class, args);
	}
}

