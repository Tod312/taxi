package com.taxiservice.microservicecomment.web;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taxiservice.microservicecomment.model.Comment;
import com.taxiservice.microservicecomment.service.CommentService;

import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/comment")
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@GetMapping
	public Comment getComment(@RequestParam Optional<Long> id) {
		if(id.isEmpty()) {
			throw new ValidationException("Id can not be null");
		}
		return commentService.getById(id.get());
	}
	
	@PostMapping("/create")
	public Comment create(@RequestBody Comment comment) {
		return comment;
	}
	
	@PutMapping("/update")
	public ResponseEntity<Comment> update(@RequestBody Comment comment){
		commentService.update(comment);
		return new ResponseEntity<Comment>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Comment> delete(@RequestParam Optional<Long> id){
		if(id.isEmpty()) {
			throw new ValidationException("Id can not be null");
		}
		commentService.delete(id.get());
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
