package com.demo.token.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.demo.token.dto.AuthenticationResponse;
import com.demo.token.dto.UsersDTO;
import com.demo.token.exception.ResourceNotFoundException;
import com.demo.token.model.Users;
import com.demo.token.model.Users.Role;
import com.demo.token.repo.UserRepository;
import com.demo.token.service.UsersService;
import com.demo.token.serviceutility.UtilService;
import com.demo.token.validation.EmailValidator;
import com.demo.token.validation.PhoneNumberValidation;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UsersService {
	/**
	 * Repository for performing CRUD operations on Users table. Used to interact
	 * with the database for managing users-related data.
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * UtilService is a service class that provides various utility methods that can
	 * be utilized by different components, promoting code * reusability and
	 * separation of concerns.
	 */

	@Autowired
	private UtilService utilService;

	public UsersDTO convertToDTO(Users users) {
		UsersDTO usersDTO = utilService.getModelMapper().map(users, UsersDTO.class);
		usersDTO.setMessage("User Sucessfully Registered ");
		return usersDTO;

//		return new UsersDTO(users.getUuid(), users.getName(), users.getEmail(), users.getUserName(), users.getRole(),
//				users.getIsActive(),
//				"User Sucessfully Registered , Please check your mail for further access's credentials ");
//		
	}

	@Override
	@Transactional
	public UsersDTO createUser(Users request) {

		if (!PhoneNumberValidation.isValid(request.getPhoneNumber())) {
			throw new IllegalArgumentException("Invalid phone number format.");
		}
		if (!EmailValidator.isValid(request.getEmail())) {
			throw new IllegalArgumentException("Invalid email, Please check your email.");
		}
		Optional<Users> existingUser = userRepository.findByuserNameAndIsActive(request.getUserName(), true);
		if (existingUser.isPresent()) {
			throw new IllegalStateException("A user with this userName already exists and is active.");
		}

		Optional<Users> existingPhone = userRepository.findByPhoneNumberAndIsActive(request.getPhoneNumber(), true);
		if (existingPhone.isPresent()) {
			throw new IllegalStateException("Phone number is already exist.");
		}

		if (request.getRole().equals(Role.TRAINEE) || request.getRole().equals(Role.ATTENDEE)) {

			String subject = "Registration Sucessfull";
			String htmlfile = "registrationEmail";
			utilService.getMailService().sendEmail(request.getEmail(), subject, request.getName(), request.getEmail(),
					request.getPhoneNumber(), request.getUserName(), request.getPassword(), request.getRole(),
					htmlfile);
			request.setIsActive(true);
			request.setPassword(utilService.getPasswordEncoder().encode(request.getPassword()));

			Users savedUsers = userRepository.save(request);
			UsersDTO usersDTO = convertToDTO(savedUsers);
			usersDTO.setMessage("User Sucessfully Register as " + "" + request.getRole() + ""
					+ " please check your mail for login credentials");
			return usersDTO;
		} else {
			throw new IllegalArgumentException("Please check the role is either (TRAINEE or ATTENDEE)");

		}
	}

	@Override
	public AuthenticationResponse authenticate(String userName, String password) {
		try {
			UsernamePasswordAuthenticationToken tokenn = new UsernamePasswordAuthenticationToken(userName, password);
			// Before authentication
			System.out.println("Token before authentication: " + tokenn);

			Authentication authenticatedToken = utilService.getAuthenticationManager().authenticate(tokenn);
			// after authentication
			System.out.println("Token after authentication: " + authenticatedToken);

			UserDetails userDetails = (UserDetails) authenticatedToken.getPrincipal();
			Optional<Users> users = userRepository.findByuserName(userDetails.getUsername());
			if (users.isPresent() && users.get().getIsActive()) {
				String token = utilService.getJwtService().generateToken(userDetails);
				return new AuthenticationResponse(token);
			} else {
				throw new IllegalStateException("User has been inactive. Please contact the Admin.");
			}
		} catch (AuthenticationException e) {
			throw new BadCredentialsException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public Users updateUser(String uuid, Users request) {

		Users user = userRepository.findByUuid(uuid)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + uuid));

		if (!PhoneNumberValidation.isValid(request.getPhoneNumber())) {
			throw new IllegalArgumentException("Invalid phone number format");
		}

		if (!EmailValidator.isValid(request.getEmail())) {
			throw new IllegalArgumentException("Invalid email, Please check your email.");
		}

		Optional<Users> existingPhone = userRepository.findByPhoneNumberAndIsActive(request.getPhoneNumber(), true);
		if (existingPhone.isPresent() && !existingPhone.get().getUuid().equals(uuid)) {
			throw new IllegalArgumentException("Phone number is already in use by another account.");
		}

		Optional<Users> existingUserName = userRepository.findByUserName(request.getUserName());
		if (existingUserName.isPresent() && !existingUserName.get().getUuid().equals(uuid)) {
			throw new IllegalArgumentException("A user with this userName already exists.");
		}
		// Allow ADMIN to be changed to TRAINEE or ATTENDEE
		if (!user.getRole().equals(Role.ADMIN) && request.getRole().equals(Role.ADMIN)) {
			throw new IllegalStateException("Invalid Role");
		}
		// Common fields update
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPhoneNumber(request.getPhoneNumber());
		user.setUserName(request.getUserName());
		user.setRole(request.getRole());
		user.setIsActive(true);

		String subject = "Updated Sucessfully";
		String htmlfile = "updatedEmail";
		utilService.getMailService().sendEmail(request.getEmail(), subject, request.getName(), request.getEmail(),
				request.getPhoneNumber(), request.getUserName(), request.getPassword(), request.getRole(), htmlfile);
		return userRepository.save(user);
	}

	@Override
	public Optional<String> deactiveUser(String Uuid) {
		Users deletedUser = userRepository.findByUuid(Uuid)
				.orElseThrow(() -> new IllegalArgumentException(" user not found with uuid"));
		if (deletedUser.getRole().equals(Role.ADMIN)) {
			throw new IllegalStateException("Yor are trying to delete a Admin !!!!.");
		}
		deletedUser.setIsActive(false);
		userRepository.save(deletedUser);
		return Optional.of(deletedUser.getName());
	}

	@Override
	public List<UsersDTO> getAllUsers() {
		List<Users> users = userRepository.findByIsActiveTrueAndRoleNot(Role.ADMIN);

		if (users.isEmpty()) {
			throw new ResourceNotFoundException("No Users Found");
		}
		return users.stream() // Convert list to Stream
				.map(this::convertToDTO) // Convert each Users to UsersDTO
				.collect(Collectors.toList()); // Collect results into a List<UsersDTO>
	}

	@Override
	public List<UsersDTO> getAllActiveUsers() {
		return userRepository.findByIsActiveTrue().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<UsersDTO> getAllInactiveUsers() {
		return userRepository.findByIsActiveFalse().stream().map(this::convertToDTO) // Convert each User to UserDTO
				.collect(Collectors.toList());
	}

	@Override
	public List<UsersDTO> getAllTrainees() {
		return userRepository.findAllByRole(Role.TRAINEE).stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<UsersDTO> getAllAttendees() {
		return userRepository.findAllByRole(Role.ATTENDEE).stream().map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Users> getUsersByUuid(String Uuid) {
		Users users1 = userRepository.findByUuid(Uuid)
				.orElseThrow(() -> new IllegalArgumentException("No user founs this uuid"));
		return Optional.of(users1);

	}

	@Override
	public Optional<Users> findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	@Override
	public List<UsersDTO> findAllByNameContaining(String name) {
		return userRepository.findByNameContainingAndIsActive(name, true).stream().map(this::convertToDTO)
				.collect(Collectors.toList());
	}

}
