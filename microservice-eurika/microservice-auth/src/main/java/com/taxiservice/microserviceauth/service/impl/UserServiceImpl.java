package com.taxiservice.microserviceauth.service.impl;

import org.springframework.stereotype.Service;

import com.taxiservice.microserviceauth.model.User;
import com.taxiservice.microserviceauth.model.exception.ResourceNotFound;
import com.taxiservice.microserviceauth.repository.UserRepository;
import com.taxiservice.microserviceauth.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User getByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	@Override
	public User getById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFound("User not found with id=" + id));
	}

	@Override
	public User create(User user) {
		return userRepository.save(user);
	}

}
