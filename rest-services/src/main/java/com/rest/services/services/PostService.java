package com.rest.services.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.services.data.entities.Post;
import com.rest.services.data.entities.User;
import com.rest.services.exceptions.PostNotFoundException;
import com.rest.services.exceptions.UserNotFoundException;
import com.rest.services.repositories.PostRepository;
import com.rest.services.repositories.UserRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	// TODO: For security verify if the passed userId corresponds with the
	// currently logged in User.
	@Transactional(readOnly = true)
	public List<Post> getAll(Integer userId) {
		return postRepo.findAll();
	}
	
	// TODO: For security verify if the passed userId corresponds with the
	// currently logged in User.
	@Transactional(readOnly = true)
	public Optional<Post> findById(Integer postId) {
		return postRepo.findById(postId);
	}

	// TODO: For security verify if the passed userId corresponds with the
	// currently logged in User.
	@Transactional(readOnly = true)
	public List<Post> findByUserId(Integer userId) {
		return postRepo.findByUserId(userId);
	}
	
	// TODO: For security verify if the passed userId corresponds with the
	// currently logged in User.
	@Transactional
	public Post save(Post post, Integer userId) 
			throws IllegalArgumentException, UserNotFoundException {
		
		Optional<User> optUser = userRepo.findById(userId);
		
		if(!optUser.isPresent())
			throw new UserNotFoundException("User with id " + userId + " does not exist");
		
		User user = optUser.get();
		user.addPost(post);
		
		return postRepo.save(post);
	}
	
	// TODO: For security verify if the Post to be deleted is related
	// with the currently logged in User.
	@Transactional
	public void deleteById(Integer postId) 
			throws PostNotFoundException {
		
		if(postRepo.existsById(postId)) {
			postRepo.deleteById(postId);
			
		} else {
			throw new PostNotFoundException("Post with id " + postId + " does not exist");
		}
			
	}

}
