package com.taxiservice.microserviceauth.web.dto;


public class JwtResponse {
	
	private Long id;
	private String username;
	private String accessToken;
	private String refreshToken;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public static Builder newBuilder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private JwtResponse jwtResponse;
		
		private Builder() {
			jwtResponse = new JwtResponse();
		}
		
		public Builder username(String username) {
			jwtResponse.username = username;
			return this;
		}
		public Builder accessToken(String accessToken) {
			jwtResponse.accessToken = accessToken;
			return this;
		}
		public Builder refreshToken(String refreshToken) {
			jwtResponse.refreshToken = refreshToken;
			return this;
		}
		
		public JwtResponse build() {
			return jwtResponse;
		}
	}

}
