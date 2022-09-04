package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

class EmployeeNotFoundException extends RuntimeException {

	EmployeeNotFoundException(Long id) {
		super("Could not find employee " + id);
	}
}

@RestController
class EmployeeController {

	private final EmployeeRepository repository;
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	EmployeeController(EmployeeRepository repository) {
		this.repository = repository;
	}

	@QueryMapping
	List<Employee> employees() {
		logger.info("Getting all employees");
		return repository.findAll();
	}

	@QueryMapping
	Optional<Employee> employeeById(@Argument Long id) {
		logger.info("Getting one employee with id " + id);
		return repository.findById(id);
	}

}
