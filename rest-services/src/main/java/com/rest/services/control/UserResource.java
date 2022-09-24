package com.rest.services.control;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.rest.services.data.entities.User;
import com.rest.services.exceptions.UserNotFoundException;
import com.rest.services.services.UserService;

/*
 * Here we use EntityModel, CollectionModel and PagedModel.
 * For Post entity we create a PostModel separated class to be 
 * used as implementation of EntityModel.
 *
 */
@RestController
@RequestMapping(path = "/users")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public CollectionModel<User> getAll() {
		
		List<User> users = userService.findAll();
		
		CollectionModel<User> allUsers = 
				CollectionModel.of(users);
		
		Link self = linkTo(methodOn(this.getClass())
				.getAll()).withSelfRel();
		
		allUsers.add(self);
		
		return allUsers;
	}
	
	@GetMapping(path = "/{id}")
	public EntityModel<User> findById(@PathVariable Integer id) {
		
		Optional<User> user = userService.findById(id);
		
		if(!user.isPresent())
			throw new UserNotFoundException("User with id " + id + " was not found.");
		
		// EntityModel to be returned
		EntityModel<User> userModel = EntityModel.of(user.get());
		
		// Create Link for all-users resource
		Link allUsersLink = linkTo(methodOn(this.getClass())
				.getAll()).withRel("all-users");
		// Create self reference link
		Link selfReference = linkTo(methodOn(this.getClass())
				.findById(user.get().getId())).withSelfRel();
		
		// Add link to EntityModel
		userModel.add(allUsersLink);
		userModel.add(selfReference);
				
		return userModel;
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable int id) {
		
		userService.deleteById(id);
		
		/*
		 * In this case we could also return 200 status.
		 * So, this method could return nothing (void).
		 */
		return ResponseEntity
				.noContent()
				.build();
	}
	
	@PostMapping
	public ResponseEntity<URI> createUser(@Valid @RequestBody User user) {
		
		User savedUser = userService.save(user);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		
		return ResponseEntity
				.created(location)
				.build();
	}
	
	/*
	 * Filtering field in an Entity
	 */
	@GetMapping(path = "/without-birthdate/{id}")
	public MappingJacksonValue findByIdWithoutBirthDate(@PathVariable Integer id) {
		
		User user = null;
		try {
			user = userService.findById(id).get();
		}catch(NoSuchElementException e) {
			throw new UserNotFoundException("User with id " + id + " was not found.");
		}

		// Filtering birth date out of User
		SimpleBeanPropertyFilter filter =
				SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
		FilterProvider filters = 
				(new SimpleFilterProvider())
						.addFilter("filterBirthDate", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(user);
		mapping.setFilters(filters);
		
		return mapping;
	}
	
	/*
	 * Filtering field in a List
	 */
	@GetMapping(path = "/without-birthdate")
	public MappingJacksonValue getAllWithoutBirthDate() {
		
		List<User> users = userService.findAll();
		
		// Filtering birth date out of User
		SimpleBeanPropertyFilter filter =
				SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
		FilterProvider filters = 
				(new SimpleFilterProvider())
						.addFilter("filterBirthDate", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(users);
		mapping.setFilters(filters);
		
		return mapping;
	}

}
