package com.xr.pet.mng.sys.PetMngSys.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xr.pet.mng.sys.PetMngSys.Response.ApiResponse;
import com.xr.pet.mng.sys.PetMngSys.Service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/register")
	public ResponseEntity<Object> register(@RequestParam int countrycode, @RequestParam long mobilenumber) {

		userService.addUser(countrycode,mobilenumber);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}
	
	@PostMapping(value = "/verify")
	public ResponseEntity<Object> verify(@RequestParam long mobilenumber, @RequestParam long otp){

		return new ResponseEntity<>(new ApiResponse(userService.verifyOtp(mobilenumber, otp)), HttpStatus.OK);

	}
	
}
