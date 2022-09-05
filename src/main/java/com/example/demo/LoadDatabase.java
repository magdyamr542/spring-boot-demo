package com.example.demo;

import org.slf4j.LoggerFactory;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

	private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
		return args -> {

			logger.info("Loading the database...");

			Role role1 = new Role("software developer", "front end work");
			Role role2 = new Role("Business manager", "does nothing");
			Role role3 = new Role("Doctor", "does nursing");

			logger.info("Saving role 1"
					+ roleRepository.save(role1));

			logger.info("Saving role 2"
					+ roleRepository.save(role2));

			logger.info("Saving role 3"
					+ roleRepository.save(role3));

			logger.info("Saving employee 1"
					+ employeeRepository.save(new Employee("amr",
							List.of(role1, role2))));

			logger.info("Saving employee 2"
					+ employeeRepository.save(new Employee("tarek",
							List.of(role3))));
		};
	}
}
