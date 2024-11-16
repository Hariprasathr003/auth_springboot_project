package com.demo.token.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TopicNotFoundException extends RuntimeException {
	
	
	private static final long serialVersionUID = 1L;
	public TopicNotFoundException(String message)
	{
		super(message);
	}
	public TopicNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
