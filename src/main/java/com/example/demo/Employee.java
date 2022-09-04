package com.example.demo;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
class Employee {

	private @Id @GeneratedValue Long id;
	private String name;

	@OneToMany
	private List<Role> roles;

	Employee() {
	}

	Employee(String name, List<Role> roles) {
		this.name = name;
		this.roles = roles;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Employee))
			return false;
		Employee employee = (Employee) o;
		return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name);

	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name);
	}

	@Override
	public String toString() {
		return "Employee{" + "id=" + this.id + ", name='" + this.name + '}';
	}
}
