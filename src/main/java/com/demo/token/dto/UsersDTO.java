package com.demo.token.dto;

import com.demo.token.model.Users.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for Users.
 * <p>
 * This class is used to transfer user data between different layers of the application
 * without exposing the actual entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {

    /**
     * Unique identifier for the user.
     */
    private String uuid;

    /**
     * Name of the user.
     */
    private String name;

    /**
     * Email address of the user.
     */
    private String email;

    /**
     * Username used for authentication.
     */
    private String userName;

    /**
     * Role of the user (e.g., ADMIN, TRAINEE, ATTENDEE).
     */
    private Role role;

    /**
     * Indicates whether the user is active.
     */
    private boolean isActive;

    /**
     * A custom message related to the user (e.g., status updates).
     */
    private String message;

    /**
     * Default constructor.
     */
    }
