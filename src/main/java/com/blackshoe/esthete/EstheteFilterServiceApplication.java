package com.blackshoe.esthete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EstheteFilterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstheteFilterServiceApplication.class, args);
	}

}
