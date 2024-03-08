package com.taxiservice.microserviceauth.service.impl;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taxiservice.microserviceauth.model.Driver;
import com.taxiservice.microserviceauth.model.Role;
import com.taxiservice.microserviceauth.model.User;
import com.taxiservice.microserviceauth.service.AuthService;
import com.taxiservice.microserviceauth.service.DriverService;
import com.taxiservice.microserviceauth.service.UserService;
import com.taxiservice.microserviceauth.web.JwtProvider;
import com.taxiservice.microserviceauth.web.dto.JwtRequest;
import com.taxiservice.microserviceauth.web.dto.JwtResponse;

@Service
@Transactional
public class AuthServiceImpl implements AuthService{

	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final UserService userService;
	private final DriverService driverService;
	private final PasswordEncoder encoder;
	

	public AuthServiceImpl(JwtProvider jwtProvider, UserService userService, AuthenticationManager authenticationManager, PasswordEncoder encoder, DriverService driverService) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
		this.userService = userService;
		this.driverService = driverService;
		this.encoder = encoder;

	}

	@Override
	@Transactional(readOnly = true)
	public JwtResponse login(JwtRequest jwtRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));

		User user = userService.getByUsername(jwtRequest.getUsername());
		JwtResponse jwtResponse = new JwtResponse();
		
		jwtResponse.setId(user.getId());
		jwtResponse.setUsername(user.getUsername());
		jwtResponse.setAccessToken(jwtProvider.createAccessToken(user.getId(), user.getUsername(), user.getRoles()));
		jwtResponse.setRefreshToken(jwtProvider.createRefreshToken(user.getId(), user.getUsername()));
		
		return jwtResponse;
	}

	@Override
	@Transactional(readOnly = true)
	public JwtResponse refresh(String refreshToken) {
		return jwtProvider.refreshAccessToken(refreshToken);
		
	}

	@Override
	@Transactional
	public void register(JwtRequest jwtRequest) {
		User user = new User();
		user.setUsername(jwtRequest.getUsername());
		user.setPassword(encoder.encode(jwtRequest.getPassword()));
		user.setRoles(List.of(Role.ROLE_USER));
		User createUser = userService.create(user);
		Driver driver = new Driver();
		driver.setUser(createUser);
		driverService.create(driver);
	}

	@Override
	@Transactional(readOnly = true)
	public void validation(String accessToken) {
		if(!jwtProvider.validToken(accessToken)) {
			throw new AccessDeniedException("Token is not valid");
		}
		
	}
	
	
}
