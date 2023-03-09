package com.xr.pet.mng.sys.PetMngSys.Service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.xr.pet.mng.sys.PetMngSys.Model.UserMaster;
import com.xr.pet.mng.sys.PetMngSys.Security.JwtTokenProvider;

@Service
public class JwtService {

	public static String getJwtToken(UserMaster user, CustomUserDetailsServices customUserDetailService,
			JwtTokenProvider jwtTokenProvider, PasswordEncoder bCryptPasswordEncoder) {

		UserDetails userDetails = customUserDetailService.loadUserById(user.getId());
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
				user.getMobileNumber());

		SecurityContextHolder.getContext().setAuthentication(authentication);
		return jwtTokenProvider.generateJwtToken(authentication);
	}

}
