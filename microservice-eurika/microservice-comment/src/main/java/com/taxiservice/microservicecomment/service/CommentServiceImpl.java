package com.taxiservice.microservicecomment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taxiservice.microservicecomment.model.Comment;
import com.taxiservice.microservicecomment.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService{
	
	private final CommentRepository commentRepository;

	public CommentServiceImpl(CommentRepository commentRepository) {
		super();
		this.commentRepository = commentRepository;
	}

	@Override
	public Comment getById(Long id) {
		return commentRepository.findById(id).get();
	}

	@Override
	public List<Comment> getAllByDriverId(Long driverId) {
		return commentRepository.findByDriverId(driverId);
	}

	@Override
	public List<Comment> getAllByUserId(Long id) {
		return commentRepository.findByUserId(id);
	}

	@Override
	public Comment create(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public void update(Comment comment) {
		commentRepository.save(comment);
	}

	@Override
	public void delete(Long id) {
		commentRepository.deleteById(id);
		
	}

	
}
