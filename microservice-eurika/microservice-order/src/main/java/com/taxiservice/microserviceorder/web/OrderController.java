package com.taxiservice.microserviceorder.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taxiservice.microserviceorder.model.Order;
import com.taxiservice.microserviceorder.model.Status;
import com.taxiservice.microserviceorder.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderController {

	
	private final OrderService orderService;

	public OrderController(@Autowired OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping
	public Order order(@RequestParam Long id) {
		return orderService.getById(id);
	}
	
	@PutMapping
	public void confirmOrder(@RequestBody Order order, @RequestParam Long driverId) {
		order.setDriverId(driverId);
		orderService.update(order);
		//Должна быть проверка на то, что на водителе нету заказа со статусом IN_PROGRESS
		//иначе выдать ошибку "Завершите текущий заказ"
	}
	
	@GetMapping("/driver")
	public List<Order> getDriverOrders(@RequestParam Long driverId) {
		return orderService.getByDriverId(driverId);
	}
	
	@GetMapping("/user")
	public List<Order> getClientOrders(@RequestParam Long clientId){
		return orderService.getByClientId(clientId);
	}
	
	@GetMapping("/free")
	public List<Order> getWaitingOrders(){
		return orderService.getByStatus(Status.WAITING);
	}
	
	@PostMapping("/create")
	public Order create(@RequestBody Order order, HttpServletRequest request){
		System.out.println(order);
		return orderService.create(order);
	}
	
	@PutMapping("/update")
	public void update(@RequestBody Order order){
		 orderService.create(order);
	}
	
	@PutMapping("/confirm")
	public void confirm(@RequestParam Long driverId, Long orderId){
		System.out.println("Confirm method");
		orderService.confirmOrder(driverId, orderId);
	}
	
	@DeleteMapping("/delete")
	public void delete(@RequestParam Long id){
		orderService.delete(id);
	}
	
	
}
