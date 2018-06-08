package com.orangeandbronze.coding_practices.leave.domain;

/* TODO Exceptions should be in the same package as the classes that throw them. 
 * Move these exceptions to the proper package.  */
public class LeaveApplicationException extends RuntimeException {

	public LeaveApplicationException() {
		super();
	}

	public LeaveApplicationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LeaveApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public LeaveApplicationException(String message) {
		super(message);
	}

	public LeaveApplicationException(Throwable cause) {
		super(cause);
	}

}
