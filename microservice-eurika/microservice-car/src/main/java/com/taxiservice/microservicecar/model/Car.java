package com.taxiservice.microservicecar.model;

import java.util.Objects;

import com.taxiservice.microservicecar.util.UniqueCarNumber;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cars")
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 8, max = 8, message = "Car number can not be more or less than 8")
	@UniqueCarNumber
	private String number;
	
	@Column(name = "car_make")
	@NotBlank
	private String carMake;
	
	@NotBlank
	private String model;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Status status;
	
	public Car() {}
	
	
	public Car(@Size(min = 8, max = 8, message = "Car number can not be more or less than 8") String number,
			String carMake, String model, Status status) {
		super();
		this.number = number;
		this.carMake = carMake;
		this.model = model;
		this.status = status;
	}


	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCarMake() {
		return carMake;
	}

	public void setCarMake(String carMake) {
		this.carMake = carMake;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		return Objects.equals(number, other.number);
	}
	
	
	
	
}
