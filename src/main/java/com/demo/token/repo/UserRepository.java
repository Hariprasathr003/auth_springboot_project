package com.demo.token.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.token.model.Users;
import com.demo.token.model.Users.Role;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	/**
	 * To find a user by UserName
	 * 
	 * @param userName
	 * @return User Details
	 */
	Optional<Users> findByUserName(String userName);

	/**
	 * To find a user by UserName
	 * 
	 * @param userName
	 * @return User Details
	 */
	Optional<Users> findByuserName(String userName);

	/**
	 * To find a user ByRole
	 * 
	 * @param role
	 * @return user based on role
	 */
	Optional<Users> findByRole(Role role);

	/**
	 * To get all users by roles
	 * 
	 * @param role
	 * @return List of users
	 */
	List<Users> findAllByRole(Role role);

	/**
	 * To find a Active User from DB
	 * 
	 * @param userName
	 * @param b
	 * @return Active user
	 */
	Optional<Users> findByuserNameAndIsActive(String userName, boolean b);

	/**
	 * To find user by user's uuid
	 * 
	 * @param user's uuid
	 * @return user details
	 */
	Optional<Users> findByUuid(String uuid);

	/**
	 * Find all users where isActive is true
	 * 
	 * @return List of active user(true)
	 */
	List<Users> findByIsActiveTrue();

	/**
	 * Find all users where isActive is false
	 * 
	 * @return List of active user(false)
	 */
	List<Users> findByIsActiveFalse();

	/**
	 * To find a phoneNumber and is Active
	 * 
	 * @param phoneNumber
	 * @param b
	 * @return active User
	 */
	Optional<Users> findByPhoneNumberAndIsActive(String phoneNumber, boolean b);
	
	/**
	 * To get all active users from DB except admin
	 * @param role
	 * @return List of active users of both role("TRAINEE" and "ATTENDEE")
	 */
    List<Users> findByIsActiveTrueAndRoleNot(Role role);
	/**
	 * 
	 * @param name
	 * @return list of active users from DB
	 */
	// Search by partial match
	List<Users> findByNameContainingAndIsActive(String name,boolean b);
	

}
