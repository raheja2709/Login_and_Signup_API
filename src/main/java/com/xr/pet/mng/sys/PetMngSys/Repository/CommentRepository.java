package com.xr.pet.mng.sys.PetMngSys.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xr.pet.mng.sys.PetMngSys.Model.Comment;
import com.xr.pet.mng.sys.PetMngSys.Request.CommentDTO;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Comment findByPostIdAndUserId(long postId, int userId);

	Comment findById(Long id);

	List<Comment> findAllByPostId(long postId);

	List<Comment> findByPostId(long id);

	@Query("SELECT new com.xr.pet.mng.sys.PetMngSys.Request.CommentDTO(u.firstName, u.lastName, c.text) FROM UserMaster u JOIN u.postComments c WHERE c.postId = :postId")
	List<CommentDTO> getAllCommentsOnPost(@Param("postId") Long postId);

	void deleteCommentByPostId(Long postId);

}
