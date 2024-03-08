package com.taxiservice.microservicecomment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringConfig {

	
	@Bean
	public SecurityFilterChain chain(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.anyRequest().permitAll())
			.csrf(t -> t.disable());
		return http.build();
	}
}
