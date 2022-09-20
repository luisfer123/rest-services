package com.rest.services.data.entities;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class User {
	
	private Integer id;
	
	@Size(min = 2, message = "Name must have at leat 2 characters")
	private String name;
	
	@Past
	private Date birthDate;

	private User(UserBuilder UserBuilder) {
		this.id = UserBuilder.id;
		this.name = UserBuilder.name;
		this.birthDate = UserBuilder.birthDate;
	}

	private User() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this)
			return true;
		
		if(!(o instanceof User))
			return false;
		
		User other = (User) o;
		return id != null &&
				id.equals(other.id);
	}
	
	@Override
	public int hashCode() {
		return User.class.hashCode();
	}

	public static UserBuilder builder() {
		return new UserBuilder();
	}

	public static final class UserBuilder {
		private Integer id;
		private String name;
		private Date birthDate;

		private UserBuilder() {
		}

		public UserBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		public UserBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public UserBuilder withBirthDate(Date birthDate) {
			this.birthDate = birthDate;
			return this;
		}

		public User build() {
			return new User(this);
		}
	}

}
