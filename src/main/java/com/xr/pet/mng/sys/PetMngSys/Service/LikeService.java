package com.xr.pet.mng.sys.PetMngSys.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.xr.pet.mng.sys.PetMngSys.Exception.UserException;
import com.xr.pet.mng.sys.PetMngSys.Model.Like;
import com.xr.pet.mng.sys.PetMngSys.Model.Post;
import com.xr.pet.mng.sys.PetMngSys.Model.UserMaster;
import com.xr.pet.mng.sys.PetMngSys.Repository.LikeRepository;
import com.xr.pet.mng.sys.PetMngSys.Repository.PostRepository;
import com.xr.pet.mng.sys.PetMngSys.Request.LikesDTO;
import com.xr.pet.mng.sys.PetMngSys.Utils.Messages;
import com.xr.pet.mng.sys.PetMngSys.Utils.Utils;

@Service
public class LikeService {

	@Autowired
	LikeRepository likeRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserService userService;

	@Autowired
	PostService postService;

	public List<LikesDTO> getAllLikesOnPost(Long postId) {
		Post post = postService.findByPostId(postId);
		if(post==null) {
			throw new UserException(Messages.POST_NOT_FOUND);
		}
		return likeRepository.findByPostId(postId);
	}

	public Post addLike(Long postId) {
		int userId = Utils.getJwtUserId();
		Post post = postService.findByPostId(postId);
		if (post == null) {
			throw new UserException(Messages.POST_NOT_FOUND);
		}
		UserMaster user = userService.findUserById(userId);
		if (user == null) {
			throw new UserException(Messages.USER_NOT_FOUND);
		}
		Like like = likeRepository.findByPostIdAndUserId(postId, userId);

		if (like != null) {
			throw new UserException(Messages.ALREADY_LIKED);
		}

		like = new Like();
		like.setLikes(1L);
		like.setUser(user);
		like.setPost(post);
		try {
			likeRepository.save(like);
		} catch (DataIntegrityViolationException e) {
			throw new UserException(Messages.ALREADY_LIKED);
		}
		post.setLikes(post.getLikes() + like.getLikes());
		return postRepository.save(post);
	}

	public Post removeLike(Long postId) {

		Post post = postService.findByPostId(postId);
		if (post == null) {
			throw new UserException(Messages.POST_NOT_FOUND);
		}
		int userId = Utils.getJwtUserId();
		Like like = likeRepository.findByPostIdAndUserId(postId, userId);
		if (like == null) {
			throw new UserException(Messages.DIDNT_LIKED);
		}
		long dellike = post.getLikes();
		likeRepository.delete(like);
		// likeRepository.save(like);
		dellike--;
		post.setLikes(dellike);
		return postService.save(post);
	}

	public List<LikesDTO> findByPostId(long postId) {
		List<LikesDTO> likes = likeRepository.findByPostId(postId);
		return likes;
	}

	public void delete(Like like) {
		likeRepository.delete(like);
	}

}
