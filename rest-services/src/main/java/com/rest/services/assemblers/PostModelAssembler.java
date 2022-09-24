package com.rest.services.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Optional;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rest.services.control.PostResource;
import com.rest.services.data.entities.Post;
import com.rest.services.data.models.PostModel;

@Component
public class PostModelAssembler extends RepresentationModelAssemblerSupport<Post, PostModel> {

	public PostModelAssembler() {
		super(PostResource.class, PostModel.class);
	}

	public PostModelAssembler(Class<?> controllerClass, Class<PostModel> resourceType) {
		super(controllerClass, resourceType);
	}

	@Override
	public PostModel toModel(Post post) {
		
		PostModel postModel = instantiateModel(post);
		
		postModel.add(linkTo(methodOn(PostResource.class)
				.getById(post.getId()))
				.withSelfRel());
		
		postModel.add(linkTo(methodOn(PostResource.class)
				.getAll(Optional.of(post.getUser().getId())))
				.withRel("All-pots"));
		
		postModel.setId(post.getId());
		postModel.setDescription(post.getDescription());
		
		return postModel;
	}
	
	@Override
	public CollectionModel<PostModel> toCollectionModel(
			Iterable<? extends Post> posts) {
		
		CollectionModel<PostModel> bonsaiModels =
				super.toCollectionModel(posts);

		return bonsaiModels;
	}


}
