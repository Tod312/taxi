package com.taxiservice.microservicedriver.config;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

@Configuration
public class DataConfig {

	private final DataSource dataSource;
	
	
	public DataConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	public Connection getDatasource() {
		return DataSourceUtils.getConnection(dataSource);
	}
}
