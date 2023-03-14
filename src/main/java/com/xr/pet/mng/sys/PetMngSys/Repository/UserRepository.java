package com.xr.pet.mng.sys.PetMngSys.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xr.pet.mng.sys.PetMngSys.Model.UserMaster;

public interface UserRepository extends JpaRepository<UserMaster, Integer> {

	public UserMaster findByMobileNumber(long mobileNumber);

	public UserMaster findById(int id);

	public UserMaster findByFirstName(String name);

	public UserMaster findById(long id);

	public Boolean existsByEmail(String email);
}
