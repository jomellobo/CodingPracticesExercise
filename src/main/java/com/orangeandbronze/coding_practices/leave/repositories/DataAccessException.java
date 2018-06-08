package com.orangeandbronze.coding_practices.leave.repositories;

import java.sql.SQLException;

public class DataAccessException extends RuntimeException{
	
	public DataAccessException(String msg, Throwable e) {
		throw new RuntimeException(msg, e);
	}
	
	public DataAccessException(String msg) {
		super(msg);
	}

	public DataAccessException(Throwable e) {
		super(e);
	}
}
