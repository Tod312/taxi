package com.taxiservice.microservicedriver.service;

import java.util.List;

import com.taxiservice.microservicedriver.model.driver.Driver;
import com.taxiservice.microservicedriver.model.driver.Status;

public interface DriverService {
	
	Driver getById(Long id);
	List<Driver> getByStatus(Status status);
	Driver update(Driver driver);
	void delete(Long id);
	String updateStatus(Long id, Status status);
}
