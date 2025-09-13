package com.example.training;

import com.example.training.entity.RoleEnum;
import com.example.training.service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TrainingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingApplication.class, args);
	}
	@Bean
	public ApplicationRunner runner(UserService userService) {
		return args -> {
			try {
				userService.registerUser("admin", "admin123", RoleEnum.ADMIN);
			} catch (Exception ignored) {}
			try {
				userService.registerUser("mohamed", "1234", RoleEnum.USER);
			} catch (Exception ignored) {}
		};
	}
}
