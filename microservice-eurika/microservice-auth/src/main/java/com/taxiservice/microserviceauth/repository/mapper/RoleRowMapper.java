package com.taxiservice.microserviceauth.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.taxiservice.microserviceauth.model.Role;

@Component
public class RoleRowMapper implements RowMapper<Role>{

	@Override
	public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Role.valueOf(rs.getString("role"));
	}

}
