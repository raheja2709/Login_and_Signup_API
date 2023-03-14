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

	List<Comment> findByPostId(long postId);

	// Class CommentDTO Path //Getting this 3 Fields From Comment //Joined User Who
	// Commented on Post
	@Query("SELECT NEW com.xr.pet.mng.sys.PetMngSys.Request.CommentDTO(u.firstName, u.lastName, c.text) FROM Comment c JOIN c.user u WHERE c.post.id = :postId")
	List<CommentDTO> findCommentsWithUserDetails(@Param("postId") Long postId);
}
