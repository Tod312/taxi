package com.taxiservice.microservicecar.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taxiservice.microservicecar.model.Car;
import com.taxiservice.microservicecar.repository.CarRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueCarNumberContraint implements ConstraintValidator<UniqueCarNumber, String>{

	@Autowired
	private CarRepository carRepository;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Optional<Car> car = carRepository.findByNumber(value);
		return car.isEmpty();
	}

}
