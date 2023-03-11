package com.xr.pet.mng.sys.PetMngSys.Repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xr.pet.mng.sys.PetMngSys.Model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	public Post findById(long postId);

	Page<Post> findAllByTagContainingIgnoreCase(String tag, Pageable pageable);

	Page<Post> findAll(Pageable pageable);

//	@Query("SELECT u FROM User u JOIN u.likes l WHERE l.post.id = :postId")
//	List<UserMaster> findUsersLikedPost(@Param("postId") Long postId);

	@Modifying
	@Query("DELETE FROM com.xr.pet.mng.sys.PetMngSys.Model.Comment c WHERE c.post.id = :postId")
	void deleteCommentsByPostId(@Param("postId") Long postId);

	@Modifying
	@Query("DELETE FROM com.xr.pet.mng.sys.PetMngSys.Model.Like l WHERE l.post.id = :postId")
	void deleteLikesByPostId(@Param("postId") Long postId);

	@Modifying
	@Query("DELETE FROM com.xr.pet.mng.sys.PetMngSys.Model.Post p WHERE p.id = :postId")
	void deletePostById(@Param("postId") Long postId);

	@Transactional
	default void deletePostCascade(Long postId) {
		deleteCommentsByPostId(postId);
		deleteLikesByPostId(postId);
		deletePostById(postId);
	}
}
