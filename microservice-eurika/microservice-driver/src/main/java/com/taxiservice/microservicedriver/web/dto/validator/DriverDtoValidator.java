package com.taxiservice.microservicedriver.web.dto.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.taxiservice.microservicedriver.web.dto.driver.DriverDto;

public class DriverDtoValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return DriverDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

	private void validPassword() {
		
	}
	
	private void validLogin() {
		
	}
	
}
