package com.taxiservice.microserviceauth.repository.Impl;

import java.util.List;

import com.taxiservice.microserviceauth.model.Role;

public interface JdbcRoleRepository {
	
	public List<Role> findByDriverId(Long id);

	public void create(Long driverId, List<Role> roles);
	
	public void create(Long driverId, Role role);
	
	public void delete(Long driverId, Role role);
	
	public void delete(Long driverId, List<Role> role);
}
