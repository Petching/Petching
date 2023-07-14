package com.Petching.petching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableJpaAuditing
public class PetchingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetchingApplication.class, args);
	}

	@Controller
	public static class MainController {
		@GetMapping("/")
		public String main() {
			return "redirect:/chat/rooms";
		}
	}

}
