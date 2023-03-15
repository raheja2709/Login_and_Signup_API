package com.xr.pet.mng.sys.PetMngSys.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xr.pet.mng.sys.PetMngSys.Exception.UserException;
import com.xr.pet.mng.sys.PetMngSys.Model.Like;
import com.xr.pet.mng.sys.PetMngSys.Model.Post;
import com.xr.pet.mng.sys.PetMngSys.Repository.LikeRepository;
import com.xr.pet.mng.sys.PetMngSys.Repository.UserRepository;
import com.xr.pet.mng.sys.PetMngSys.Request.LikesDTO;
import com.xr.pet.mng.sys.PetMngSys.Utils.Messages;
import com.xr.pet.mng.sys.PetMngSys.Utils.Utils;

@Service
public class LikeService {

	@Autowired
	LikeRepository likeRepository;

	@Autowired
	PostService postService;

	@Autowired
	UserRepository userRepository;

	public Post addLiketoPost(long postId) {
		Post post = postService.findByPostId(postId);
		int userId = Utils.getJwtUserId();
		if (post == null) {
			throw new UserException(Messages.POST_NOT_FOUND);
		}
		Like existingLike = likeRepository.findByPostIdAndUserId(postId, userId);
		if (existingLike != null) {
			throw new UserException(Messages.ALREADY_LIKED);
		}
		Like like = new Like();
		like.setUserId(userId);
		like.setPostId(postId);
		like = likeRepository.save(like); // save the new Like entity

		return post;
	}


	public List<LikesDTO> getUsersWhoLikedPost(long postId) {
		Post post = postService.findByPostId(postId);
		if (post == null) {
			throw new UserException(Messages.POST_NOT_FOUND);
		}
		List<LikesDTO> userslikes = likeRepository.getListOfLikesOnPost(postId);
		return userslikes;
	}

	public void removeLike(Long postId) {

		Post post = postService.findByPostId(postId);
		if (post == null) {
			throw new UserException(Messages.POST_NOT_FOUND);
		}
		int userId = Utils.getJwtUserId();
		Like like = likeRepository.findByPostIdAndUserId(postId, userId);
		if (like == null) {
			throw new UserException(Messages.DIDNT_LIKED);
		}
		likeRepository.delete(like);

	}

}
