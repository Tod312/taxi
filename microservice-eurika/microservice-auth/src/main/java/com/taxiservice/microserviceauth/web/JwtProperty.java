package com.taxiservice.microserviceauth.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperty {

	@Value("${spring.jwt.secret}")
	private String secret;
	
	private long accessExpiration;
	
	private long refreshExpiration;

	
	public JwtProperty() {}


	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}


	public long getAccessExpiration() {
		return accessExpiration;
	}


	public void setAccessExpiration(@Value("$spring.jwt.accessExpiration") String accessExpiration) {
		this.accessExpiration = Long.valueOf(accessExpiration);
	}


	public long getRefreshExpiration() {
		return refreshExpiration;
	}


	public void setRefreshExpiration(@Value("$spring.jwt.refreshExpiration") String refreshExpiration) {
		this.refreshExpiration = Long.valueOf(refreshExpiration);
	}

	
	
	
}
