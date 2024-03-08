package com.taxiservice.microserviceauth.service;

import com.taxiservice.microserviceauth.web.dto.JwtRequest;
import com.taxiservice.microserviceauth.web.dto.JwtResponse;

public interface AuthService {

	public JwtResponse login(JwtRequest jwtRequest);
	
	public JwtResponse refresh(String refreshToken);

	public void register(JwtRequest jwtRequest);
	public void validation(String  accessToken);
}
