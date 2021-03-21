package com.zorup.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SocialpostApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialpostApplication.class, args);
	}

}
