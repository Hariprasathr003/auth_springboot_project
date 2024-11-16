package com.demo.token.validation;

public class Demo {
	public static void main(String[] args) {
		
		ValidPath vp=new ValidPath();
		System.out.println(vp.isValid("/api/user/login"));
	}
}
