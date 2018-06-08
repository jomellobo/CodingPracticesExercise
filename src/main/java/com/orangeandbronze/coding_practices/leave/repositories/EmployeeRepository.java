package com.orangeandbronze.coding_practices.leave.repositories;

import java.sql.*;

import com.orangeandbronze.coding_practices.leave.domain.*;

/* TODO All the methods of the Repositories declare 'throws SQLException', which breaks encapsulation
 * since it reveals that the Repositories used JDBC. Now any class that calls any of the Repository methods
 * also get tied to JDBC. We need to implement exception translation in order to hide the implementation.
 * Create a class in this package called 'DataAccessException', which should extend RuntimeException. Use this
 * class to implement exception translation, and then remove the 'throws SQLException' declaration from the 
 * methods of the Repository classes. */
public class EmployeeRepository extends ConnectionManager{

	public Employee findById(int filerEmployeeId) {
		String sql = "select * from employees where employee_id = ?";
		/* TODO Remove the duplicate code 'DriverManager.getConnection("jdbc:hsqldb:leaves", "sa", "")'
		 * by creating an abstract super class for the Repository classes, and moving the duplicate
		 * code to that class. */
		try (Connection conn = getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filerEmployeeId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int vlCredits = rs.getInt("vl_credits");
			int slCredits = rs.getInt("sl_credits");
			int approverEmployeeId = rs.getInt("approver_employee_id");
			return new Employee(filerEmployeeId, vlCredits, slCredits, new Employee(approverEmployeeId, 0, 0, null));
		} catch(SQLException e) {
			throw new DataAccessException("Data access error",e);
		}
	}

}
