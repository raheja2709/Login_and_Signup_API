package com.xr.pet.mng.sys.PetMngSys.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xr.pet.mng.sys.PetMngSys.Exception.UserException;
import com.xr.pet.mng.sys.PetMngSys.Model.UserMaster;
import com.xr.pet.mng.sys.PetMngSys.Repository.UserRepository;
import com.xr.pet.mng.sys.PetMngSys.Utils.Messages;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public UserMaster addUser(int countrycode, long mobilenumber) {
		UserMaster existingUser = userRepository.findByMobileNumber(mobilenumber);
		if (existingUser != null && existingUser.isRegistered()) {
			throw new UserException(Messages.USER_EXISTS);
		} else if (existingUser != null && !existingUser.isRegistered()) {
			userRepository.delete(existingUser);
		}
		long otp = 111111;
		UserMaster newUser = UserMaster.builder().countryCode(countrycode).mobileNumber(mobilenumber).otp(otp)
				.registered(false).build();
		return userRepository.save(newUser);
	}

	public UserMaster verifyOtp(long mobilenumber, long otp) {
		UserMaster user = userRepository.findByMobileNumber(mobilenumber);
		if (user == null) {
			throw new UserException(Messages.USER_NOT_FOUND);
		}
		if (user.isRegistered()) {
			throw new UserException(Messages.USER_ALREADY_REGISTERED);
		}
		if (!user.getOtp().equals(otp)) {
			throw new UserException(Messages.OTP_NOT_MATCH);
		}
		user.setRegistered(true);
		user.setOtp(null);
		return userRepository.save(user);
	}
}
