package com.xr.pet.mng.sys.PetMngSys.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xr.pet.mng.sys.PetMngSys.Model.Like;
import com.xr.pet.mng.sys.PetMngSys.Request.LikesDTO;

public interface LikeRepository extends JpaRepository<Like, Long> {

	Like findByPostIdAndUserId(long postId, int userId);



	@Query("SELECT NEW com.xr.pet.mng.sys.PetMngSys.Request.LikesDTO(u.firstName, u.lastName) FROM likes l Join l.user u WHERE l.post.id = :postId")
	List<LikesDTO> findByPostId(@Param("postId") Long postId);
}
