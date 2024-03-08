package com.taxiservice.microservicecar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.taxiservice.microservicecar.model.Car;
import com.taxiservice.microservicecar.model.Status;

public interface CarRepository extends CrudRepository<Car, Long>{
	
	public Optional<Car> findByNumber(String number);
	public List<Car> findAllByStatus(Status status);
	public Car deleteByNumber(String number);
	

}
