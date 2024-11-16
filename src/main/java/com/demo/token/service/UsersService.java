package com.demo.token.service;

import java.util.List;
import java.util.Optional;

import com.demo.token.dto.AuthenticationResponse;
import com.demo.token.dto.UsersDTO;
import com.demo.token.model.Users;

public interface UsersService {
	/**
	 * To create a new user
	 * 
	 * @param users details
	 * @return UsersDTO
	 */
	UsersDTO createUser(Users users);

	/**
	 * To authenticate a user by credentials
	 * 
	 * @param request(userName,password)
	 * @return AuthenticationResponse as token
	 */
	AuthenticationResponse authenticate(String userName, String password);

	/**
	 * To update existing user
	 * 
	 * @param User's Uuid
	 * @param users  user_details
	 * @return updated user
	 */
	Users updateUser(String Uuid, Users users);

	/**
	 * To deactive or delete a user
	 * 
	 * @param Uuid
	 * @return success message
	 */
	Optional<String> deactiveUser(String Uuid);

	/**
	 * To get all users
	 * 
	 * @return List of users
	 */
	List<UsersDTO> getAllUsers();

	/**
	 * To get all active user
	 * 
	 * @return list of active user
	 */

	List<UsersDTO> getAllActiveUsers();

	/**
	 * To get all in-active user
	 * 
	 * @return list of in-active user
	 */
	List<UsersDTO> getAllInactiveUsers();

	/**
	 * To get all TRAINEES
	 * 
	 * @return List of TRAINEES
	 */
	public List<UsersDTO> getAllTrainees();

	/**
	 * To get all ATTENDEES
	 * 
	 * @return List of ATTENDEES
	 */
	public List<UsersDTO> getAllAttendees();

	/**
	 * To get user by using Uuid
	 * 
	 * @param Uuid
	 * @return User details
	 */
	Optional<Users> getUsersByUuid(String Uuid);

	/**
	 * To find a user by userName
	 * 
	 * @param username
	 * @return User details
	 */
	Optional<Users> findByUserName(String username);

	/**
	 * To find all user by given name
	 * 
	 * @param name
	 * @return List of users
	 */
	List<UsersDTO> findAllByNameContaining(String name);

}
