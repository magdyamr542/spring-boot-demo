package com.example.demo;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
  RabbitTemplate rabbitTemplate;

  @Autowired
  private final SecretRepository secretRepo;

  @Autowired
  private final RoleRepository roleRepo;

  private static final Logger logger = LoggerFactory.getLogger(
    Controller.class
  );

  Controller(
    EmployeeRepository employeeRepository,
    RoleRepository roleRepository,
    SecretRepository secretRepository
  ) {
    this.employeeRepo = employeeRepository;
    this.roleRepo = roleRepository;
    this.secretRepo = secretRepository;
  }

  @QueryMapping
  Iterable<Employee> employeesSortedByName() {
    logger.info("Getting all employees sorted by name ");
    return employeeRepo.findAll(Sort.by("name"));
  }

  @QueryMapping
  Iterable<Employee> getEmployeeLimited(@Argument int count) {
    logger.info("Getting limited number of employees ", count);
    return employeeRepo.findAll(PageRequest.ofSize(count));
  }

  @QueryMapping
  List<Employee> getEmployeeByName(@Argument String name) {
    logger.info("Getting all employees by name " + name);
    return employeeRepo.findByNameLike(name);
  }

  @QueryMapping
  Iterable<Employee> employees() {
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

  @QueryMapping
  List<Secret> secrets() {
    logger.info("Getting all secrets");
    return secretRepo.findAll();
  }

  @MutationMapping
  Employee addEmployee(@Argument(name = "ep") EmployeeInput employeeInput) {
    logger.info("Adding an employee with name " + employeeInput.getName());
    return employeeRepo.save(new Employee(employeeInput.getName(), List.of()));
  }

  @MutationMapping
  Employee addRoleToEmployee(
    @Argument Long employeeId,
    @Argument RoleInput role
  )
    throws Exception {
    logger.info("Adding role " + role + " to employee with id " + employeeId);
    Employee emp = employeeRepo
      .findById(employeeId)
      .orElseThrow(
        () -> new Exception("There is no such employee with the provided id")
      );

    Role newRole = new Role(role.getName(), role.getDescription());
    logger.info("Saving the  role " + newRole);
    roleRepo.save(newRole);

    emp.addRole(newRole);
    employeeRepo.save(emp);
    return emp;
  }

  @MutationMapping
  Secret addSecret(@Argument String value) {
    Secret secret = new Secret(value);
    logger.info("Adding a secret " + secretRepo.save(secret));
    return secret;
  }

  @MutationMapping
  Employee addSecretToEmployee(
    @Argument Long secretId,
    @Argument Long employeeId
  )
    throws Exception {
    logger.info(
      "Adding secret with id " + secretId + " to employee with id " + employeeId
    );
    Employee emp = employeeRepo
      .findById(employeeId)
      .orElseThrow(
        () -> new Exception("There is no such employee with the provided id")
      );

    Secret secret = secretRepo
      .findById(secretId)
      .orElseThrow(
        () -> new Exception("There is no such secret with the provided id")
      );

    emp.setSecret(secret);
    employeeRepo.save(emp);

    return emp;
  }

  @MutationMapping
  Boolean deleteEmployee(@Argument Long id) {
    logger.info("Deleting employee with id" + id);
    employeeRepo.deleteById(id);
    return true;
  }

  @MutationMapping
  Boolean sendMessageToQueue(
    @Argument String value,
    @Argument String routingKey,
    @Argument String exchange
  ) {
    logger.info("-----");
    logger.info("Send msg:" + value);
    logger.info("Exchange:" + exchange);
    logger.info("Routing key:" + routingKey);
    logger.info("-----");
    this.rabbitTemplate.convertAndSend(exchange, routingKey, value);
    return true;
  }
}
