package com.xr.pet.mng.sys.PetMngSys.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xr.pet.mng.sys.PetMngSys.Model.UserMaster;
import com.xr.pet.mng.sys.PetMngSys.Request.UpdateDTO;
import com.xr.pet.mng.sys.PetMngSys.Response.ApiResponse;
import com.xr.pet.mng.sys.PetMngSys.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/register")
	public ResponseEntity<Object> register(@RequestParam int countrycode, @RequestParam long mobilenumber) {

		userService.addUser(countrycode, mobilenumber);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@GetMapping(value = "/getDetails")
	public ResponseEntity<Object> verify(@RequestParam int id) {
		return new ResponseEntity<>(new ApiResponse(userService.getUserDetail(id)), HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Object> update(int id, UpdateDTO user) {
		return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
	}

	@GetMapping(value = "getAllUsers")
	public List<UserMaster> getAllUsers() {
		return userService.findAllUsers();
	}
}
