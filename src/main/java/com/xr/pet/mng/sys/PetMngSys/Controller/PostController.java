package com.xr.pet.mng.sys.PetMngSys.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xr.pet.mng.sys.PetMngSys.Model.Post;
import com.xr.pet.mng.sys.PetMngSys.Request.PostDTO;
import com.xr.pet.mng.sys.PetMngSys.Service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	PostService postService;

	@PostMapping("/addPost")
	public ResponseEntity<Object> createPost(@RequestParam MultipartFile file, PostDTO postDTO) throws IOException {
		return new ResponseEntity<>(postService.addPost(file, postDTO), HttpStatus.CREATED);
	}

	@GetMapping
	public Page<Post> findAll(@RequestParam(required = false) String Tag,
			@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
		return postService.getAllPosts(Tag, pageNumber, pageSize);
	}

	@DeleteMapping("/delete/Post/{postId}/{userId}")
	public ResponseEntity<Object> deletePost(@PathVariable Long postId) {
		return new ResponseEntity<>(postService.delete(postId), HttpStatus.OK);
	}

	@PutMapping("/update/post/{postId}")
	public ResponseEntity<Object> updatePost(@PathVariable Long postId, @RequestParam MultipartFile image, PostDTO post)
			throws IOException {
		return new ResponseEntity<>(postService.updatePost(postId, image, post), HttpStatus.OK);
	}

}