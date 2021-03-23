package com.example.socialpost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //Time Entity에 해당하는 엔티티들을 감시해서 값을 알아서 넣어줌
@SpringBootApplication
public class SocialpostApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialpostApplication.class, args);
	}

}
