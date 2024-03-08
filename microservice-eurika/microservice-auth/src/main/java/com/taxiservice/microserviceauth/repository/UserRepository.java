package com.taxiservice.microserviceauth.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.taxiservice.microserviceauth.model.User;

public interface UserRepository extends Repository<User, Long>{

	public Optional<User> findById(Long id);
	public User save(User user);
	public Optional<User> findByUsername(String Username);
}
