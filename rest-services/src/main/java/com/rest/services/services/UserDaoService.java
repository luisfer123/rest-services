package com.rest.services.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.rest.services.data.entities.User;

@Component
public class UserDaoService {
	
	private static List<User> users = new ArrayList<>();
	
	private static AtomicInteger usersCount = new AtomicInteger();
	
	static {
		users.add(User.builder()
				.withId(usersCount.incrementAndGet())
				.withName("Adam")
				.withBirthDate(new Date())
				.build());
		users.add(User.builder()
				.withId(usersCount.incrementAndGet())
				.withName("Eve")
				.withBirthDate(new Date())
				.build());
		users.add(User.builder()
				.withId(usersCount.incrementAndGet())
				.withName("Jack")
				.withBirthDate(new Date())
				.build());
	}
	
	public List<User> findAll() {
		return users;
	}
	
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(usersCount.incrementAndGet());
			users.add(user);
		}
		return user;
	}
	
	public User findOne(int id) {
		for(User u: users) {
			if(u.getId() == id)
				return u;
		}
		
		return null;
	}
	
	public User deleteById(int id) {
		
		Iterator<User> usersIterator = users.iterator();
		while(usersIterator.hasNext()) {
			User user = usersIterator.next();
			if(user.getId() == id) {
				usersIterator.remove();
				return user;
			}
		}
		
		return null;
	}

}
