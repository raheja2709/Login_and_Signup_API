package com.xr.pet.mng.sys.PetMngSys.Service;

import java.io.IOException;

import javax.transaction.Transactional;

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
import com.xr.pet.mng.sys.PetMngSys.Model.Post;
import com.xr.pet.mng.sys.PetMngSys.Repository.CommentRepository;
import com.xr.pet.mng.sys.PetMngSys.Repository.LikeRepository;
import com.xr.pet.mng.sys.PetMngSys.Repository.PostRepository;
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

	@Autowired
	LikeService likeService;

	public Post findByPostId(long postId) {
		return postRepository.findById(postId);
	}

	public Post save(Post post) {
		return postRepository.save(post);
	}

	public Post addPost(MultipartFile file, PostDTO post) throws IOException {
		int userId = Utils.getJwtUserId();
		Post newpost = new Post();
		newpost.setTag(post.getTag());
		newpost.setLocation(post.getLocation());
		newpost.setDescription(post.getDescription());
		newpost.setPostCreatorsId(userId);
		if (file != null) {
			newpost.setFilePath(Utils.saveFile(file, Constants.PROFILE_PREFIX));

		}
		return postRepository.save(newpost);
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

	@Transactional
	public String deletePost(Long postId) {
		Post post = postRepository.findById(postId);
		int userId = Utils.getJwtUserId();
		if (post == null) {
			throw new UserException(Messages.POST_NOT_FOUND);
		}
		if (!post.getPostCreatorsId().equals(userId)) {
			throw new UserException(Messages.INVALID_ID);
		}
		postRepository.deletePostById(postId);
		commentRepository.deleteCommentByPostId(postId);
		likeRepository.deleteLikesByPostId(postId);
		return "Post deleted";
	}

	public Post updatePost(long postId, MultipartFile image, PostDTO post) throws IOException {
		Post posts = postRepository.findById(postId);
		if (posts == null) {
			throw new UserException(Messages.POST_NOT_FOUND);
		}
		Integer userId = Utils.getJwtUserId();
		if (!userId.equals(posts.getPostCreatorsId())) {
			throw new UserException(Messages.INVALID_ID);
		}
		posts.setTag(post.getTag());
		posts.setLocation(post.getLocation());
		posts.setDescription(post.getDescription());
		if (image != null) {
			posts.setFilePath(Utils.saveFile(image, Constants.PROFILE_PREFIX));
		}

		return postRepository.save(posts);
	}

}
