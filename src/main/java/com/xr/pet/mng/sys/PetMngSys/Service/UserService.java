package com.xr.pet.mng.sys.PetMngSys.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xr.pet.mng.sys.PetMngSys.Exception.UserException;
import com.xr.pet.mng.sys.PetMngSys.Model.UserMaster;
import com.xr.pet.mng.sys.PetMngSys.Repository.UserRepository;
import com.xr.pet.mng.sys.PetMngSys.Request.UpdateDTO;
import com.xr.pet.mng.sys.PetMngSys.Security.JwtTokenProvider;
import com.xr.pet.mng.sys.PetMngSys.Utils.Messages;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private CustomUserDetailsServices customUserDetailsServices;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	public UserMaster findByMobileNumber(long mobilenumber) {
		return userRepository.findByMobileNumber(mobilenumber);
	}

	public UserMaster findUserById(int id) {
		return userRepository.findById(id);
	}

	public UserMaster addUser(int countrycode, long mobilenumber) {
		UserMaster existingUser = userRepository.findByMobileNumber(mobilenumber);
		if (existingUser != null) {
			long otp = 111111;
			existingUser.setOtp(otp);
			existingUser.setOtpSentTime(new Date().getTime());
			existingUser.setVerified(false);
			return userRepository.save(existingUser);
		}
		long otp = 111111;
		UserMaster newUser = UserMaster.builder().countryCode(countrycode).mobileNumber(mobilenumber).otp(otp)
				.otpSentTime(new Date().getTime()).verified(false).build();
		return userRepository.save(newUser);
	}

	public UserMaster verifyOtp(long mobilenumber, long otp) {
		UserMaster user = userRepository.findByMobileNumber(mobilenumber);
		if (user == null) {
			throw new UserException(Messages.USER_NOT_FOUND);
		}
		if (user.isVerified()) {
			throw new UserException(Messages.USER_NOT_VERIFIED);
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
		user.setToken(JwtService.getJwtToken(user, customUserDetailsServices, jwtTokenProvider, bCryptPasswordEncoder));
		return userRepository.save(user);
	}

	public UserMaster getUserDetail(int userid) {
		return userRepository.findById(userid);
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

		userMaster.setFirstName(user.getFirstName());

		userMaster.setLastName(user.getLastName());

		userMaster.setEmail(user.getEmail());

		return userRepository.save(userMaster);

	}
}
