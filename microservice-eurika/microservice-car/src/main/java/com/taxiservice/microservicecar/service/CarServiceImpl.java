package com.taxiservice.microservicecar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.taxiservice.microservicecar.model.Car;
import com.taxiservice.microservicecar.model.Status;
import com.taxiservice.microservicecar.model.exception.ResourceNotFoundException;
import com.taxiservice.microservicecar.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService{

	private final CarRepository carRepository;
	
	public CarServiceImpl(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	@Override
	public Car getById(Long id) {
		return carRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Car with id:" + id + " not found"));
	}
	
	@Override
	public Car getByNumber(String number) {
		return carRepository.findByNumber(number)
				.orElseThrow(() -> new ResourceNotFoundException("Car with this number=" + number + " not found"));
	}

	@Override
	public List<Car> getAll() {
		List<Car> cars = new ArrayList<>();
		carRepository.findAll().forEach(car -> cars.add(car));
		return cars;
	}

	@Override
	public Car create(Car car) {
		return carRepository.save(car);
	}

	@Override
	public Car update(Car car) {
		return carRepository.save(car);
	}

	@Override
	public void deleteByNumber(String number) {
		Car car = carRepository.deleteByNumber(number);
	}

	@Override
	public List<Car> getAllByStatus(Status status) {
		return carRepository.findAllByStatus(status);
	}

	@Override
	public void deleteById(Long id) {
		carRepository.deleteById(id);
		
	}

}
