package com.taxiservice.microserviceauth.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.taxiservice.microserviceauth.model.Role;
import com.taxiservice.microserviceauth.model.User;

public class JwtEntityFactory {

	public static JwtEntity createJwtEntity(User user) {
		return new JwtEntity(
				user.getId(),
				user.getUsername(),
				user.getPassword(),
				mapRoleToGrantedAuthority(user.getRoles())
				);
				
	}
	
	private static List<GrantedAuthority> mapRoleToGrantedAuthority(List<Role> roles){
		return roles.stream().map(role -> role.name())
				.map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());
	}
}
