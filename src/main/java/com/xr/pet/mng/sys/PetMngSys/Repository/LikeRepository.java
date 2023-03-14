package com.xr.pet.mng.sys.PetMngSys.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xr.pet.mng.sys.PetMngSys.Model.Like;
import com.xr.pet.mng.sys.PetMngSys.Request.LikesDTO;

public interface LikeRepository extends JpaRepository<Like, Long> {

	Like findByPostIdAndUserId(long postId, int userId);

	@Query("SELECT new com.xr.pet.mng.sys.PetMngSys.Request.LikesDTO(u.firstName, u.lastName) FROM likes l JOIN l.user u WHERE l.post.id = :postId")
	List<LikesDTO> getAllLikesOnPost(@Param("postId") Long postId);

	List<Like> findByPostId(long postId);

}
