package com.demo.token.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.token.dto.AuthenticationResponse;
import com.demo.token.dto.LoginRequest;
import com.demo.token.dto.UsersDTO;
import com.demo.token.exception.NoUsersFoundException;
import com.demo.token.exception.ResourceNotFoundException;
import com.demo.token.model.Users;
import com.demo.token.service.UsersService;

import io.jsonwebtoken.lang.Collections;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class AuthController {

	/**
	 * @param userService The service that handles the business logic for users.
	 *                    Dependency Injection is used here to ensure the controller
	 *                    can access the service methods.
	 */
	@Autowired
	private UsersService userService;
	
	/**
	 * To create a new user.
	 * 
	 * @param users UserDetails
	 * @return Saved user entity
	 */
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody Users users) {
		try {
			UsersDTO savedUserDTO = userService.createUser(users);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}

	/**
	 * authenticate user
	 * 
	 * @param request user credentials userName and password
	 * @return Authorization Token:
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
		try {
			AuthenticationResponse response = userService.authenticate(request.getUserName(),request.getPassword());
			return ResponseEntity.ok(response);
		} catch (IllegalStateException e) {
			// Handle case where the user is inactive
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			// Handle any other unexpected errors
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	/**
	 * To update a existing user
	 * 
	 * @param user's  uuid
	 * @param request UserDetails
	 * @return Saved user entity
	 */
	@PutMapping("update/{uuid}")
	public ResponseEntity<?> updateUser(@PathVariable("uuid") String uuid, @Valid @RequestBody Users request) {
		try {
			Users updatedusr = userService.updateUser(uuid, request);
			return ResponseEntity.ok(updatedusr);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occured ");
		}
	}

	/**
	 * To delete a user
	 * 
	 * @param user's Uuid
	 * @return Deleted user name with success message
	 */
	@DeleteMapping("/delete/{Uuid}")
	public ResponseEntity<String> deactiveUser(@PathVariable String Uuid) {
		try {
			Optional<String> userName = userService.deactiveUser(Uuid);
			return ResponseEntity.ok("User " + userName.get() + " Deletted Sucessfully ");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} catch (Exception ex) {
			// Handle other unexpected exceptions
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred: " + ex.getMessage());
		}
	}

	/**
	 * To get All users
	 * 
	 * @return All saved users
	 */
	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers() {
		try {
			List<UsersDTO> allUsers = userService.getAllUsers();
			if (allUsers.isEmpty()) {
				throw new NoUsersFoundException("No User Found");
			} else {
				return ResponseEntity.ok(allUsers);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

	/**
	 * To get all Active Users
	 * 
	 * @return All saved active users
	 */
	@GetMapping("/getAllActiveUsers")
	public ResponseEntity<?> getAllActiveUsers() {
		List<UsersDTO> activeUsers = userService.getAllActiveUsers();
		if (activeUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Active Users found");
		}
		return ResponseEntity.ok(activeUsers);

	}

	/**
	 * To get all Non-Active Users
	 * 
	 * @return All Non-Active Users
	 */
	@GetMapping("/getAllInActiveUsers")
	public ResponseEntity<?> getAllInactiveUsers() {
		List<UsersDTO> inactiveUsers = userService.getAllInactiveUsers();
		if (inactiveUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No In-Active Users found");
		}
		return ResponseEntity.ok(inactiveUsers);
	}

	/**
	 * To get all TRAINEES
	 * 
	 * @return All saved TRAINEES
	 */
	@GetMapping("/getAllTrainees")
	public ResponseEntity<?> getAllTrainees() {
		List<UsersDTO> traineeUsers = userService.getAllTrainees();
		if (traineeUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Trainee Found");
		}
		return ResponseEntity.ok(traineeUsers);
	}

	/**
	 * To get all ATTENDEES
	 * 
	 * @return All saved ATTENDEES
	 */
	@GetMapping("/getAllAttendees")
	public ResponseEntity<?> getAllAttendees() {
		List<UsersDTO> AttendeeUsers = userService.getAllAttendees();
		if (AttendeeUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Attendee Found");
		}
		return ResponseEntity.ok(AttendeeUsers);
	}

	/**
	 * To view user by user's uuid
	 * 
	 * @param Uuid
	 * @return Saved user
	 */
	@GetMapping("/getUserBy/{Uuid}")
	public ResponseEntity<?> getUserByUuid(@PathVariable String Uuid) {
		try {
			Optional<Users> existingUser = userService.getUsersByUuid(Uuid);
			return ResponseEntity.ok(existingUser);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/search/{name}")
	public ResponseEntity<List<UsersDTO>> getAllUserByName(@PathVariable String name) {
		List<UsersDTO> allUsers = userService.findAllByNameContaining(name);
		if (allUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
		} else {
			return ResponseEntity.ok(allUsers);
		}
	}
}