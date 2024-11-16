package com.demo.token;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
	public static void main(String args[])
	{
		BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		String rawPassword="Admin123";
		String encodePassword=passwordEncoder.encode(rawPassword);
		System.out.println(encodePassword);
	}

}
