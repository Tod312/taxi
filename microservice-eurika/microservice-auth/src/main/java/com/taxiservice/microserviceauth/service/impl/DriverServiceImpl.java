package com.taxiservice.microserviceauth.service.impl;

import org.springframework.stereotype.Service;

import com.taxiservice.microserviceauth.model.Driver;
import com.taxiservice.microserviceauth.repository.DriverRepository;
import com.taxiservice.microserviceauth.service.DriverService;

@Service
public class DriverServiceImpl implements DriverService{
	
	private final DriverRepository driverRepository;

	
	public DriverServiceImpl(DriverRepository driverRepository) {
		this.driverRepository = driverRepository;
	}

	@Override
	public Driver create(Driver driver) {
		return driverRepository.save(driver);
	}

}
