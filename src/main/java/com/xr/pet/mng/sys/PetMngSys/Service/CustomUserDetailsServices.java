package com.xr.pet.mng.sys.PetMngSys.Service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.xr.pet.mng.sys.PetMngSys.Model.UserMaster;
import com.xr.pet.mng.sys.PetMngSys.Security.UserPrincipal;

@Component
public class CustomUserDetailsServices implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Transactional
	public UserPrincipal loadUserByUsername(long mobilenumber) throws UsernameNotFoundException {
		UserMaster user = userService.findByMobileNumber(mobilenumber);
		return UserPrincipal.createUser(user.getId());
	}

	@Transactional
	public UserDetails loadUserById(int id) throws UsernameNotFoundException {
		UserMaster user = userService.findUserById(id);
		return UserPrincipal.createUser(user.getId());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
