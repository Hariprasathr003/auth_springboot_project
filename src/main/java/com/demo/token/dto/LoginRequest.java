package com.demo.token.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	@NotNull(message = "Username cannot be empty")
	private String userName;

	@NotNull(message = "Password cannot be empty")
	private String password;

}
