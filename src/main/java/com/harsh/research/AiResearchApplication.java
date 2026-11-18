package com.harsh.research;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class AiResearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiResearchApplication.class, args);
	}

}
