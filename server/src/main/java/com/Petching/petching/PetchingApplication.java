package com.Petching.petching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PetchingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetchingApplication.class, args);
	}

}
