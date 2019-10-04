package com.stackroute.muzixmanager.exception;

@SuppressWarnings("serial")
public class TrackExistException extends Exception{

	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TrackExistException(final String message) {
		super();
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "TrackExistException [message: " + message + "]";
	}
}
