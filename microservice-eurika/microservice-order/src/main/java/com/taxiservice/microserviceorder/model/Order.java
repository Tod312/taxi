package com.taxiservice.microserviceorder.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name ="client_id")
	@NotNull
	private Long clientId;
	
	@Column(name ="driver_id")
	private Long driverId;
	
	@Column(name = "creation_date")
	private LocalDate creationDate;
	
	@Column(name = "cost")
	private BigDecimal cost;
	
	@Column(name = "comment_id")
	private Long commentId;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Enumerated(EnumType.STRING)
	private Tariff tariff;
	
	@Column(name = "from_location")
	private String fromLocation;
	
	@Column(name = "to_location")
	private String toLocation;

	@Transient
	private int numberAnimals;
	@Transient
	private int numberBabychairs;
	
	@Column(name = "number_km")
	private Integer numberKm;
	
	@Column(name = "number_minutes")
	private Integer numberMinutes;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	
	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Tariff getTariff() {
		return tariff;
	}

	public void setTariff(Tariff tariff) {
		this.tariff = tariff;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public String getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getToLocation() {
		return toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public int getNumberAnimals() {
		return numberAnimals;
	}

	public void setNumberAnimals(int numberAnimals) {
		this.numberAnimals = numberAnimals;
	}

	public int getNumberBabychairs() {
		return numberBabychairs;
	}

	public void setNumberBabychairs(int numberBabychairs) {
		this.numberBabychairs = numberBabychairs;
	}

	public Integer getNumberKm() {
		return numberKm;
	}

	public void setNumberKm(Integer numberKm) {
		this.numberKm = numberKm;
	}

	public Integer getNumberMinutes() {
		return numberMinutes;
	}

	public void setNumberMinutes(Integer numberMinutes) {
		this.numberMinutes = numberMinutes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", clientId=" + clientId + ", driverId=" + driverId + ", creationDate="
				+ creationDate + ", cost=" + cost + ", commentId=" + commentId + ", status=" + status + ", tariff="
				+ tariff + ", fromLocation=" + fromLocation + ", toLocation=" + toLocation + ", numberAnimals="
				+ numberAnimals + ", numberBabychairs=" + numberBabychairs + ", numberKm=" + numberKm
				+ ", numberMinutes=" + numberMinutes + "]";
	}


	
	
	
	
}
