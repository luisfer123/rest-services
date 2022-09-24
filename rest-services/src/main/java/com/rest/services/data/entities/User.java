package com.rest.services.data.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
@Table(name="`User`")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Size(min = 2, message = "Name must have at leat 2 characters")
	@Column(name = "`name`")
	private String name;
	
	@Past
	@Column(name = "`birth_date`")
	private Date birthDate;
	
	@OneToMany(
			mappedBy = "user",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<Post> posts = new HashSet<>();
	
	public User() {
		super();
	}

	private User(UserBuilder UserBuilder) {
		this.id = UserBuilder.id;
		this.name = UserBuilder.name;
		this.birthDate = UserBuilder.birthDate;
	}
	
	public void addPost(Post post) {
		post.setUser(this);
		this.posts.add(post);
	}
	
	public void removePost(Post post) {
		post.setUser(null);
		this.posts.remove(post);
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

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
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
