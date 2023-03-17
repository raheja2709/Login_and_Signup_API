package com.xr.pet.mng.sys.PetMngSys.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xr.pet.mng.sys.PetMngSys.Model.Comment;
import com.xr.pet.mng.sys.PetMngSys.Request.CommentDTO;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Comment findByPostIdAndUserId(long postId, long userId);

	Comment findById(Long id);

	List<Comment> findAllByPostId(long postId);

	List<Comment> findByPostId(long id);

	void deleteCommentByPostId(Long postId);

	@Query(value = "SELECT c.text as commentText, u.first_name as userFirstName, u.last_name as userLastName FROM user_master u INNER JOIN comments c ON u.user_id = c.userId WHERE c.postid=:postId", nativeQuery = true)
	List<CommentDTO> getAllCommentsOnPost(@Param("postId") Long postId);

}
