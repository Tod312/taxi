package com.example.demo.config;


import org.springframework.cloud.gateway.filter.GatewayFilter;

import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.server.ResponseStatusException;




public class AuthenticationFilter extends AbstractGatewayFilterFactory<com.example.demo.config.AuthenticationFilter.Conf>{

	private final WebClient.Builder webClientl;
	
	

	
	public AuthenticationFilter(Builder webClientl) {
		this.webClientl = webClientl;
	}

	public static class Conf{}

	@Override
	public GatewayFilter apply(Conf config) {
		return ((exchange, chain) -> {
			if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorised");
				
			}
			String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			if(authHeader != null && authHeader.startsWith("Bearer ")) {
				authHeader = authHeader.substring(7);
			}
			 try {
//               //REST call to AUTH service
//               template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
               //jwtUtil.validateToken(authHeader);
				 webClientl.build()
				 .get()
				 .uri("http://auth/validate?accessToken=", authHeader)
				 .retrieve().bodyToMono(String.class);
				 

           } catch (Exception e) {
               System.out.println("invalid access...!");
               throw new RuntimeException("unauthorized access to application");
           }
			 return chain.filter(exchange);
		});
	}


}

