package com.taxiservice.microserviceorder.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.taxiservice.microserviceorder.model.Order;
import com.taxiservice.microserviceorder.model.Status;

public interface OrderRepository extends CrudRepository<Order, Long>{

	public List<Order> findByDriverId(Long driverId);
	public List<Order> findByDriverIdAndStatus(Long driverId, Status status);
	
	public List<Order> findByClientId(Long clientId);
	public List<Order> findByClientIdAndStatus(Long clientId, Status status);
	
	public List<Order> findByStatus(Status status);
	
	public List<Order> findByCreationDate(LocalDate creationDate);
	
	public void deleteByCreationDate(LocalDate creationDate);
	
}
