package com.taxiservice.microserviceauth.repository;

import org.springframework.data.repository.Repository;

import com.taxiservice.microserviceauth.model.Driver;

public interface DriverRepository extends Repository<Driver, Long>{

	public Driver save(Driver driver);
}
