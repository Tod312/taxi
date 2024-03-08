package com.taxiservice.microservicecar.web.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taxiservice.microservicecar.model.Car;
import com.taxiservice.microservicecar.model.Status;
import com.taxiservice.microservicecar.service.CarService;

import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;


@RestController
@RequestMapping("/car")
public class CarController {

	public final CarService carService;

	public CarController(@Autowired CarService carService) {
		this.carService = carService;
	}
	
	@GetMapping
	public ResponseEntity<Car> getCar(@RequestParam Optional<Long> id) {
		if(id.isEmpty()) {
			throw new ValidationException("Id cannot be null");
		}
		return ResponseEntity.status(HttpStatus.OK).body(carService.getById(id.get()));
	}
	
	@GetMapping("/byNumber")
	public ResponseEntity<Car> getCarByNUmber(@RequestParam String number) {
		if(number.length() > 8 || number.length() < 8 || number.isBlank()) {
			throw new ValidationException("Bad valid");
		}
		return ResponseEntity.status(HttpStatus.OK).body(carService.getByNumber(number));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Car>> getAllCar(@RequestParam(required = false) Status status) {
		if(status == null) {
			return ResponseEntity.status(HttpStatus.OK).body(carService.getAll());
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(carService.getAllByStatus(status));
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<Car> create(@Validated @RequestBody Car car) {
		Car createCar = carService.create(car);
		return ResponseEntity.status(HttpStatus.CREATED).body(createCar);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> update(@Validated @RequestBody Car car) {
		carService.create(car);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Resource updated successfully");
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@RequestParam Optional<Long> id) {
		if(id.isEmpty()) {
			throw new ValidationException("Id cannot be null");
		}
		
		carService.deleteById(id.get());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Resource delete successfully");
	}
}
