package com.taxiservice.microservicecomment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.taxiservice.microservicecomment.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long>{
	
	public List<Comment> findByDriverId(Long driverId);
	public List<Comment> findByUserId(Long userId);
}
