package com.xr.pet.mng.sys.PetMngSys.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xr.pet.mng.sys.PetMngSys.Exception.UserException;
import com.xr.pet.mng.sys.PetMngSys.Model.Like;
import com.xr.pet.mng.sys.PetMngSys.Model.Post;
import com.xr.pet.mng.sys.PetMngSys.Model.UserMaster;
import com.xr.pet.mng.sys.PetMngSys.Repository.LikeRepository;
import com.xr.pet.mng.sys.PetMngSys.Request.LikesDTO;
import com.xr.pet.mng.sys.PetMngSys.Utils.Messages;
import com.xr.pet.mng.sys.PetMngSys.Utils.Utils;

@Service
public class LikeService {

	@Autowired
	LikeRepository likeRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;

	public List<LikesDTO> getAllLikes(Long postId) {
		List<Like> likes = likeRepository.findByPostId(postId);
		List<LikesDTO> likeDtos = new ArrayList<>();

		for (Like like : likes) {
			UserMaster user = userService.findUserById(like.getUserId());
			LikesDTO likeDto = new LikesDTO(user.getFirstName(), user.getLastName());
			likeDtos.add(likeDto);
		}

		return likeDtos;
	}

	public Post addLike(long postId) {
		Post post = postService.findByPostId(postId);
		if (post == null) {
			throw new UserException(Messages.POST_NOT_FOUND);
		}

		int userId = Utils.getJwtUserId();
		Like like = likeRepository.findByPostIdAndUserId(postId, userId);

		if (like != null) {
			throw new UserException(Messages.ALREADY_LIKED);
		}

		like = new Like();
		like.setPostId(postId);
		like.setUserId(userId);
		like.setLikes(1L);

		like = likeRepository.save(like); // Save the Like object and retrieve it from the database
		post.setLikes(post.getLikes() + like.getLikes()); // Increment the likes count using the retrieved Like object

		return postService.save(post);
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

	public List<Like> findByPostId(long postId) {
		List<Like> likes = likeRepository.findByPostId(postId);
		return likes;
	}

	public void delete(Like like) {
		likeRepository.delete(like);
	}

}
