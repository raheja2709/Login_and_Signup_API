package com.xr.pet.mng.sys.PetMngSys.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xr.pet.mng.sys.PetMngSys.Model.UserMaster;

public interface UserRepository extends JpaRepository<UserMaster,Integer>{

	public UserMaster findByMobileNumber(long mobileNumber);  
}
