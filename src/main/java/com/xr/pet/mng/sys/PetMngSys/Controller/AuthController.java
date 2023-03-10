package com.xr.pet.mng.sys.PetMngSys.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xr.pet.mng.sys.PetMngSys.Response.ApiResponse;
import com.xr.pet.mng.sys.PetMngSys.Service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/verify")
	public ResponseEntity<Object> verify(@RequestParam long mobilenumber, @RequestParam long otp) {
		return new ResponseEntity<>(new ApiResponse(userService.verifyOtp(mobilenumber, otp)), HttpStatus.OK);
	}

	@PostMapping(value = "/resendOTP")
	public ResponseEntity<Object> resendOTP(@RequestParam long mobilenumber) {
		return new ResponseEntity<>(userService.resendotp(mobilenumber), HttpStatus.OK);
	}

}
