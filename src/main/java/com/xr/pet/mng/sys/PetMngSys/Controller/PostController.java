package com.xr.pet.mng.sys.PetMngSys.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xr.pet.mng.sys.PetMngSys.Model.Comment;
import com.xr.pet.mng.sys.PetMngSys.Model.Post;
import com.xr.pet.mng.sys.PetMngSys.Request.CommentRequest;
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

	@PostMapping("/{postId}/likes")
	public ResponseEntity<Object> addLikeToPost(@PathVariable("postId") Long postId) {
		return new ResponseEntity<>(postService.addLike(postId), HttpStatus.OK);
	}

	@PostMapping("/{postId}/comments")
	public ResponseEntity<Object> addComment(@PathVariable Long postId, CommentRequest commentRequest) {
		return new ResponseEntity<>(postService.addComment(postId, commentRequest), HttpStatus.OK);
	}

	@DeleteMapping("/{postId}/removeLike")
	public ResponseEntity<Object> removeLike(@PathVariable("postId") Long postId) {
		return new ResponseEntity<>(postService.removeLike(postId), HttpStatus.OK);
	}

	@GetMapping
	public Page<Post> findAll(@RequestParam(required = false) String Tag,
			@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
		return postService.getAllPosts(Tag, pageNumber, pageSize);
	}
	
	@GetMapping("/comments")
	public List<Comment> findAll(@RequestParam(required = true) long postId){
		return postService.getAllComments(postId);
	}

//	@DeleteMapping("/{postId}/deleteComment")
//	public ResponseEntity<Object> deleteComment(@PathVariable("postId") Long postId){
//		return new ResponseEntity<>(postService.deleteComment(postId),HttpStatus.OK);
//	}
//	

}