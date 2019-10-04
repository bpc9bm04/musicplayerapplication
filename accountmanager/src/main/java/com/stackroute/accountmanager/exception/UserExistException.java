package com.stackroute.accountmanager.exception;

@SuppressWarnings("serial")
public class UserExistException extends Exception{
	
	public UserExistException(String message) {
		super(message);
	}
}
