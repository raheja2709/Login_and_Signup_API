package com.xr.pet.mng.sys.PetMngSys.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xr.pet.mng.sys.PetMngSys.Model.Like;
import com.xr.pet.mng.sys.PetMngSys.Request.LikeDTO;

public interface LikeRepository extends JpaRepository<Like, Long> {

	Like findByPostIdAndUserId(long postId, long userId);

	void deleteLikesByPostId(Long postId);

	@Query(value = "SELECT u.first_name as userFirstName, u.last_name as userLastName FROM user_master u INNER JOIN likes l ON u.user_id = l.userId WHERE l.postid=:postId", nativeQuery = true)
	List<LikeDTO> getAllLikesOnPost(@Param("postId") Long postId);

}
