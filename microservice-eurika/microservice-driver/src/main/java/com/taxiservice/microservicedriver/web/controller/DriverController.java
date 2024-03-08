package com.taxiservice.microservicedriver.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taxiservice.microservicedriver.model.driver.Driver;
import com.taxiservice.microservicedriver.model.driver.Status;
import com.taxiservice.microservicedriver.service.DriverService;
import com.taxiservice.microservicedriver.web.dto.driver.DriverDto;
import com.taxiservice.microservicedriver.web.mapper.DriverMapper;

import jakarta.validation.ValidationException;


@RestController
@RequestMapping("/driver")
public class DriverController {

	private final DriverService driverService;
	private final DriverMapper driverMapper;

	public DriverController(DriverService driverService, DriverMapper driverMapper) {
		this.driverService = driverService;
		this.driverMapper = driverMapper;
	}
	
	@GetMapping
	public DriverDto getUser(@RequestParam Optional<Long> id) {
		if(id.isEmpty()) {
			throw new ValidationException("Id cannot be null");
		}
		Driver deriver = driverService.getById(id.get());
		DriverDto dto = driverMapper.toDto(deriver);
		return dto;
	}

	
	@GetMapping("/free_drivers")
	public List<DriverDto> getUserByStatus(@RequestParam Status status){
		List<DriverDto> dto = driverMapper.toDto(driverService.getByStatus(status));
		return dto;
	}
	
	
	@PutMapping("/update")
	public void update(@RequestBody DriverDto dto) {
		Driver driver = driverMapper.toEntity(dto);
		driverService.update(driver);
	}
	
	@PutMapping("/updateStatus")
	public ResponseEntity<String> update(@RequestParam Optional<Long> driverId, @RequestParam Status status) {
		if(driverId.isEmpty()) {
			throw new ValidationException("driverId cannot be null");
		}
		driverService.updateStatus(driverId.get(), status);
		return ResponseEntity.ok().body("Status was changed to good");
	}
	
	@DeleteMapping("/delete")
	public void delete(@RequestParam Optional<Long> id) {
		if(id.isEmpty()) {
			throw new ValidationException("Id cannot be null");
		}
		driverService.delete(id.get());
	}
	
}
