package com.xr.pet.mng.sys.PetMngSys.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xr.pet.mng.sys.PetMngSys.Repository.LikeRepository;
import com.xr.pet.mng.sys.PetMngSys.Repository.PostRepository;
import com.xr.pet.mng.sys.PetMngSys.Service.LikeService;

@RestController
@RequestMapping("/like")
public class LikeController {

	@Autowired
	LikeService likeService;

	@Autowired
	LikeRepository likeRepository;
	@Autowired
	PostRepository postRepository;

	@PostMapping("/{postId}/likes")
	public ResponseEntity<Object> addLikeToPost(@PathVariable("postId") Long postId) {
		return new ResponseEntity<>(likeService.addLiketoPost(postId), HttpStatus.OK);
	}

	@GetMapping("/likes/{postId}")
	public ResponseEntity<Object> getAllLikesOnPost(@PathVariable("postId") long postId) {
		return new ResponseEntity<>(likeService.getAllLikesOnPost(postId), HttpStatus.OK);
	}

	@PostMapping("/{postId}/dislike")
	public ResponseEntity<Object> dislikePost(@PathVariable Long postId) {
		likeService.removeLike(postId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
