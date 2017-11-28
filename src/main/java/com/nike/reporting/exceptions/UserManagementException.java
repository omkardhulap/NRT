package com.nike.reporting.exceptions;

public class UserManagementException extends NikeException {

	private static final long serialVersionUID = 1L;

	public UserManagementException() {
		super();
	}
	
	public UserManagementException(String errorCode, String errorMsg){
		super(errorCode, errorMsg);
	}
	
	public UserManagementException(Throwable ex, String errorMsg){
		super(ex, errorMsg);
	}

}
