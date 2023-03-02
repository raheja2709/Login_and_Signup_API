package comtaskxrXrTask.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comtaskxrXrTask.exception.UserException;
import comtaskxrXrTask.model.UserMaster;
import comtaskxrXrTask.repository.AuthRepository;
import comtaskxrXrTask.request.RegisterDTO;
import comtaskxrXrTask.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	private static final int MAX_ATTEMPTS = 3;

	@Autowired
	private AuthRepository authRepository;

	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> register(RegisterDTO registerdto)
			throws UnsupportedEncodingException, MessagingException {
		authService.register(registerdto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public void login(@RequestParam String email, @RequestParam String password) {
	    UserMaster user = authRepository.findUserByEmail(email);
	    if (user == null) {
	        throw new UserException("Invalid email or password");
	    } else if (user.getBlockEnd() != null && LocalDateTime.now().isBefore(user.getBlockEnd())) {
	        System.out.println("User.getBlocked = " + user.getBlockEnd());
	        System.out.println("LocalDateAndTime = " + LocalDateTime.now());
	        authRepository.save(user);
	        throw new UserException("Account is blocked. Please try again later.");
	    } else if (!user.getPassword().equals(password)) {
	    	long BLOCK_TIME_MINUTES = 1;
	        long attempts = user.getMinattempts();
	        if (attempts >= MAX_ATTEMPTS) {
	            long blockDuration = BLOCK_TIME_MINUTES * 60 * 1000; // in milliseconds
	            LocalDateTime blockEnd = LocalDateTime.now().plus(blockDuration, ChronoUnit.MILLIS);
	            user.setBlockEnd(blockEnd);
	            authRepository.save(user);
	            System.out.println("Blockend = " + user.getBlockEnd());
	            System.out.println("LocalDateTime = "+LocalTime.now());
	            throw new UserException("Maximum attempts exceeded. Please try again later.");
	        }
	        attempts++;
	        user.setMinattempts(attempts);
	        authRepository.save(user);
	        throw new UserException("Invalid email or password");
	    }
	    System.out.println("Time when LoggedIn : "+user.getBlockEnd());
	    user.setMinattempts(0L);
	    user.setBlockEnd(null);
	    authRepository.save(user);
	}


}
