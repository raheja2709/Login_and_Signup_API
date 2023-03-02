package comtaskxrXrTask.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comtaskxrXrTask.model.UserMaster;
import comtaskxrXrTask.request.RegisterDTO;
import comtaskxrXrTask.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;


	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> register(RegisterDTO registerdto)
			throws UnsupportedEncodingException, MessagingException {
		authService.register(registerdto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}


	@PostMapping("/loginss")
	public ResponseEntity<UserMaster> login(@RequestParam String email, @RequestParam String password) {
		
			UserMaster user = authService.login(email, password);
			return ResponseEntity.ok(user);
	}

}
