package com.orangeandbronze.coding_practices.leave.domain;

import com.orangeandbronze.coding_practices.leave.domain.*;

/* TODO Exceptions should be in the same package as the classes that throw them. 
 * Move these exceptions to the proper package.  */
public class WrongApproverException extends LeaveApplicationException {

	public WrongApproverException() {
	}

	public WrongApproverException(String message) {
		super(message);
	}

	public WrongApproverException(Throwable cause) {
		super(cause);
	}

	public WrongApproverException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongApproverException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
