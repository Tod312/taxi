package com.taxiservice.microserviceeurika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MicroserviceEurikaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceEurikaApplication.class, args);
	}

}
