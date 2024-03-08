package com.taxiservice.microserviceauth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taxiservice.microserviceauth.model.User;
import com.taxiservice.microserviceauth.service.UserService;
import com.taxiservice.microserviceauth.web.JwtEntityFactory;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User with login=" + username + " not found");
		}
		return JwtEntityFactory.createJwtEntity(user);
	}

}
