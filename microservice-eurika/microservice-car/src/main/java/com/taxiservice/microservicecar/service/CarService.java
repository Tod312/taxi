package com.taxiservice.microservicecar.service;

import java.util.List;
import java.util.Optional;

import com.taxiservice.microservicecar.model.Car;
import com.taxiservice.microservicecar.model.Status;

public interface CarService {

	public Car getById(Long id);
	
	public Car getByNumber(String number);
	
	public List<Car> getAll();
	
	public Car create(Car car);
	
	public Car update(Car car);
	
	public void deleteByNumber(String number);
	
	public List<Car> getAllByStatus(Status status);
	
	public void deleteById(Long id);
}
