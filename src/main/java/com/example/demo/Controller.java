package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

class EmployeeNotFoundException extends RuntimeException {

	EmployeeNotFoundException(Long id) {
		super("Could not find employee " + id);
	}

}

@RestController
class Controller {

	@Autowired
	private final EmployeeRepository employeeRepo;

	@Autowired
	private final RoleRepository roleRepo;

	private static final Logger logger = LoggerFactory.getLogger(Controller.class);

	Controller(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
		this.employeeRepo = employeeRepository;
		this.roleRepo = roleRepository;
	}

	@QueryMapping
	List<Employee> employees() {
		logger.info("Getting all employees");
		return employeeRepo.findAll();
	}

	@QueryMapping
	Optional<Employee> employeeById(@Argument Long id) {
		logger.info("Getting one employee with id " + id);
		return employeeRepo.findById(id);
	}

	@QueryMapping
	List<Role> roles() {
		logger.info("Getting all roles");
		return roleRepo.findAll();
	}

	@MutationMapping
	Employee addEmployee(@Argument(name = "ep") EmployeeInput employeeInput) {
		logger.info("Adding an employee with name " + employeeInput.getName());
		return employeeRepo.save(new Employee(employeeInput.getName(), List.of()));
	}

	@MutationMapping
	Employee addRoleToEmployee(@Argument Long employeeId, @Argument RoleInput role) throws Exception {
		logger.info("Adding role " + role + " to employee with id " + employeeId);
		Employee emp = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new Exception("There is no such employee with the provided id"));

		Role newRole = new Role(role.getName(), role.getDescription());
		logger.info("Saving the  role " + newRole);
		roleRepo.save(newRole);

		emp.addRole(newRole);
		employeeRepo.save(emp);
		return emp;
	}

}
