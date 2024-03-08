package com.taxiservice.microserviceauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.taxiservice.microserviceauth.web.util.NewUsernameValidator;
import com.taxiservice.microserviceauth.web.util.annotation.NewUsername;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.Validator;

@Configuration
public class TestSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
			
		httpSecurity.authorizeHttpRequests(http -> http
			.anyRequest().permitAll())
		.sessionManagement(sessionManagement -> sessionManagement
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.csrf(csrf -> csrf.disable());
			return httpSecurity.build();
	}
	
}
