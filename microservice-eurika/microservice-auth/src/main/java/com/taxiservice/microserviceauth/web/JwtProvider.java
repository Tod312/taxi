package com.taxiservice.microserviceauth.web;

import java.security.Key;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.taxiservice.microserviceauth.model.Role;
import com.taxiservice.microserviceauth.model.User;
import com.taxiservice.microserviceauth.service.UserService;
import com.taxiservice.microserviceauth.web.dto.JwtResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtProvider {

	private final JwtProperty jwtProperty;
	private final UserService userService;
	private final UserDetailsService detailsService;
	private Key key;
	
	@PostConstruct
	public void init() {
		key = Keys.hmacShaKeyFor(jwtProperty.getSecret().getBytes());
	}

	
	
	public JwtProvider(JwtProperty jwtProperty, UserService userService, UserDetailsService detailsService) {
		super();
		this.jwtProperty = jwtProperty;
		this.userService = userService;
		this.detailsService = detailsService;
	}
	
	public String createAccessToken(Long id, String username, List<Role> roles ) {
		Claims claims = Jwts.claims()
				.subject(username)
				.add("id", id)
				.add("roles", roles)
				.build();
		Date now = new Date();
		Date experation = Date.from(now.toInstant().plusSeconds(jwtProperty.getAccessExpiration()));
		return Jwts.builder()
				.claims(claims)
				.issuedAt(now)
				.expiration(experation)
				.signWith(key)
				.compact();
	}
	
	public String createRefreshToken(Long id, String username) {
		Claims claims = Jwts.claims()
				.subject(username)
				.add("id", id)
				.build();
		Date date = new Date();
		Date expiration = Date.from(ZonedDateTime.now().plus(jwtProperty.getRefreshExpiration(), ChronoUnit.SECONDS).toInstant());
		return Jwts.builder()
				.claims(claims)
				.issuedAt(date)
				.expiration(expiration)
				.signWith(key)
				.compact();
	}
	
	public JwtResponse refreshAccessToken(String refreshToken) {
		JwtResponse jwtResponse = new JwtResponse();
		if(!validToken(refreshToken)) {
			throw new AccessDeniedException("This token is not valid");
		}
		Long userId = Long.valueOf(getId(refreshToken));
		User user = userService.getById(userId);
		jwtResponse.setId(user.getId());
		jwtResponse.setUsername(user.getUsername());
		jwtResponse.setAccessToken(createAccessToken(user.getId(), user.getUsername(), user.getRoles()));
		jwtResponse.setRefreshToken(createRefreshToken(user.getId(), user.getUsername()));
		
		return jwtResponse;
	}
	
	public boolean validToken(String token) {
		Jws<Claims> claims = Jwts
				.parser()
				.verifyWith((SecretKey)key)
				.build()
				.parseSignedClaims(token);
		return claims.getPayload().getExpiration().after(new Date());
	}
	
	private String getId(String token) {
		return Jwts.parser()
				.verifyWith((SecretKey) key)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.get("id")
				.toString();
	}
	
	private String getUsername(String token) {
		return Jwts.parser()
				.verifyWith((SecretKey) key)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	public Authentication getAuthentication(String token) {
		String username = getUsername(token);
		UserDetails details = detailsService.loadUserByUsername(username);
		return new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
		
	}
}
