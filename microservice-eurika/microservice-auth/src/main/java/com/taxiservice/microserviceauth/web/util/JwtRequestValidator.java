package com.taxiservice.microserviceauth.web.util;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.taxiservice.microserviceauth.model.User;
import com.taxiservice.microserviceauth.service.UserService;
import com.taxiservice.microserviceauth.web.dto.JwtRequest;

import jakarta.validation.ConstraintViolation;


public class JwtRequestValidator implements Validator{
	
	private final UserService userService;
	
	@Autowired
	private jakarta.validation.Validator validator;

	public JwtRequestValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(JwtRequest.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		JwtRequest jwtRequest = (JwtRequest) target;
		System.out.println("validate method");
		validateWithJavaValidation(jwtRequest, errors);
		//validateRegistrationLogin(jwtRequest.getUsername(),errors);
	}
	
	private void validateWithJavaValidation(JwtRequest jwtRequest, Errors errors) {
		
		Set<ConstraintViolation<JwtRequest>> constraints = validator.validate(jwtRequest);
		
		for (ConstraintViolation<JwtRequest> constraintViolation : constraints) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.rejectValue(propertyPath, "", message);
        }
	}
	
	private void validateRegistrationLogin(String username, Errors errors) {
		User old = userService.getByUsername(username);
		if(old != null) {
			errors.rejectValue("username", "username.exists", "username is already taken");
		}
	}

}
