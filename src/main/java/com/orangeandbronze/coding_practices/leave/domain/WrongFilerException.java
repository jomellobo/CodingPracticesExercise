package com.orangeandbronze.coding_practices.leave.domain;

import com.orangeandbronze.coding_practices.leave.domain.*;

/* TODO Exceptions should be in the same package as the classes that throw them. 
 * Move these exceptions to the proper package.  */
public class WrongFilerException extends LeaveApplicationException {

	public WrongFilerException() {
	}

	public WrongFilerException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WrongFilerException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongFilerException(String message) {
		super(message);
	}

	public WrongFilerException(Throwable cause) {
		super(cause);
	}

}
