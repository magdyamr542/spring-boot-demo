package com.example.demo;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository
  extends PagingAndSortingRepository<Employee, Long> {
  List<Employee> findByName(String name);
  List<Employee> findByNameLike(String name);
}
