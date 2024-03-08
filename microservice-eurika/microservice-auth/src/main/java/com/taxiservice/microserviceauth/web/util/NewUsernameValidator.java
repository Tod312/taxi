package com.taxiservice.microserviceauth.web.util;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taxiservice.microserviceauth.model.User;
import com.taxiservice.microserviceauth.service.UserService;
import com.taxiservice.microserviceauth.web.util.annotation.NewUsername;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class NewUsernameValidator implements ConstraintValidator<NewUsername, String>{

	@Autowired
	private UserService userService;

	public NewUsernameValidator() {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		System.err.println("NewUsernameValidator method");
		User user = userService.getByUsername(value);
		//System.err.println(user.getUsername());
		return user == null;
	}

}
