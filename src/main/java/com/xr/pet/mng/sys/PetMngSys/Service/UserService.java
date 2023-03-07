package com.xr.pet.mng.sys.PetMngSys.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xr.pet.mng.sys.PetMngSys.Exception.UserException;
import com.xr.pet.mng.sys.PetMngSys.Model.UserMaster;
import com.xr.pet.mng.sys.PetMngSys.Repository.UserRepository;
import com.xr.pet.mng.sys.PetMngSys.Request.UpdateDTO;
import com.xr.pet.mng.sys.PetMngSys.Utils.Messages;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public UserMaster addUser(int countrycode, long mobilenumber) {
		UserMaster existingUser = userRepository.findByMobileNumber(mobilenumber);
		if (existingUser != null) {
			long otp = 111111;
			System.out.println("Existing User");
			existingUser.setOtp(otp);
			existingUser.setOtpSentTime(new Date().getTime());
			existingUser.setVerified(false);
			return userRepository.save(existingUser);
		}
		long otp = 111111;
		System.out.println("New User");
		UserMaster newUser = UserMaster.builder().countryCode(countrycode).mobileNumber(mobilenumber).otp(otp)
				.otpSentTime(new Date().getTime()).verified(false).build();
		return userRepository.save(newUser);
	}

	public UserMaster verifyOtp(long mobilenumber, long otp) {
		UserMaster user = userRepository.findByMobileNumber(mobilenumber);
		if (user == null) {
			throw new UserException(Messages.USER_NOT_FOUND);
		}
		if (!user.getOtp().equals(otp)) {
			throw new UserException(Messages.OTP_NOT_MATCH);
		}

		long diffMinutes = ((new Date().getTime()) - user.getOtpSentTime()) / (60 * 1000);
		if (diffMinutes >= 1) {
			throw new UserException(Messages.OTP_EXPIREd);
		}
		user.setOtp(null);
		user.setOtpSentTime(null);
		user.setVerified(true);
		return userRepository.save(user);
	}

	public Long resendotp(long mobilenumber) {
		UserMaster user = userRepository.findByMobileNumber(mobilenumber);
		if (user == null) {
			throw new UserException(Messages.USER_NOT_FOUND);
		}
		long otp = 111111;
		user.setOtpSentTime(new Date().getTime());
		user.setOtp(otp);
		userRepository.save(user);
		return otp;
	}

	public UserMaster update(int id, UpdateDTO user) {
		UserMaster userMaster = userRepository.findById(id);
		if (userMaster == null) {
			throw new UserException(Messages.USER_NOT_FOUND);
		}
		if(!userMaster.isVerified()) {
			throw new UserException(Messages.USER_NOT_VERIFIED);
		}
		if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
			userMaster.setFirstName(user.getFirstName());
		}
		if (user.getLastName() != null && !user.getLastName().isEmpty()) {
			userMaster.setLastName(user.getLastName());
		}
		if (user.getEmail() != null && !user.getLastName().isEmpty()) {
			userMaster.setEmail(user.getEmail());
		}
		return userRepository.save(userMaster);

	}
}
