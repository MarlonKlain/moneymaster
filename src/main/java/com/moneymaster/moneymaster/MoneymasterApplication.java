package com.moneymaster.moneymaster;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class MoneymasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneymasterApplication.class, args);
	}

	// --- ADD THIS BEAN TO DEBUG ---
	@Bean
	CommandLineRunner commandLineRunner(Environment env) {
		return args -> {
			System.out.println("=================================================");
			System.out.println(">>>>>> [DEBUG] SPRING_DATASOURCE_URL from Environment: " + env.getProperty("SPRING_DATASOURCE_URL"));
			System.out.println("=================================================");
		};
	}
}
