package com.rest.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.services.data.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
