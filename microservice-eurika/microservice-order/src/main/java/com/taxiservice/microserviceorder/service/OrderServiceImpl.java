package com.taxiservice.microserviceorder.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.taxiservice.microserviceorder.model.Order;
import com.taxiservice.microserviceorder.model.Status;
import com.taxiservice.microserviceorder.model.Tariff;
import com.taxiservice.microserviceorder.model.exception.ResourceNotFoundException;
import com.taxiservice.microserviceorder.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{

	Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	private final OrderRepository orderRepository;
	private final RestTemplate restTemplate;
	
	
	public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
		this.orderRepository = orderRepository;
		this.restTemplate = restTemplate;
	}

	@Override
	public List<Order> getByDriverId(Long driverId) {
		return orderRepository.findByDriverId(driverId);
	}

	@Override
	public List<Order> getByClientId(Long clientId) {
		return orderRepository.findByClientId(clientId);
	}

	@Override
	public Order create(Order order) {
		Tariff tariff = order.getTariff();
		BigDecimal bigDecimal = tariff.getCost(order.getNumberAnimals(),
				order.getNumberBabychairs(), order.getNumberKm(), order.getNumberMinutes());
		order.setCost(bigDecimal);
		order.setCreationDate(LocalDate.now());
		order.setStatus(Status.WAITING);
		return orderRepository.save(order);
	}

	@Override
	public void update(Order order) {
		orderRepository.save(order);
	}

	@Override
	public Order getById(Long id) {
		return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("This order not found"));
	}

	@Override
	public void delete(Long id) {
		orderRepository.deleteById(id);
		
	}

	@Override
	public List<Order> getByStatus(Status status) {
		return orderRepository.findByStatus(status);
	}

	@Override
	public void confirmOrder(Long driverId, Long orderId) {
		Order order = getById(orderId);
		order.setDriverId(driverId);
		order.setStatus(Status.IN_PROGRESS);
		orderRepository.save(order);
		System.out.println("Confirm service method");
		String url = "http://localhost:8081/driver/updateStatus?driverId=" + driverId + "&status=BUSY";
		restTemplate.put(url, null);
		LOGGER.info("orderId=%d was acceped by driverId=%d", orderId, driverId);
	}
	
	@Override
	public void finishOrder(Long orderId) {
		Order order = getById(orderId);
		// также необходимо добавить снятие денег за поездку
		order.setStatus(Status.DONE);
		String url = "http://localhost:8080/driver/update?updateStatus=" + order.getDriverId() + "&status=FREE";
		restTemplate.put(url, null);
		LOGGER.info("orderId=%d completed", orderId);
	}

}
