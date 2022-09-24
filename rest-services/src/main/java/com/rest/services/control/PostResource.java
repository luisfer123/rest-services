package com.rest.services.control;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.services.assemblers.PostModelAssembler;
import com.rest.services.data.entities.Post;
import com.rest.services.data.models.PostModel;
import com.rest.services.exceptions.UserNotFoundException;
import com.rest.services.services.PostService;

@RestController
@RequestMapping(path = "/posts")
public class PostResource {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private PostModelAssembler postModelAssembler;
	
	@GetMapping(path = "")
	public ResponseEntity<CollectionModel<PostModel>> getAll(
			@RequestParam("user_id") Optional<Integer> optUserId) {
		
		if(!optUserId.isPresent())
			throw new UserNotFoundException("User with given id was not found.");
		
		List<Post> posts =  postService.findByUserId(optUserId.get());
		
		return new ResponseEntity<>(
				postModelAssembler.toCollectionModel(posts),
				HttpStatus.OK);
	}
	
	@GetMapping(path = "/{postId}")
	public ResponseEntity<PostModel> getById(
			@PathVariable int postId) {
				
		// When implementing security. In service we should check whether 
		// Post.User.id == current login user id
		return postService.findById(postId)
				.map(postModelAssembler::toModel)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping(path = "")
	public ResponseEntity<URI> createPost(
			@RequestBody Post post,
			@RequestParam("user_id") Integer userId) {
		
		Post savedPost = postService.save(post, userId);
		
		URI self = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		
		return ResponseEntity
				.created(self)
				.build();
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<HttpStatus> deletePost(
			@PathVariable("id") Integer postId) {
		
		postService.deleteById(postId);
		
		return ResponseEntity
				.noContent()
				.build();
	}

}
