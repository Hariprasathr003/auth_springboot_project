package com.demo.token;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableRabbit
public class JwTtoken1Application {

	public static void main(String[] args) {
		SpringApplication.run(JwTtoken1Application.class, args);
	}

}
