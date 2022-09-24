package com.rest.services.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.services.data.entities.Post;
import com.rest.services.data.entities.User;
import com.rest.services.repositories.UserRepository;

@Service
public class DataInitializerService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Transactional
	@EventListener(classes = ApplicationReadyEvent.class)
	public void onInit() {
		
		User oscar = User.builder()
				.withName("Oscar")
				.withBirthDate(new Date())
				.build();
		userRepo.save(oscar);
		
		User juan = User.builder()
				.withName("Juan")
				.withBirthDate(new Date())
				.build();
		userRepo.save(juan);
		
		User pedro = User.builder()
				.withName("Pedro")
				.withBirthDate(new Date())
				.build();
		
		userRepo.save(pedro);
		
		Post post1Oscar = Post.builder()
				.withDescription("First post for Oscar")
				.build();
		oscar.addPost(post1Oscar);
		
		Post post2Oscar = Post.builder()
				.withDescription("Second post for Oscar")
				.build();
		oscar.addPost(post2Oscar);
		
		Post post3Oscar = Post.builder()
				.withDescription("Third post Oscar")
				.build();
		oscar.addPost(post3Oscar);
		
		userRepo.save(oscar);
		
		Post post1Juan = Post.builder()
				.withDescription("First post for Juan")
				.build();
		juan.addPost(post1Juan);
		
		Post post2Juan = Post.builder()
				.withDescription("Second post for Juan")
				.build();
		juan.addPost(post2Juan);
		
		Post post3Juan = Post.builder()
				.withDescription("Third post Juan")
				.build();
		juan.addPost(post3Juan);
		
		userRepo.save(juan);
		
		Post post1Pedro = Post.builder()
				.withDescription("First post for Pedro")
				.build();
		pedro.addPost(post1Pedro);
		
		Post post2Pedro = Post.builder()
				.withDescription("Second post for Pedro")
				.build();
		pedro.addPost(post2Pedro);
		
		Post post3Pedro = Post.builder()
				.withDescription("Third post Pedro")
				.build();
		pedro.addPost(post3Pedro);
		
		userRepo.save(pedro);
		
	}

}
