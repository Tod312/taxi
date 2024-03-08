package com.taxiservice.microserviceorder.service;

import java.util.List;
import java.util.Optional;

import com.taxiservice.microserviceorder.model.Order;
import com.taxiservice.microserviceorder.model.Status;

public interface OrderService {

	public List<Order> getByDriverId(Long driveridd);
	
	public List<Order> getByClientId(Long clientId);
	
	public Order getById(Long id);
	
	public List<Order> getByStatus(Status status);
	public Order create(Order order);
	public void update(Order order);
	public void delete(Long id);
	public void confirmOrder(Long driverId, Long orderId);
	public void finishOrder(Long orderId);
}
