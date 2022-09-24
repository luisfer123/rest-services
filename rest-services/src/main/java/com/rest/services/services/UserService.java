package com.rest.services.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.services.data.entities.User;
import com.rest.services.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<User> findById(Integer userId) {
		return userRepo.findById(userId);
	}

	@Transactional
	public void deleteById(Integer userId) throws NoSuchElementException {
		if(userRepo.existsById(userId)) {
			userRepo.deleteById(userId);
		} else {
			throw new NoSuchElementException("User with id " + userId + " does not exist");
		}
	}
	
	@Transactional
	public User save(User user) throws IllegalArgumentException {
		return userRepo.save(user);
	}

}
