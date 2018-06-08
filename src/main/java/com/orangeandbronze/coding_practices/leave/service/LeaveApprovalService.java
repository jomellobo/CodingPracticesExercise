package com.orangeandbronze.coding_practices.leave.service;

import java.sql.*;
import java.util.*;

import com.orangeandbronze.coding_practices.leave.domain.*;
import com.orangeandbronze.coding_practices.leave.repositories.*;

/* TODO Notice that all the methods have to declare 'throws SQLException' since that's what the repository code throws. 
 * This breaks Single Responsibility Principle since a service should not be aware of any persistence details. The service
 * is now tightly coupled to the implementation of the Repositories. If the Repositories are changed to use something other
 * than JDBC, then all the 'throws SQLException' code needs to be changed as well.
 * Implement exception translation on the Repositories so that we no longer need to declare 'throws SQLException' in
 * the Service classes. Remove the 'throws SQLException' declarations after you have implemented exception translation. */
public class LeaveApprovalService {

	private EmployeeRepository employeeRepo = new EmployeeRepository();
	private LeaveApplicationRepository leaveApplicationRepo = new LeaveApplicationRepository();

	public Collection<LeaveApplication> getPendingApplicationsFiledToApprover(
			int approverEmployeeId){
		return leaveApplicationRepo.findPendingByApprover(approverEmployeeId);
	}

	public void approveLeaveApplication(int approverEmployeeId,
			UUID leaveApplicationId){
		Employee approver = employeeRepo.findById(approverEmployeeId);
		LeaveApplication application = leaveApplicationRepo
				.findById(leaveApplicationId);
		approver.approve(application);
		leaveApplicationRepo.updateStatus(application);
	}

	public void disapproveLeaveApplication(int approverEmployeeId,
			UUID leaveApplicationId) {
		Employee approver = employeeRepo.findById(approverEmployeeId);
		LeaveApplication application = leaveApplicationRepo
				.findById(leaveApplicationId);
		approver.disapprove(application);
		leaveApplicationRepo.updateStatus(application);
	}

	public void setEmployeeRepository(EmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	public void setLeaveApplicationRepository(
			LeaveApplicationRepository leaveApplicationRepo) {
		this.leaveApplicationRepo = leaveApplicationRepo;
	}

}
