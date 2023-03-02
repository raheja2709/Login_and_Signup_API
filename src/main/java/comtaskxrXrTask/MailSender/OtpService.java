package comtaskxrXrTask.MailSender;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import comtaskxrXrTask.repository.AuthRepository;
import comtaskxrXrTask.request.RegisterDTO;

@Service
public class OtpService {

	@Autowired
	AuthRepository userRepo;

	@Autowired
	JavaMailSender mailSender;
	

	public void sendEmail(RegisterDTO registerdto) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("jr397051@gmail.com", "Reset Password");
		helper.setTo(registerdto.getEmail());
		String subject = "Password";

		String content = "<p>Hello "+registerdto.getFirstName()+" Your Password is "+ registerdto.getPassword();
		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}

}
