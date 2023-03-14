package com.xr.pet.mng.sys.PetMngSys.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xr.pet.mng.sys.PetMngSys.Request.CommentDTO;
import com.xr.pet.mng.sys.PetMngSys.Request.CommentRequest;
import com.xr.pet.mng.sys.PetMngSys.Service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/{postId}/comments")
	public ResponseEntity<Object> addComment(@PathVariable Long postId, CommentRequest commentRequest) {
		return new ResponseEntity<>(commentService.addComment(postId, commentRequest), HttpStatus.OK);
	}

	@GetMapping("/comments")
	public List<CommentDTO> getAllCommentsOnPost(@RequestParam(required = true) long postId) {
		return commentService.getAllComments(postId);
	}
}
