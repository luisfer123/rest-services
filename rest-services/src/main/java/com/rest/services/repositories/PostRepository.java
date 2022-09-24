package com.rest.services.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.services.data.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	List<Post> findByUserId(Integer userId);

}
