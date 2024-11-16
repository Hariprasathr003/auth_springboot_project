package com.demo.token.serviceutility;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.demo.token.service.CategoryService;
import com.demo.token.service.MailService;
import com.demo.token.service.TopicsReadStatusService;
import com.demo.token.service.UsersService;
import com.demo.token.serviceImpl.JwtService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Component
public class UtilService {
	/**
	 * Encode a provide password. This ensures that user passwords are not stored in
	 * plain text, enhancing security.
	 */

	private final PasswordEncoder passwordEncoder;

	/**
	 * Manage authentication process by provided credentials compare against with
	 * stored credentials
	 */
	private final AuthenticationManager authenticationManager;

	/**
	 * Used to extract role from Authorization Header
	 */
	private final JwtService jwtService;

	/**
	 * Responsible for sending email to user after succssfull registration
	 */
	private final MailService mailService;
	/**
	 * Responsible for automating the conversion between different object models,
	 * Used to map fields from one object to another based on their field names and
	 * types.
	 */
	private final ModelMapper modelMapper;

	/**
	 * Service for managing read statuses of topics by users. Used to track which
	 * topics have been read by which users, and to update read timestamps. This
	 * helps in implementing features like marking topics as read.
	 */
	private final TopicsReadStatusService topicsReadStatusService;

	/**
	 * Service for handling business logic related to categories. Provides methods
	 * for managing categories that topics belong to..
	 */
	private final CategoryService categoryService;
	
}
