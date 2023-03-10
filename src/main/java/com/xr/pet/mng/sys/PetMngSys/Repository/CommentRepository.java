package com.xr.pet.mng.sys.PetMngSys.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xr.pet.mng.sys.PetMngSys.Model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Comment findByPostIdAndUserId(long postId, int userId);

	Comment findById(Long id);

	List<Comment> findAllByPostId(long postId);

	List<Comment> findByPostId(long postId);
}
