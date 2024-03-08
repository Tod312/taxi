package com.taxiservice.microservicecomment.service;

import java.util.List;

import com.taxiservice.microservicecomment.model.Comment;

public interface CommentService {

	public Comment getById(Long id);
	
	public List<Comment> getAllByDriverId(Long driverId);
	public List<Comment> getAllByUserId(Long id);
	
	public Comment create(Comment comment);
	
	public void update(Comment comment);
	
	public void delete(Long id);
}
