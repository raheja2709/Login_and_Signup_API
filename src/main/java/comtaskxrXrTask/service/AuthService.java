package comtaskxrXrTask.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comtaskxrXrTask.MailSender.OtpService;
import comtaskxrXrTask.Utils.Messages;
import comtaskxrXrTask.exception.UserException;
import comtaskxrXrTask.model.UserMaster;
import comtaskxrXrTask.repository.AuthRepository;
import comtaskxrXrTask.request.RegisterDTO;
import comtaskxrXrTask.request.UpdateDTO;

@Service
public class AuthService {

	// private static final int MAX_ATTEMPTS = 3;

	private static final int MAX_ATTEMPTS = 4;
	private static final long BLOCK_TIME_MINUTES = 1;

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private OtpService otpService;

	/*
	 * Add New User
	 */
	public void register(RegisterDTO registerdto) throws UnsupportedEncodingException, MessagingException {

		UserMaster userMaster = authRepository.findByUsername(registerdto.getUsername());
		if (userMaster != null) {
			throw new UserException(Messages.USER_ALREADY_EXISTS);
		}
		if (authRepository.existsByEmail(registerdto.getEmail())) {
			throw new UserException(Messages.EMAIL_TAKEN);
		}
		String checkPass = registerdto.getPassword();
		String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
		char currentCharacter;

		int upperCaseCount, lowerCaseCount, specialCharacter, numberCount;
		upperCaseCount = lowerCaseCount = specialCharacter = numberCount = 0;

		for (int i = 0; i < checkPass.length(); i++) {
			currentCharacter = checkPass.charAt(i);
			if (Character.isDigit(currentCharacter)) {
				numberCount++;
			} else if (Character.isUpperCase(currentCharacter)) {
				upperCaseCount++;
			} else if (Character.isLowerCase(currentCharacter)) {
				lowerCaseCount++;
			} else if (specialChars.contains(String.valueOf(currentCharacter))) {
				specialCharacter++;
			}
		}
		if (upperCaseCount >= 2 && lowerCaseCount >= 2 && specialCharacter >= 1 && numberCount >= 1) {

			UserMaster newUser = UserMaster.builder().firstName(registerdto.getFirstName())
					.lastName(registerdto.getLastName()).username(registerdto.getUsername())
					.email(registerdto.getEmail()).password(registerdto.getPassword()).build();

			otpService.sendEmail(registerdto);
			authRepository.save(newUser);
		} else {
			throw new UserException(Messages.PASSWORD_CONTAINS);
		}
	}

	public UserMaster login(String email, String password) {
		UserMaster user = authRepository.findUserByEmail(email);

		if (user == null) {
			throw new UserException("Invalid email or password");
		}

		if (user.getBlockEnd() != null && LocalDateTime.now().isBefore(user.getBlockEnd())) {
			throw new UserException("Account is blocked. Please try again later.");
		}

		if (!user.getPassword().equals(password)) {
			long attempts = user.getMinattempts() + 1;

			if (attempts >= MAX_ATTEMPTS) {
				LocalDateTime blockEnd = LocalDateTime.now().plus(BLOCK_TIME_MINUTES, ChronoUnit.MINUTES);
				user.setBlockEnd(blockEnd);
				user.setMinattempts(0L); // Reset minattempts to zero after block period has expired
				authRepository.save(user);
				System.out.println("Current Time = " + LocalDateTime.now());
				System.out.println("Unblock on = " + user.getBlockEnd());
				throw new UserException("Maximum attempts exceeded. Please try again later.");
			}

			user.setMinattempts(attempts);
			authRepository.save(user);
			throw new UserException("Invalid email or password");
		}

		user.setMinattempts(0L);
		user.setBlockEnd(null);
		authRepository.save(user);

		return user;
	}

	public UserMaster updateUser(int Id, UpdateDTO users) throws UserException {

		UserMaster userDetails = authRepository.findById(Id);
		String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
		int upperCaseCount, lowerCaseCount, specialCharacter, numberCount;
		upperCaseCount = lowerCaseCount = specialCharacter = numberCount = 0;
		char currentCharacter;

		if (userDetails == null) {
			throw new UserException(Messages.USER_NOT_FOUND);
		}

		if (users.getUsername() != null && !users.getUsername().isEmpty()) {
			userDetails.setUsername(users.getUsername());
		}

		if (users.getPassword() != null && !users.getPassword().isEmpty()) {
			userDetails.setPassword(users.getPassword());
		}

		if (users.getFirstName() != null && !users.getFirstName().isEmpty()) {
			userDetails.setFirstName(users.getFirstName());
		}

		if (users.getLastName() != null && !users.getLastName().isEmpty()) {
			userDetails.setLastName(users.getLastName());
		}

		for (int i = 0; i < users.getPassword().length(); i++) {
			currentCharacter = users.getPassword().charAt(i);
			if (Character.isDigit(currentCharacter)) {
				numberCount++;
			} else if (Character.isUpperCase(currentCharacter)) {
				upperCaseCount++;
			} else if (Character.isLowerCase(currentCharacter)) {
				lowerCaseCount++;
			} else if (specialChars.contains(String.valueOf(currentCharacter))) {
				specialCharacter++;
			}
		}

		if (upperCaseCount >= 2 && lowerCaseCount >= 2 && specialCharacter >= 1 && numberCount >= 1) {

			return authRepository.save(userDetails);

		} else {
			throw new UserException(Messages.PASSWORD_CONTAINS);
		}

	}

}
