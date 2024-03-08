package com.taxiservice.microserviceauth.repository.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.taxiservice.microserviceauth.model.User;

@Repository
public class JdbcUserRepositoryImpl implements JdbcUserRepository{

	private final JdbcTemplate jdbcTemplate;
	
	private final RowMapper<User> rowMapper;
	
	public JdbcUserRepositoryImpl(@Autowired JdbcTemplate jdbcTemplate,@Autowired RowMapper<User> rowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.rowMapper = rowMapper;
	}

	@Override
	public Optional<User> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> findByUsername(String Username) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
