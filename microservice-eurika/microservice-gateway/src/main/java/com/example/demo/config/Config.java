package com.example.demo.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.config.AuthenticationFilter.Conf;

@Configuration
public class Config {

	 @Bean
	 @LoadBalanced
	 public WebClient.Builder loadBalancedWebClientBuilder() {
		 return WebClient.builder();
	 }

	@Bean
	public RouteLocator locator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("driver", r -> r.path("/driver/**")
						.filters(f -> f.filter(new AuthenticationFilter(loadBalancedWebClientBuilder()).apply(new Conf())))
						.uri("lb://DRIVER"))
				.route("order", r -> r.path("/order/**")
						.filters(f -> f.filter(new AuthenticationFilter(loadBalancedWebClientBuilder()).apply(new Conf())))
						.uri("lb://ORDER"))
				.route("auth", r -> r.path("/auth/**")
						.uri("lb://AUTH"))
				.build();
	}
}
