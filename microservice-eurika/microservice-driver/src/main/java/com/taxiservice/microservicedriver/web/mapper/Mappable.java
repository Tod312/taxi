package com.taxiservice.microservicedriver.web.mapper;

import java.util.List;

public interface Mappable<U, T> {
	
	U toEntity(T t);
	List<U> toEntities(List<T> dto);
	T toDto(U u);
	List<T> toDto(List<U> entities);

}
