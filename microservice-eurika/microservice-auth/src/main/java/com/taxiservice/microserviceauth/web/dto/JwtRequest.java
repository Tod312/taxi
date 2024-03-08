package com.taxiservice.microserviceauth.web.dto;

import com.taxiservice.microserviceauth.web.util.OnCreate;
import com.taxiservice.microserviceauth.web.util.OnUpdate;
import com.taxiservice.microserviceauth.web.util.annotation.NewUsername;

import jakarta.validation.constraints.NotBlank;

public class JwtRequest {

	@NotBlank(message = "username can't be empty", groups = {OnUpdate.class, OnCreate.class})
	@NewUsername(message = "username has already taken", groups = {OnCreate.class})
	private String username;
	
	@NotBlank(message = "password can't be empty", groups = {OnUpdate.class, OnCreate.class})
	private String password;

	public JwtRequest() {}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
