package com.example.demo;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

	private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(EmployeeRepository employeeRepository) {
		return args -> {
			logger.info("Preloading the first employee"
					+ employeeRepository.save(new Employee("amr", "software developer")));
			logger.info("Preloading the second employee"
					+ employeeRepository.save(new Employee("tarek", "doctor")));
		};
	}
}
