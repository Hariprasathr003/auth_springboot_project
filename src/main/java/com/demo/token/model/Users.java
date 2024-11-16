package com.demo.token.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Users {
	/**
	 * It is an auto generated value for each user
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * It is an unique id for each user to identify
	 */
	@Column(nullable = false, unique = true, updatable = false)
	private String uuid = UUID.randomUUID().toString();

	/**
	 * Name of the user
	 */
	@NotNull(message = "Name cannot be null")
	private String name;

	/**
	 * email for communication
	 */
	@NotNull(message = "Email cannot be null")
	private String email;

	/**
	 * unique phoneNumber for each user
	 */
	@NotNull(message = "phoneNumber cannot be null")
	@Column(name="phone_number",nullable = false)
	private String phoneNumber;

	/**
	 * Unique userName for login
	 */
	@NotNull(message = "userName cannot be null")
	@Column(name="user_name",nullable = false)
	private String userName;

	/**
	 * password for login
	 */
	@NotNull(message = "password cannot be null")
	private String password;

	/**
	 * Used to represent a user is active or not
	 */
	@Column(name="is_active",nullable = false)
	private Boolean isActive;

	/**
	 * To identify the count of updated made on Users
	 */
	@Version
	private Long version; // This is the version column for optimistic locking.

	/**
	 * The search field is used to store the search query entered by the user.
	 * 
	 * This field holds the text that the user has typed in to perform a search
	 * operation. It can be used to filter or find relevant results based on the
	 * query provided.
	 */
	private String serach;

	/**
	 * Enum representing the various roles a user can have within the system.
	 * This includes:
	 * ADMIN: Has full access and can manage users.
	 * TRAINEE: Can log in, add categories, and manage topics under specific categories.
	 * ATTENDEE: Has read-only access to view categories and topics.
	 */
	public enum Role {
		ADMIN, TRAINEE, ATTENDEE
	}
	/**
	 * Stores the user's role.
	 * Saved as a ENUM (e.g., "ADMIN","TRAINEE",) in the database.
	 */
	@NotNull(message = "Role cannot be null(TRAINEE or ATTENDEE)")
	@Enumerated(EnumType.STRING)
	private Role role;

	/**
	 * To identify the time of Creation
	 */
	@CreatedDate
	@Column(name="created_at",nullable = false, updatable = false)
	private LocalDateTime createdAt;

	/**
	 * To identify the time of updatedAt
	 */
	@LastModifiedDate
	@Column(name="updated_at",nullable = false)
	private LocalDateTime updatedAt;
	
	/**
	 * To identify the ,who created the category
	 */
	@CreatedBy
	@Column(name = "created_by")
	private String createdBY;
	
	/**
	 * To identify the ,who updates the category(ADMIN or TRAINEE)
	 */
	@LastModifiedBy
	@Column(name = "updated_by")
	private String updatedBy;
	
}
