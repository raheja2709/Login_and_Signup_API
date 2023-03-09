package com.xr.pet.mng.sys.PetMngSys.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.xr.pet.mng.sys.PetMngSys.Model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	public Post findById(long postId);
    Page<Post> findAllByTagContainingIgnoreCase(String tag,Pageable pageable);
	Page<Post> findAll(Pageable pageable);


}
