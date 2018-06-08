package com.orangeandbronze.coding_practices.leave.domain;

import com.orangeandbronze.coding_practices.leave.domain.*;

/* TODO Exceptions should be in the same package as the classes that throw them. 
 * Move these exceptions to the proper package.  */
public class InsufficientLeaveCreditsException extends LeaveApplicationException {

	public InsufficientLeaveCreditsException() {
	}

	public InsufficientLeaveCreditsException(String message) {
		super(message);
	}

	public InsufficientLeaveCreditsException(Throwable cause) {
		super(cause);
	}

	public InsufficientLeaveCreditsException(String message, Throwable cause) {
		super(message, cause);
	}

	public InsufficientLeaveCreditsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
