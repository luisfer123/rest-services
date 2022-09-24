package com.rest.services.data.models;

import java.util.Objects;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "post")
@JsonInclude(Include.NON_NULL)
public class PostModel extends RepresentationModel<PostModel> {
	
	private Integer id;
	
	private String description;
	
	public PostModel() {
		super();
	}

	public PostModel(Iterable<Link> initialLinks) {
		super(initialLinks);
	}

	public PostModel(Link initialLink) {
		super(initialLink);
	}

	public PostModel(String description) {
		super();
		this.description = description;
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

	@Override
	public String toString() {
		return "PostModel [id=" + id + ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(description, id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostModel other = (PostModel) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id);
	}

	
}
