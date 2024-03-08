package com.taxiservice.microserviceauth.web.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taxiservice.microserviceauth.service.AuthService;
import com.taxiservice.microserviceauth.web.dto.JwtRequest;
import com.taxiservice.microserviceauth.web.dto.JwtResponse;
import com.taxiservice.microserviceauth.web.util.OnCreate;
import com.taxiservice.microserviceauth.web.util.OnUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@Validated(OnUpdate.class) @RequestBody JwtRequest jwtRequest) {
		JwtResponse jwtResponse = authService.login(jwtRequest);
		return ResponseEntity.ok().body(jwtResponse);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@Validated(OnCreate.class) @RequestBody JwtRequest jwtRequest) {
		authService.register(jwtRequest);
		return ResponseEntity.ok().body("Registration is successfully");
	}
	
	@GetMapping("/refresh")
	public ResponseEntity<JwtResponse> refresh(@RequestParam String refreshToken) {
		JwtResponse jwtResponse = authService.refresh(refreshToken);
		return ResponseEntity.ok().body(jwtResponse);
	}
	
	@GetMapping("/validate")
	public ResponseEntity<String> validateToken(@RequestParam String accessToken) {
		authService.validation(accessToken);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Token is valid");
	}
}
