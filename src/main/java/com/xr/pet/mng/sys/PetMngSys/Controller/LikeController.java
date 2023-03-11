package com.xr.pet.mng.sys.PetMngSys.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xr.pet.mng.sys.PetMngSys.Model.Like;
import com.xr.pet.mng.sys.PetMngSys.Request.LikesDTO;
import com.xr.pet.mng.sys.PetMngSys.Service.LikeService;

@RestController
@RequestMapping("/like")
public class LikeController {

	@Autowired
	LikeService likeService;

	@PostMapping("/{postId}/likes")
	public ResponseEntity<Object> addLikeToPost(@PathVariable("postId") Long postId) {
		return new ResponseEntity<>(likeService.addLike(postId), HttpStatus.OK);
	}
//	Get List of Likes on Java Spring boot  

	@DeleteMapping("/{postId}/removeLike")
	public ResponseEntity<Object> removeLike(@PathVariable("postId") Long postId) {
		return new ResponseEntity<>(likeService.removeLike(postId), HttpStatus.OK);
	}

	@GetMapping("/posts/{postId}/likes")
	public List<LikesDTO> getAllLikesOnPost(@PathVariable Long postId) {
		return likeService.getAllLikesOnPost(postId);
	}
}
