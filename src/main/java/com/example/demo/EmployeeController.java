package com.example.demo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/employees")
	List<Employee> all() {
		logger.info("Getting all employees");
		return repository.findAll();
	}
	// end::get-aggregate-root[]

	@PostMapping("/employees")
	Employee newEmployee(@RequestBody Employee newEmployee) {
		logger.info("Adding a new employee " + newEmployee);
		return repository.save(newEmployee);
	}

	// Single item
	@GetMapping("/employees/{id}/internal/{name}")
	Employee one(@PathVariable Long id, @PathVariable String name) {

		logger.info("Getting a student with id=" + id + " and name=" + name);
		return repository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(id));
	}

}
