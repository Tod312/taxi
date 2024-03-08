package com.taxiservice.microservicedriver.web.dto.driver;

import org.hibernate.validator.constraints.Length;

import com.taxiservice.microservicedriver.model.driver.Name;
import com.taxiservice.microservicedriver.web.dto.validator.OnCreate;
import com.taxiservice.microservicedriver.web.dto.validator.OnUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DriverDto {
	
	@NotNull(message = "Id can not be null",groups = OnUpdate.class)
	private Long id;
	
	private Name name;
	
	@Length(min = 11, max=11, message = "phone number cannot be more or less than 11 symbols, example 8 916 132 68 01 without white spaces")
	private String phone; 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
}
