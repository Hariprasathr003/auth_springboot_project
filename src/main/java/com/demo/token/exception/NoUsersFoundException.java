package com.demo.token.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoUsersFoundException extends RuntimeException{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public NoUsersFoundException(String message) 
		{
			super(message);
		}
}
