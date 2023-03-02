package comtaskxrXrTask.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comtaskxrXrTask.MailSender.OtpService;
import comtaskxrXrTask.Utils.Messages;
import comtaskxrXrTask.exception.UserException;
import comtaskxrXrTask.model.UserMaster;
import comtaskxrXrTask.repository.AuthRepository;
import comtaskxrXrTask.request.RegisterDTO;

@Service
@Transactional
public class AuthService {

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private OtpService otpService;

	private static final int MAX_ATTEMPTS = 3;

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

	public String loginservice(String email, String password) {
		UserMaster user = authRepository.findUserByEmail(email);
		if (user == null) {
			throw new UserException("Invalid email or password");
		} else if (!user.getPassword().equals(password)) {
			long attempts = user.getMinattempts();
			if (attempts >= MAX_ATTEMPTS) {
				long blockDuration = 10 * 60 * 1000; // 10 minutes in milliseconds
				System.out.println("Block Duration = "+blockDuration);
				System.out.println("");
				LocalDateTime blockEnd = LocalDateTime.now().plus(blockDuration, ChronoUnit.MILLIS);
				LocalDateTime blockEndTest = LocalDateTime.now();
				System.out.println("blockEndTest = "+blockEndTest);
				System.out.println("ChromoUnit.Miles = "+ChronoUnit.MILLIS);
				System.out.println("BlockEnd : "+blockEnd);
				
				user.setBlockEnd(blockEnd);
				authRepository.save(user);
				throw new UserException("Maximum attempts exceeded. Please try again later.");
			}
			attempts++;
			user.setMinattempts(attempts);
			authRepository.save(user);
			throw new UserException("Invalid email or password");
		} else if (user.getBlockEnd() != null && LocalDateTime.now().isBefore(user.getBlockEnd())) {
			throw new UserException("Account is blocked. Please try again later.");
		}
		user.setMinattempts(0);
		user.setBlockEnd(null);
		authRepository.save(user);
		return "Login successful";
	}

}