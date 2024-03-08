package com.taxiservice.microservicedriver.web.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.taxiservice.microservicedriver.model.driver.Driver;
import com.taxiservice.microservicedriver.model.driver.Name;
import com.taxiservice.microservicedriver.web.dto.driver.DriverDto;

@Component
public class DriverMapperImpl implements DriverMapper{

	@Override
	public Driver toEntity(DriverDto t) {
		
		Driver driver = new Driver();
		
		driver.setId(t.getId());
		driver.setName(t.getName());
		
		return driver;
	}

	@Override
	public List<Driver> toEntities(List<DriverDto> dto) {
		List<Driver> drivers = new ArrayList<Driver>(dto.size());
		for(DriverDto d : dto) {
			drivers.add(toEntity(d));
		}
		return drivers;
	}

	@Override
	public DriverDto toDto(Driver u) {
		DriverDto driverDto = new DriverDto();
		
		
		driverDto.setId(u.getId());
		driverDto.setName(u.getName());
		

		return driverDto;
	}

	@Override
	public List<DriverDto> toDto(List<Driver> entities) {
		List<DriverDto> dtoList = entities.stream().map(e -> toDto(e)).collect(Collectors.toList());
		return dtoList;
	}

}
