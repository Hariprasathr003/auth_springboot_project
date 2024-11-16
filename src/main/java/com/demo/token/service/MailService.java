package com.demo.token.service;

import com.demo.token.model.Users.Role;

public interface MailService {

	/**
	 * To send email to registered user
	 * 
	 * @param email
	 * @param name
	 * @param email2
	 * @param phoneNumber
	 * @param userName
	 * @param password
	 * @param role
	 */
	void sendEmail(String email, String subject, String name, String email2, String phoneNumber, String userName,
			String password, Role role, String htmlfile);

}
