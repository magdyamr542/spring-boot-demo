package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
class Role {

	private @Id @GeneratedValue Long id;
	private String name;
	private String description;

	Role() {
	}

	Role(String name, String description) {

		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getDeString() {
		return this.description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Employee))
			return false;
		Role role = (Role) o;
		return Objects.equals(this.id, role.id) && Objects.equals(this.name, role.name)
				&& Objects.equals(this.description, role.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.description);
	}

	@Override
	public String toString() {
		return "Role{" + "id=" + this.id + ", name='" + this.name + '\'' + ", description='" + this.description
				+ '\'' + '}';
	}
}
