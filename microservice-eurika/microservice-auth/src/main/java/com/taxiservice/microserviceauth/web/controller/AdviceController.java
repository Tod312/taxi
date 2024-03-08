package com.taxiservice.microserviceauth.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;


@RestControllerAdvice
public class AdviceController {

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> accessDined(AccessDeniedException e){
		e.printStackTrace();
		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body("Unauthorised");
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNoValid(MethodArgumentNotValidException e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("valid failed");
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException e) {
		e.printStackTrace();
		System.out.println("@ExceptionHandler(ConstraintViolationException.class)");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("valid failed");
	}
	
}
