package com.taxiservice.microservicedriver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taxiservice.microservicedriver.model.driver.Driver;
import com.taxiservice.microservicedriver.model.driver.Status;
import com.taxiservice.microservicedriver.model.expression.ResourceNotFoundException;
import com.taxiservice.microservicedriver.repository.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService{

	private final DriverRepository driverRepository;
	
	
	public DriverServiceImpl(DriverRepository driverRepository) {
		this.driverRepository = driverRepository;
	}

	@Override
	public Driver getById(Long id) {
		return driverRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Driver for this id=" + id + " not found"));
	}

	@Override
	public List<Driver> getByStatus(Status status) {
		return driverRepository.findByStatus(status);
	}

	@Override
	public Driver update(Driver driver) {
		driverRepository.save(driver);
		return driver;
	}
	
	@Override
	public String updateStatus(Long id, Status status) {
		Driver driver = driverRepository.findById(id).get();
		driver.setStatus(status);
		driverRepository.save(driver);
		return status.getClass().getName();
		
	}

	@Override
	public void delete(Long id) {
		driverRepository.deleteById(id);
	}

}
