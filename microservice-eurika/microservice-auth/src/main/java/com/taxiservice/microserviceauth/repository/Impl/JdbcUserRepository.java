package com.taxiservice.microserviceauth.repository.Impl;

import java.util.Optional;

import com.taxiservice.microserviceauth.model.User;

public interface JdbcUserRepository {

	public Optional<User> findById(Long id);
	public User save(User user);
	public Optional<User> findByUsername(String Username);
}
