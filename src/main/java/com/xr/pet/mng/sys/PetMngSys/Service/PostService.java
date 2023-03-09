package com.xr.pet.mng.sys.PetMngSys.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xr.pet.mng.sys.PetMngSys.Exception.UserException;
import com.xr.pet.mng.sys.PetMngSys.Model.Comment;
import com.xr.pet.mng.sys.PetMngSys.Model.Like;
import com.xr.pet.mng.sys.PetMngSys.Model.Post;
import com.xr.pet.mng.sys.PetMngSys.Repository.CommentRepository;
import com.xr.pet.mng.sys.PetMngSys.Repository.LikeRepository;
import com.xr.pet.mng.sys.PetMngSys.Repository.PostRepository;
import com.xr.pet.mng.sys.PetMngSys.Request.CommentRequest;
import com.xr.pet.mng.sys.PetMngSys.Request.PostDTO;
import com.xr.pet.mng.sys.PetMngSys.Utils.Constants;
import com.xr.pet.mng.sys.PetMngSys.Utils.Messages;
import com.xr.pet.mng.sys.PetMngSys.Utils.Utils;

@Service
public class PostService {

	@Autowired
	PostRepository postRepository;

	@Autowired
	LikeRepository likeRepository;

	@Autowired
	CommentRepository commentRepository;

	public Post findByPostId(long postId) {
		return postRepository.findById(postId);
	}

	public Post addPost(MultipartFile file, PostDTO post) throws IOException {
		Post newpost = new Post();
		newpost.setTag(post.getTag());
		newpost.setLocation(post.getLocation());
		newpost.setDescription(post.getDescription());
		if (file != null) {
			newpost.setFilePath(Utils.saveFile(file, Constants.PROFILE_PREFIX));

		}
		return postRepository.save(newpost);
	}

	public Post addLike(long postId) {
		Post post = findByPostId(postId);
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

		return postRepository.save(post);
	}

	public Post addComment(Long postId, CommentRequest commentRequest) {
		Post post = findByPostId(postId);
		if (post == null) {
			throw new UserException(Messages.POST_NOT_FOUND);
		}
		Comment comment = new Comment();
		comment.setPostId(postId);
		if (commentRequest.getText() == null || commentRequest.getText().isEmpty()) {
			throw new UserException(Messages.ENTER_TEXT);
		}
		comment.setText(commentRequest.getText());
		comment.setUserId(Utils.getJwtUserId());
		commentRepository.save(comment);
//		post.getComments().add(comment);
		return postRepository.save(post);
	}

	public Post removeLike(Long postId) {

		Post post = findByPostId(postId);
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
		return postRepository.save(post);
	}

	public Page<Post> getAllPosts(String tag, int pageNumber, int pageSize) {
		if (tag != null) {
			Pageable pageable = new PageRequest(pageNumber - 1, pageSize,
					new Sort(new Order(Direction.DESC, "creationDate"), new Order(Direction.ASC, "tag")));
			return postRepository.findAllByTagContainingIgnoreCase(tag, pageable);
		} else {
			Pageable pageable = new PageRequest(pageNumber - 1, pageSize,
					new Sort(new Order(Direction.DESC, "creationDate")));
			return postRepository.findAll(pageable);
		}
	}

	public List<Comment> getAllComments(Long postId) {
		Post post = postRepository.findById(postId);
		if (post == null) {
			throw new UserException(Messages.POST_NOT_FOUND);
		}
		return commentRepository.findAllByPostId(postId);
	}

}
