package com.xr.pet.mng.sys.PetMngSys.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xr.pet.mng.sys.PetMngSys.Exception.UserException;
import com.xr.pet.mng.sys.PetMngSys.Model.Comment;
import com.xr.pet.mng.sys.PetMngSys.Model.Post;
import com.xr.pet.mng.sys.PetMngSys.Repository.CommentRepository;
import com.xr.pet.mng.sys.PetMngSys.Request.CommentDTO;
import com.xr.pet.mng.sys.PetMngSys.Request.CommentRequest;
import com.xr.pet.mng.sys.PetMngSys.Utils.Messages;
import com.xr.pet.mng.sys.PetMngSys.Utils.Utils;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	UserService userService;

	@Autowired
	PostService postService;

	public Post addComment(Long postId, CommentRequest commentRequest) {
		Post post = postService.findByPostId(postId);
		if (post == null) {
			throw new UserException(Messages.POST_NOT_FOUND);
		}
		Comment comment = new Comment();
		comment.setPostId(postId);
		if (commentRequest.getText() == null || commentRequest.getText().isEmpty()) {
			throw new UserException(Messages.ENTER_TEXT);
		}
		comment.setText(commentRequest.getText());
		long userId = Utils.getJwtUserId();
		comment.setUserId(userId);
		commentRepository.save(comment);
		return postService.save(post);
	}

	public List<CommentDTO> getAllComments(Long postId) {
		Post post = postService.findByPostId(postId);
		if (post == null) {
			throw new UserException(Messages.POST_NOT_FOUND);
		}
		List<CommentDTO> results = commentRepository.getAllCommentsOnPost(postId);

		return results;
	}

	public List<Comment> findByPostId(long postId) {
		List<Comment> comment = commentRepository.findByPostId(postId);
		return comment;
	}

	public void delete(Comment comment) {
		commentRepository.delete(comment);
	}

}