package com.demo.token.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.demo.token.model.Users.Role;
import com.demo.token.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

	/**
	 * JavaMailSender is used to send emails from the application. It provides
	 * methods to configure and send email messages using SMTP (Simple Mail Transfer
	 * Protocol).
	 */
	@Autowired
	private JavaMailSender javaMailSender;
	/**
	 * TemplateEngine is used for processing email templates. It enables the
	 * application to generate dynamic email content by merging templates with data
	 * before sending emails.
	 */
	@Autowired
	private TemplateEngine templateEngine;

	@Override
	public void sendEmail(String to, String subject, String name, String email, String phoneNumber, String userName,
			String password, Role role, String htmlfile) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			// helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);

			// Create the email body with user details and a custom message
			Context context = new Context();
			context.setVariable("name", name);
			context.setVariable("email", email);
			context.setVariable("phoneNumber", phoneNumber);
			context.setVariable("userName", userName);
			context.setVariable("password", password);
			context.setVariable("role", role);

			// Process the HTML template with Thymeleaf
			String htmlContent = templateEngine.process(htmlfile, context);

			// Set the HTML content in the email body
			helper.setText(htmlContent, true); // true to indicate that it is HTML

			// Send the email
			javaMailSender.send(message);
			System.out.println("Registration email sent successfully!");

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException("Error while sending email: " + e.getMessage());
		}
	}
}
