package com.taxiservice.microservicedriver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.taxiservice.microservicedriver.model.driver.Driver;
import com.taxiservice.microservicedriver.model.driver.Status;

public interface DriverRepository extends CrudRepository<Driver, Long>{

	Optional<Driver> findByLogin(String login);
	List<Driver> findByStatus(Status status);
}
