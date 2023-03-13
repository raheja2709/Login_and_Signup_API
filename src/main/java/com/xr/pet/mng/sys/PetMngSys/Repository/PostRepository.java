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

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM post WHERE id = :postId", nativeQuery = true)
	void deletePost(@Param("postId") Long postId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM comment WHERE post_id = :postId", nativeQuery = true)
	void deleteCommentsForPost(@Param("postId") Long postId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM likes WHERE post_id = :postId", nativeQuery = true)
	void deleteLikesForPost(@Param("postId") Long postId);

}
