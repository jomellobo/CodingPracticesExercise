package com.orangeandbronze.coding_practices.leave.repositories;

import java.sql.*;
import java.time.*;
import java.util.*;

import com.orangeandbronze.coding_practices.leave.domain.*;

/* TODO All the methods of the Repositories declare 'throws SQLException', which breaks encapsulation
 * since it reveals that the Repositories used JDBC. Now any class that calls any of the Repository methods
 * also get tied to JDBC. We need to implement exception translation in order to hide the implementation.
 * Create a class in this package called 'DataAccessException', which should extend RuntimeException. Use this
 * class to implement exception translation, and then remove the 'throws SQLException' declaration from the 
 * methods of the Repository classes. */
public class LeaveApplicationRepository extends ConnectionManager{

	
	public Collection<LeaveApplication> findPendingByFilerEmployeeId(
			int filerEmployeeId){
		String sql = "select * from leave_applications "
				+ " where filer_employee_id = ? AND status = 'PENDING'";
		/* TODO Remove the duplicate code 'DriverManager.getConnection("jdbc:hsqldb:leaves", "sa", "")'
		 * by creating an abstract super class for the Repository classes, and moving the duplicate
		 * code to that class. */
		try (Connection conn = getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filerEmployeeId);
			ResultSet rs = stmt.executeQuery();
			return applicationsFromResultSet(rs);
		} catch(SQLException e) {
			throw new DataAccessException("Data access error in find Pending By Filer EmployeeId",e);
		}
	}

	public LeaveApplication findById(UUID leaveApplicationId) {
		String sql = "select * from leave_applications where leave_application_id = ?";
		/* TODO Remove the duplicate code 'DriverManager.getConnection("jdbc:hsqldb:leaves", "sa", "")'
		 * by creating an abstract super class for the Repository classes, and moving the duplicate
		 * code to that class. */
		try (Connection conn = getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, leaveApplicationId.toString());
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return applicationFromResultSet(rs);
		} catch(SQLException e) {
			throw new DataAccessException("Data access error in findById",e);
		}
	}

	public Collection<LeaveApplication> findPendingByApprover(
			int approverEmployeeId) {
		String sql = "select * from leave_applications "
				+ " where approver_employee_id = ? AND status = 'PENDING'";
		/* TODO Remove the duplicate code 'DriverManager.getConnection("jdbc:hsqldb:leaves", "sa", "")'
		 * by creating an abstract super class for the Repository classes, and moving the duplicate
		 * code to that class. */
		try (Connection conn = getConnection())  {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, approverEmployeeId);
			ResultSet rs = stmt.executeQuery();
			return applicationsFromResultSet(rs);
		}catch(SQLException e) {
			throw new DataAccessException("Data access error in findPendingByApprover ",e);
		}
	}

	public void updateStatus(LeaveApplication application) {
		String sql = "update leave_applications set status = ? where leave_application_id = ?";
		/* TODO Remove the duplicate code 'DriverManager.getConnection("jdbc:hsqldb:leaves", "sa", "")'
		 * by creating an abstract super class for the Repository classes, and moving the duplicate
		 * code to that class. */
		try (Connection conn = getConnection())  {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, application.getStatus().toString());
			stmt.setString(2, application.getLeaveApplicationId().toString());
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated != 1) {
				throw new DataAccessException("Rows updated not 1, was: "
						+ rowsUpdated);
			}
		}catch(SQLException e) {
			throw new DataAccessException("Data access error in updateStatus LeaveApplication",e);
		}
	}
	
	public void create(LeaveApplication newLeaveApplication) {
		String sql = "insert into leave_applications "
				+ " (leave_application_id, filer_employee_id, approver_employee_id, leave_type, start_date,	end_date, status) "
				+ " values (?, ?, ?, ?, ?, ?, ?) ";
		/* TODO Remove the duplicate code 'DriverManager.getConnection("jdbc:hsqldb:leaves", "sa", "")'
		 * by creating an abstract super class for the Repository classes, and moving the duplicate
		 * code to that class. */
		try (Connection conn = getConnection())  {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, newLeaveApplication.getLeaveApplicationId()
					.toString());
			stmt.setInt(2, newLeaveApplication.getFiler().getEmployeeId());
			stmt.setInt(3, newLeaveApplication.getApprover().getEmployeeId());
			stmt.setString(4, newLeaveApplication.getType().toString());
			stmt.setDate(5,
					java.sql.Date.valueOf(newLeaveApplication.getStart()));
			stmt.setDate(6, java.sql.Date.valueOf(newLeaveApplication.getEnd()));
			stmt.setString(7, newLeaveApplication.getStatus().toString());
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated != 1) {
				throw new DataAccessException("Rows updated not 1, was: "
						+ rowsUpdated);
			}
		}catch(SQLException e) {
			throw new DataAccessException("Data access error in create LeaveApplication",e);
		} 
	}

	private LeaveApplication applicationFromResultSet(ResultSet rs){
		try {
			UUID leaveApplicationId = UUID.fromString(rs
					.getString("leave_application_id"));
			int filerEmployeeId = rs.getInt("filer_employee_id");
			int approverEmployeeId = rs.getInt("approver_employee_id");
			LeaveType leaveType = LeaveType.valueOf(rs.getString("leave_type"));
			LocalDate start = rs.getDate("start_date").toLocalDate();
			LocalDate end = rs.getDate("end_date").toLocalDate();
			LeaveApplication.Status status = LeaveApplication.Status.valueOf(rs
					.getString("status"));
			return new LeaveApplication(leaveApplicationId, new Employee(
					filerEmployeeId, 0, 0, null), new Employee(approverEmployeeId,
					0, 0, null), leaveType, start, end, status);
		}catch(SQLException e) {
			throw new DataAccessException("Data access error in applicationFromResultSet",e);
		} 
	}

	private Collection<LeaveApplication> applicationsFromResultSet(ResultSet rs){
		try {
			Collection<LeaveApplication> applications = new ArrayList<>();
			while (rs.next()) {
				applications.add(applicationFromResultSet(rs));
			}
	
			return applications;
		}catch(SQLException e) {
			throw new DataAccessException("Data access error in applicationFromResultSet Collection",e);
		}
	}
}
