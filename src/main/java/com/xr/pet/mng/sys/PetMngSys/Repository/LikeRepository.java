package com.xr.pet.mng.sys.PetMngSys.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xr.pet.mng.sys.PetMngSys.Model.Like;

public interface LikeRepository extends JpaRepository<Like,Long>{

    Like findByPostIdAndUserId(long postId, int userId);

}
