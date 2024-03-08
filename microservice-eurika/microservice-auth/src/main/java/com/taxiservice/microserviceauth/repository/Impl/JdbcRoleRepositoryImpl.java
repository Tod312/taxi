package com.taxiservice.microserviceauth.repository.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.taxiservice.microserviceauth.model.Role;

@Repository
public class JdbcRoleRepositoryImpl implements JdbcRoleRepository{

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Role> rowMapper;
	
	private final String SQL_CREATE= 
			"INSERT INTO users_roles(driver_id, role) "
			+ "VALUES(:driverId, :role)";
	
	private final String SQL_FIND_BY_DRIVER_ID= 
			"SELECT * FROM users_role "
			+ "WHERE driver_id = :id";
	
	private final String SQL_DELETE_BY_DRIVER_ID_AND_ROLE= 
			"SELECT * FROM users_role "
			+ "WHERE driver_id = :id AND role = :role";

	public JdbcRoleRepositoryImpl(JdbcTemplate jdbcTemplate, RowMapper<Role> rowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.rowMapper = rowMapper;
	}

	@Override
	public List<Role> findByDriverId(Long id) {
		return jdbcTemplate.query(SQL_FIND_BY_DRIVER_ID, rowMapper);
	}
	
	@Override
	public void create(Long driverId, List<Role> roles) {
		List<Object[]> batchArgs = new ArrayList<>();
		
		for(Role role : roles) {
			Object[] args = {driverId, role.name()};
			batchArgs.add(args);
		}
		jdbcTemplate.batchUpdate(SQL_CREATE, batchArgs);
	}

	@Override
	public void create(Long driverId, Role role) {
		Object[] args = {driverId, role.name()};
		jdbcTemplate.update(SQL_CREATE, args);
	}

	@Override
	public void delete(Long driverId, Role role) {
		Object[] args = {driverId, role};
		jdbcTemplate.update(SQL_DELETE_BY_DRIVER_ID_AND_ROLE, args);
		
	}

	@Override
	public void delete(Long driverId, List<Role> roles) {
		List<Object[]> batchArgs = new ArrayList<>();
		
		for(Role role : roles) {
			Object[] args = {driverId, role.name()};
			batchArgs.add(args);
		}
		jdbcTemplate.batchUpdate(SQL_DELETE_BY_DRIVER_ID_AND_ROLE, batchArgs);
		
	}
	
}
