package com.taxiservice.microservicedriver.web.security.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taxiservice.microservicedriver.service.DriverService;

//@Component("driverSecrityExpressiom")
public class CustomDriverSecurityExpression {

	@Autowired
	private DriverService driverService;
	
	public boolean CanAccessDriver(Long id) {
		
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return false;
	}
}
