package com.taxiservice.microserviceauth.service;

import com.taxiservice.microserviceauth.model.User;

public interface UserService {

	public User getByUsername(String username);
	public User getById(Long id);
	public User create(User user);
}
