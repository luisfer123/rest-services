package com.rest.services.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Post")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "description")
	private String description;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public Post() {
		super();
	}

	private Post(Builder builder) {
		this.description = builder.description;
		this.id = builder.id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		
		if(!(o instanceof Post))
			return false;
		
		Post other = (Post) o;
		return id != null &&
				id.equals(other.id);
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	public static IDescriptionStage builder() {
		return new Builder();
	}

	public interface IDescriptionStage {
		public IBuildStage withDescription(String description);
	}

	public interface IBuildStage {
		public IBuildStage withId(Integer id);

		public Post build();
	}

	public static final class Builder implements IDescriptionStage, IBuildStage {
		private String description;
		private Integer id;

		private Builder() {
		}

		@Override
		public IBuildStage withDescription(String description) {
			this.description = description;
			return this;
		}

		@Override
		public IBuildStage withId(Integer id) {
			this.id = id;
			return this;
		}

		@Override
		public Post build() {
			return new Post(this);
		}
	}

}
