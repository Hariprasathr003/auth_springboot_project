package com.demo.token.dto;

/**
 * Data Transfer Object (DTO) for authentication responses.
 * <p>
 * This class is used to encapsulate the authentication token that is returned
 * as part of the authentication response.
 */
public class AuthenticationResponse {

	/**
	 * The authentication token.
	 */
	private String token;

	/**
	 * Parameterized constructor for creating an AuthenticationResponse object with
	 * a token.
	 *
	 * @param token the authentication token to set
	 */
	public AuthenticationResponse(String token) {
		super();
		this.token = token;
	}

	/**
	 * Gets the authentication token.
	 *
	 * @return the authentication token
	 */
	public String getToken() {
		return token;
	}
}
