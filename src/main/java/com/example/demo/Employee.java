package com.example.demo;

import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
class Employee {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @OneToMany
  private List<Role> roles;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(referencedColumnName = "id")
  private Secret secret;

  Employee() {}

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

  public void setSecret(Secret secret) {
    this.secret = secret;
  }

  public Secret getSecret() {
    return this.secret;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Employee)) return false;
    Employee employee = (Employee) o;
    return (
      Objects.equals(this.id, employee.id) &&
      Objects.equals(this.name, employee.name)
    );
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
